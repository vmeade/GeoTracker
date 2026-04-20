package com.geotracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.geotracker.databinding.ActivityTrackingBinding
import com.geotracker.service.TrackingService
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class TrackingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackingBinding

    private val trackPoints = mutableListOf<GeoPoint>()
    private var polyline: Polyline? = null
    private var myLocationOverlay: MyLocationNewOverlay? = null
    private var firstFix = true

    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context, intent: Intent) {
            val lat      = intent.getDoubleExtra(TrackingService.EXTRA_LATITUDE,  0.0)
            val lon      = intent.getDoubleExtra(TrackingService.EXTRA_LONGITUDE, 0.0)
            val speed    = intent.getFloatExtra(TrackingService.EXTRA_SPEED,      0f)
            val dist     = intent.getFloatExtra(TrackingService.EXTRA_DISTANCE,   0f)
            val elapsed  = intent.getLongExtra(TrackingService.EXTRA_ELAPSED,     0L)
            val alt      = intent.getDoubleExtra(TrackingService.EXTRA_ALTITUDE,  0.0)
            val accuracy = intent.getFloatExtra(TrackingService.EXTRA_ACCURACY,   0f)
            updateUI(lat, lon, speed, dist, elapsed, alt, accuracy)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = packageName
        binding = ActivityTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val journeyName = "Journey ${android.text.format.DateFormat.format("MMM dd HH:mm", System.currentTimeMillis())}"
        binding.tvJourneyName.text = journeyName

        setupMap()

        // Start tracking service
        startForegroundService(Intent(this, TrackingService::class.java).apply {
            action = TrackingService.ACTION_START
            putExtra(TrackingService.EXTRA_JOURNEY_NAME, journeyName)
        })

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(updateReceiver, IntentFilter(TrackingService.ACTION_UPDATE))

        binding.btnStop.setOnClickListener { stopJourney() }
    }

    private fun setupMap() {
        binding.mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(17.0)
        }

        myLocationOverlay = MyLocationNewOverlay(
            GpsMyLocationProvider(this), binding.mapView
        ).apply {
            enableMyLocation()
            enableFollowLocation()
        }
        binding.mapView.overlays.add(myLocationOverlay)

        polyline = Polyline().apply {
            outlinePaint.color  = 0xFF1976D2.toInt()
            outlinePaint.strokeWidth = 10f
        }
        binding.mapView.overlays.add(polyline)
    }

    private fun updateUI(
        lat: Double, lon: Double, speed: Float,
        dist: Float, elapsed: Long, alt: Double, accuracy: Float
    ) {
        val kmh    = speed * 3.6f
        val km     = dist / 1000f
        val h      = elapsed / 3600000
        val m      = (elapsed % 3600000) / 60000
        val s      = (elapsed % 60000) / 1000

        binding.tvTimer.text    = "%02d:%02d:%02d".format(h, m, s)
        binding.tvSpeed.text    = "%.1f".format(kmh)
        binding.tvDistance.text = "%.2f".format(km)
        binding.tvAltitude.text = "%.0f m".format(alt)
        binding.tvAccuracy.text = "±%.0f m".format(accuracy)

        // Update route polyline
        val gp = GeoPoint(lat, lon)
        trackPoints.add(gp)
        polyline?.setPoints(trackPoints)
        if (firstFix) {
            binding.mapView.controller.animateTo(gp)
            firstFix = false
        }
        binding.mapView.invalidate()
    }

    private fun stopJourney() {
        startService(Intent(this, TrackingService::class.java).apply {
            action = TrackingService.ACTION_STOP
        })
        startActivity(Intent(this, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
        finish()
    }

    override fun onResume()  { super.onResume();  binding.mapView.onResume()  }
    override fun onPause()   { super.onPause();   binding.mapView.onPause()   }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReceiver)
        myLocationOverlay?.disableMyLocation()
    }
}
