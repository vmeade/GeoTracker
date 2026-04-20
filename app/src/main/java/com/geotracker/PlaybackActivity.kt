package com.geotracker

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.geotracker.database.AppDatabase
import com.geotracker.database.TrackPoint
import com.geotracker.databinding.ActivityPlaybackBinding
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.util.*

class PlaybackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaybackBinding
    private lateinit var db: AppDatabase
    private var journeyId: Long = -1

    private var trackPoints: List<TrackPoint> = emptyList()
    private var windData: WindData? = null

    // Map overlays
    private var routePolyline: Polyline? = null
    private var trailPolyline: Polyline? = null
    private var boatMarker: Marker? = null
    private var windOverlay: WindVectorOverlay? = null

    // Playback state
    private val handler = Handler(Looper.getMainLooper())
    private var isPlaying = false
    private var speedMultiplier = 10
    private var journeyDurationMs = 0L
    private var currentOffsetMs = 0L
    private var wallClockStartMs = 0L
    private var pausedOffsetMs = 0L

    // Trail is drawn only up to the last GPS point we've crossed
    private var trailEndIndex = -1
    private var trailAddedToMap = false   // don't add trail overlay until it has valid points

    private val frameRunnable = object : Runnable {
        override fun run() {
            if (!isPlaying) return
            val elapsed = (System.currentTimeMillis() - wallClockStartMs) * speedMultiplier
            currentOffsetMs = (pausedOffsetMs + elapsed).coerceAtMost(journeyDurationMs)
            applyFrame(currentOffsetMs)
            if (currentOffsetMs >= journeyDurationMs) {
                stopPlayback()
            } else {
                handler.postDelayed(this, 50L)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = packageName
        binding = ActivityPlaybackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Playback"
        }

        journeyId = intent.getLongExtra("journey_id", -1)
        if (journeyId < 0) { finish(); return }

        db = AppDatabase.getInstance(this)
        wireControls()

        lifecycleScope.launch {
            val journey = db.journeyDao().getJourneyById(journeyId)
                ?: run { finish(); return@launch }
            val points = db.trackPointDao().getPointsForJourney(journeyId)
            trackPoints = points

            // Derive duration from points if endTime was not properly saved
            journeyDurationMs = when {
                journey.endTime > journey.startTime -> journey.endTime - journey.startTime
                points.size >= 2                    -> points.last().timestamp - points.first().timestamp
                else                                -> 0L
            }

            runOnUiThread {
                supportActionBar?.title = journey.name
                binding.tvJourneyTitle.text = journey.name
                if (journeyDurationMs > 0) binding.btnPlayPause.isEnabled = true
                setupMap(points)
                applyFrame(0L)
            }

            if (points.isNotEmpty() && journeyDurationMs > 0) {
                runOnUiThread {
                    binding.tvWindStatus.text = "Fetching wind data…"
                    binding.progressWind.visibility = View.VISIBLE
                }
                val centerLat = points.map { it.latitude }.average()
                val centerLon = points.map { it.longitude }.average()
                val startMs = points.first().timestamp
                val endMs   = if (journey.endTime > 0) journey.endTime else points.last().timestamp
                val wind    = WeatherRepository.fetchWindData(centerLat, centerLon, startMs, endMs)
                windData = wind
                runOnUiThread {
                    binding.progressWind.visibility = View.GONE
                    if (wind != null) {
                        binding.tvWindStatus.text = "Wind data ready \u2714"
                        val sample = wind.interpolateAt(startMs)
                        pushWind(sample)
                        updateWindHud(sample)
                        windOverlay?.startAnimation()
                    } else {
                        binding.tvWindStatus.text = "Wind data unavailable (offline?)"
                        binding.cbWind.isEnabled      = false
                        binding.cbWindField.isEnabled = false
                    }
                }
            }
        }
    }

    // ── controls ──────────────────────────────────────────────────────────────

    private fun wireControls() {
        binding.btnPlayPause.isEnabled = false   // enabled once data loads

        binding.btnPlayPause.setOnClickListener {
            if (isPlaying) pausePlayback() else startPlayback()
        }
        binding.btnRestart.setOnClickListener {
            stopPlayback()
            trailEndIndex    = -1
            trailAddedToMap  = false
            trailPolyline?.let { binding.mapView.overlays.remove(it) }
            currentOffsetMs  = 0L
            pausedOffsetMs   = 0L
            applyFrame(0L)
        }

        val speedLabels = arrayOf("1×", "5×", "10×", "50×", "100×")
        val speedValues = intArrayOf(1, 5, 10, 50, 100)
        binding.spinnerSpeed.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, speedLabels
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        binding.spinnerSpeed.setSelection(2)
        binding.spinnerSpeed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                val was = isPlaying
                if (was) pausePlayback()
                speedMultiplier = speedValues[pos]
                if (was) startPlayback()
            }
            override fun onNothingSelected(p: AdapterView<*>?) {}
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar, progress: Int, fromUser: Boolean) {
                if (!fromUser || journeyDurationMs <= 0) return
                val was = isPlaying
                if (was) pausePlayback()
                // Reset trail when seeking backwards
                val seekMs = journeyDurationMs * progress / 1000L
                if (seekMs < currentOffsetMs) {
                    trailEndIndex   = -1
                    trailAddedToMap = false
                    trailPolyline?.let { binding.mapView.overlays.remove(it) }
                }
                currentOffsetMs = seekMs
                pausedOffsetMs  = currentOffsetMs
                applyFrame(currentOffsetMs)
                if (was) startPlayback()
            }
            override fun onStartTrackingTouch(sb: SeekBar) {}
            override fun onStopTrackingTouch(sb: SeekBar) {}
        })

        binding.cbRoute.isChecked     = true
        binding.cbTrail.isChecked     = true
        binding.cbWind.isChecked      = true
        binding.cbWindField.isChecked = true

        binding.cbRoute    .setOnCheckedChangeListener { _, v -> routePolyline?.isEnabled = v;      binding.mapView.invalidate() }
        binding.cbTrail    .setOnCheckedChangeListener { _, v -> trailPolyline?.isEnabled = v;      binding.mapView.invalidate() }
        binding.cbWind     .setOnCheckedChangeListener { _, v -> windOverlay?.showCurrentArrow = v; binding.mapView.invalidate() }
        binding.cbWindField.setOnCheckedChangeListener { _, v -> windOverlay?.showField = v;        binding.mapView.invalidate() }
    }

    // ── playback engine ───────────────────────────────────────────────────────

    private fun startPlayback() {
        handler.removeCallbacks(frameRunnable)
        if (currentOffsetMs >= journeyDurationMs) {
            trailEndIndex   = -1
            currentOffsetMs = 0L
            pausedOffsetMs  = 0L
        }
        isPlaying        = true
        wallClockStartMs = System.currentTimeMillis()
        pausedOffsetMs   = currentOffsetMs
        binding.btnPlayPause.text = "\u23F8"
        handler.post(frameRunnable)
    }

    private fun pausePlayback() {
        isPlaying      = false
        pausedOffsetMs = currentOffsetMs
        handler.removeCallbacks(frameRunnable)
        binding.btnPlayPause.text = "\u25B6"
    }

    private fun stopPlayback() {
        isPlaying = false
        handler.removeCallbacks(frameRunnable)
        binding.btnPlayPause.text = "\u25B6"
    }

    // ── frame update ──────────────────────────────────────────────────────────

    private fun applyFrame(offsetMs: Long) {
        if (trackPoints.isEmpty()) return
        try {
            val journeyStartTs = trackPoints.first().timestamp
            val currentTs      = journeyStartTs + offsetMs

            // Move boat marker
            val pos = interpolatePosition(currentTs)
            boatMarker?.position = pos
            windOverlay?.boatGeoPoint = pos

            // Grow trail – only rebuild when we cross a new GPS point.
            // Trail overlay is lazy-added after first valid setPoints() call
            // to avoid OSMDroid NPE on null bounding box.
            val newEndIndex = trackPoints.indexOfLast { it.timestamp <= currentTs }
            if (newEndIndex != trailEndIndex && newEndIndex >= 1) {
                trailEndIndex = newEndIndex
                val trail = ArrayList<GeoPoint>(newEndIndex + 1)
                for (i in 0..newEndIndex) {
                    trail.add(GeoPoint(trackPoints[i].latitude, trackPoints[i].longitude))
                }
                trailPolyline?.setPoints(trail)   // bounding box now valid
                if (!trailAddedToMap) {
                    // Insert after routePolyline so it renders on top of it
                    val routeIdx = binding.mapView.overlays.indexOf(routePolyline)
                    binding.mapView.overlays.add(
                        if (routeIdx >= 0) routeIdx + 1 else 0, trailPolyline)
                    trailAddedToMap = true
                }
            }

            // Wind
            windData?.interpolateAt(currentTs)?.also { s ->
                pushWind(s)
                updateWindHud(s)
            }

            // Seekbar
            if (journeyDurationMs > 0) {
                val pct = (offsetMs * 1000L / journeyDurationMs).toInt().coerceIn(0, 1000)
                binding.seekBar.progress = pct
            }

            // Elapsed time
            val h = offsetMs / 3_600_000L
            val m = (offsetMs % 3_600_000L) / 60_000L
            val s = (offsetMs % 60_000L) / 1_000L
            binding.tvPlaybackTime.text = "+%02d:%02d:%02d".format(h, m, s)

            // Boat speed
            val nearest = trackPoints.minByOrNull { kotlin.math.abs(it.timestamp - currentTs) }
            binding.tvBoatSpeed.text = "Boat: %.1f kt".format((nearest?.speed ?: 0f) * 1.944f)

            binding.mapView.invalidate()
        } catch (e: Exception) {
            // swallow any OSMDroid rendering errors silently
        }
    }

    private fun interpolatePosition(targetTs: Long): GeoPoint {
        val pts = trackPoints
        if (pts.size == 1) return GeoPoint(pts[0].latitude, pts[0].longitude)
        val clamped = targetTs.coerceIn(pts.first().timestamp, pts.last().timestamp)
        val i0 = pts.indexOfLast { it.timestamp <= clamped }.coerceAtLeast(0)
        val i1 = (i0 + 1).coerceAtMost(pts.size - 1)
        if (i0 == i1) return GeoPoint(pts[i0].latitude, pts[i0].longitude)
        val t0   = pts[i0].timestamp.toDouble()
        val t1   = pts[i1].timestamp.toDouble()
        val frac = if (t1 > t0) ((clamped - t0) / (t1 - t0)).coerceIn(0.0, 1.0) else 0.0
        return GeoPoint(
            pts[i0].latitude  + frac * (pts[i1].latitude  - pts[i0].latitude),
            pts[i0].longitude + frac * (pts[i1].longitude - pts[i0].longitude)
        )
    }

    private fun pushWind(sample: WindSample) {
        windOverlay?.windSpeedMs      = sample.speedMs
        windOverlay?.windDirectionDeg = sample.directionDeg
        windOverlay?.windGustsMs      = sample.gustsMs
    }

    private fun updateWindHud(sample: WindSample) {
        val knots = sample.speedMs * 1.944f
        val card  = windOverlay?.toCardinal(sample.directionDeg) ?: ""
        val bf    = windOverlay?.toBeaufort(sample.speedMs) ?: 0
        val gust  = sample.gustsMs * 1.944f
        binding.tvWindInfo.text = "Wind %.1f kt %s  G %.1f  Bf%d".format(knots, card, gust, bf)
    }

    // ── map setup ─────────────────────────────────────────────────────────────

    private fun setupMap(points: List<TrackPoint>) {
        if (points.isEmpty()) return

        binding.mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }

        val geoPoints = points.map { GeoPoint(it.latitude, it.longitude) }

        // Full faint route
        routePolyline = Polyline().apply {
            outlinePaint.color       = Color.argb(70, 21, 101, 192)
            outlinePaint.strokeWidth = 6f
            setPoints(ArrayList(geoPoints))
        }
        binding.mapView.overlays.add(routePolyline)

        // Trail created but NOT added to overlays yet — OSMDroid NPEs on a
        // Polyline that has never had setPoints() called (null bounding box).
        // We add it lazily on first valid setPoints() call in applyFrame().
        trailPolyline = Polyline().apply {
            outlinePaint.color       = 0xFF1565C0.toInt()
            outlinePaint.strokeWidth = 9f
        }
        trailAddedToMap = false

        // Wind overlay (animation started only once wind data arrives)
        windOverlay = WindVectorOverlay(binding.mapView)
        binding.mapView.overlays.add(windOverlay)

        // Boat marker
        boatMarker = Marker(binding.mapView).apply {
            position = geoPoints.first()
            title    = "Boat"
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        }
        binding.mapView.overlays.add(boatMarker)

        binding.mapView.post {
            if (geoPoints.size > 1) {
                binding.mapView.zoomToBoundingBox(
                    BoundingBox.fromGeoPoints(geoPoints).increaseByScale(1.4f), true)
            } else {
                binding.mapView.controller.apply {
                    setCenter(geoPoints.first())
                    setZoom(16.0)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        if (windData != null) windOverlay?.startAnimation()
    }
    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
        windOverlay?.stopAnimation()
        if (isPlaying) pausePlayback()
    }
    override fun onDestroy() {
        super.onDestroy()
        windOverlay?.stopAnimation()
        handler.removeCallbacksAndMessages(null)
    }
    override fun onSupportNavigateUp(): Boolean { onBackPressedDispatcher.onBackPressed(); return true }
}
