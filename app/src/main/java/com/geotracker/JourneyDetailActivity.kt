package com.geotracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.geotracker.database.AppDatabase
import com.geotracker.database.Journey
import com.geotracker.database.TrackPoint
import com.geotracker.databinding.ActivityJourneyDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class JourneyDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJourneyDetailBinding
    private lateinit var db: AppDatabase
    private var journeyId: Long = -1
    private var currentJourney: Journey? = null
    private var currentPoints: List<TrackPoint> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = packageName
        binding = ActivityJourneyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        journeyId = intent.getLongExtra("journey_id", -1)
        if (journeyId < 0) { finish(); return }

        db = AppDatabase.getInstance(this)
        lifecycleScope.launch {
            val journey = db.journeyDao().getJourneyById(journeyId)
                ?: run { finish(); return@launch }
            val points = db.trackPointDao().getPointsForJourney(journeyId)
            currentJourney = journey
            currentPoints  = points
            runOnUiThread {
                title = journey.name
                bindStats(journey)
                setupMap(points)
                setupCharts(points)
            }
        }
    }

    // ── Options menu: Rename / Export / Delete ────────────────────────────────

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_journey_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val j = currentJourney ?: return super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.action_play   -> {
                startActivity(Intent(this, PlaybackActivity::class.java).apply {
                    putExtra("journey_id", j.id)
                })
                true
            }
            R.id.action_rename -> { renameJourney(j); true }
            R.id.action_export -> { lifecycleScope.launch { exportJourney(j, currentPoints) }; true }
            R.id.action_delete -> { deleteJourney(j); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun renameJourney(journey: Journey) {
        val input = EditText(this).apply {
            setText(journey.name)
            selectAll()
            setPadding(48, 24, 48, 8)
        }
        AlertDialog.Builder(this)
            .setTitle("Rename Journey")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newName = input.text.toString().trim()
                if (newName.isNotEmpty()) {
                    lifecycleScope.launch {
                        val updated = journey.copy(name = newName)
                        db.journeyDao().updateJourney(updated)
                        currentJourney = updated
                        runOnUiThread { title = newName }
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteJourney(journey: Journey) {
        AlertDialog.Builder(this)
            .setTitle("Delete Journey")
            .setMessage(
                "Delete \"${journey.name}\"?\n" +
                "All GPS points will be removed. This cannot be undone."
            )
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    db.journeyDao().deleteJourney(journey)
                    runOnUiThread { finish() }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private suspend fun exportJourney(journey: Journey, points: List<TrackPoint>) {
        try {
            val exportFile = withContext(Dispatchers.IO) {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
                val durMs = if (journey.endTime > 0) journey.endTime - journey.startTime else 0L

                val root = JSONObject().apply {
                    put("journey", JSONObject().apply {
                        put("id",                  journey.id)
                        put("name",                journey.name)
                        put("startTime",           journey.startTime)
                        put("endTime",             journey.endTime)
                        put("startTimeIso",        sdf.format(Date(journey.startTime)))
                        put("endTimeIso",          if (journey.endTime > 0) sdf.format(Date(journey.endTime)) else "")
                        put("durationMs",          durMs)
                        put("totalDistanceMeters", journey.totalDistance)
                        put("avgSpeedKmh",         (journey.avgSpeed  * 3.6 * 100).toLong() / 100.0)
                        put("maxSpeedKmh",         (journey.maxSpeed  * 3.6 * 100).toLong() / 100.0)
                        put("minElevationMeters",  journey.minElevation)
                        put("maxElevationMeters",  journey.maxElevation)
                        put("elevationGainMeters", journey.elevationGain)
                        put("pointCount",          journey.pointCount)
                    })
                    put("trackPoints", JSONArray().also { arr ->
                        points.forEach { p ->
                            arr.put(JSONObject().apply {
                                put("timestamp",      p.timestamp)
                                put("timestampIso",   sdf.format(Date(p.timestamp)))
                                put("latitude",       p.latitude)
                                put("longitude",      p.longitude)
                                put("altitudeMeters", p.altitude)
                                put("speedKmh",       (p.speed * 3.6 * 100).toLong() / 100.0)
                                put("accuracyMeters", p.accuracy)
                            })
                        }
                    })
                }

                val dir = File(cacheDir, "exports").also { it.mkdirs() }
                File(dir, "journey_${journey.id}.json").also { it.writeText(root.toString(2)) }
            }

            // Back on Main dispatcher – safe to start activity
            val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", exportFile)
            startActivity(
                Intent.createChooser(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "application/json"
                        putExtra(Intent.EXTRA_STREAM,  uri)
                        putExtra(Intent.EXTRA_SUBJECT, "GeoTracker: ${journey.name}")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    },
                    "Export Journey Data"
                )
            )
        } catch (e: Exception) {
            Toast.makeText(this, "Export failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // ── Stats / Map / Charts ──────────────────────────────────────────────────

    private fun bindStats(j: Journey) {
        val sdf = SimpleDateFormat("EEE MMM dd, yyyy  HH:mm", Locale.getDefault())
        val dur = if (j.endTime > 0) j.endTime - j.startTime else 0L
        val h   = dur / 3600000; val m = (dur % 3600000) / 60000; val s = (dur % 60000) / 1000

        binding.tvDate.text       = sdf.format(Date(j.startTime))
        binding.tvDuration.text   = "%02d:%02d:%02d".format(h, m, s)
        binding.tvDistance.text   = "%.2f km".format(j.totalDistance / 1000f)
        binding.tvAvgSpeed.text   = "%.1f km/h".format(j.avgSpeed * 3.6f)
        binding.tvMaxSpeed.text   = "%.1f km/h".format(j.maxSpeed * 3.6f)
        binding.tvElevGain.text   = "%.0f m".format(j.elevationGain)
        binding.tvMinElev.text    = "%.0f m".format(j.minElevation)
        binding.tvMaxElev.text    = "%.0f m".format(j.maxElevation)
        binding.tvPoints.text     = "${j.pointCount} GPS points"
    }

    private fun setupMap(points: List<TrackPoint>) {
        if (points.isEmpty()) { binding.mapView.visibility = View.GONE; return }

        binding.mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
        }

        val geoPoints = points.map { GeoPoint(it.latitude, it.longitude) }

        val polyline = Polyline().apply {
            outlinePaint.color       = 0xFF1976D2.toInt()
            outlinePaint.strokeWidth = 10f
            setPoints(geoPoints)
        }
        binding.mapView.overlays.add(polyline)

        fun marker(pos: GeoPoint, t: String) = Marker(binding.mapView).apply {
            position = pos; title = t
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        }
        binding.mapView.overlays.add(marker(geoPoints.first(), "Start"))
        binding.mapView.overlays.add(marker(geoPoints.last(),  "End"))

        binding.mapView.post {
            if (geoPoints.size > 1) {
                binding.mapView.zoomToBoundingBox(
                    BoundingBox.fromGeoPoints(geoPoints).increaseByScale(1.3f), true)
            } else {
                binding.mapView.controller.setCenter(geoPoints.first())
                binding.mapView.controller.setZoom(16.0)
            }
        }
    }

    private fun setupCharts(points: List<TrackPoint>) {
        if (points.isEmpty()) {
            binding.chartSpeed.visibility     = View.GONE
            binding.chartElevation.visibility = View.GONE
            return
        }

        val speedEntries = points.mapIndexed { i, p -> Entry(i.toFloat(), p.speed * 3.6f) }
        val speedSet = LineDataSet(speedEntries, "Speed (km/h)").apply {
            color = Color.parseColor("#1976D2")
            setDrawCircles(false); lineWidth = 2f
            setDrawFilled(true); fillColor = Color.parseColor("#1976D2"); fillAlpha = 60
            mode = LineDataSet.Mode.CUBIC_BEZIER; setDrawValues(false)
        }
        binding.chartSpeed.apply {
            data = LineData(speedSet)
            description.text = "Speed over journey"
            description.textColor = Color.GRAY
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false); xAxis.setDrawLabels(false)
            axisRight.isEnabled = false
            axisLeft.textColor  = Color.DKGRAY
            legend.isEnabled    = false
            setTouchEnabled(false)
            animateX(600)
            invalidate()
        }

        val elevEntries = points.mapIndexed { i, p -> Entry(i.toFloat(), p.altitude.toFloat()) }
        val elevSet = LineDataSet(elevEntries, "Elevation (m)").apply {
            color = Color.parseColor("#388E3C")
            setDrawCircles(false); lineWidth = 2f
            setDrawFilled(true); fillColor = Color.parseColor("#388E3C"); fillAlpha = 60
            mode = LineDataSet.Mode.CUBIC_BEZIER; setDrawValues(false)
        }
        binding.chartElevation.apply {
            data = LineData(elevSet)
            description.text = "Elevation (m)"
            description.textColor = Color.GRAY
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false); xAxis.setDrawLabels(false)
            axisRight.isEnabled = false
            axisLeft.textColor  = Color.DKGRAY
            legend.isEnabled    = false
            setTouchEnabled(false)
            animateX(600)
            invalidate()
        }
    }

    override fun onResume()  { super.onResume();  binding.mapView.onResume()  }
    override fun onPause()   { super.onPause();   binding.mapView.onPause()   }
    override fun onSupportNavigateUp(): Boolean { onBackPressedDispatcher.onBackPressed(); return true }
}
