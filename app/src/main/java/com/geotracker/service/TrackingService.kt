package com.geotracker.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.geotracker.MainActivity
import com.geotracker.R
import com.geotracker.database.AppDatabase
import com.geotracker.database.Journey
import com.geotracker.database.TrackPoint
import kotlinx.coroutines.launch

class TrackingService : LifecycleService() {

    companion object {
        const val ACTION_START  = "com.geotracker.START"
        const val ACTION_STOP   = "com.geotracker.STOP"
        const val ACTION_UPDATE = "com.geotracker.UPDATE"

        const val EXTRA_JOURNEY_NAME = "journey_name"
        const val EXTRA_JOURNEY_ID   = "journey_id"
        const val EXTRA_LATITUDE     = "latitude"
        const val EXTRA_LONGITUDE    = "longitude"
        const val EXTRA_SPEED        = "speed"
        const val EXTRA_DISTANCE     = "distance"
        const val EXTRA_ELAPSED      = "elapsed"
        const val EXTRA_ALTITUDE     = "altitude"
        const val EXTRA_ACCURACY     = "accuracy"

        private const val CHANNEL_ID      = "tracking_channel"
        private const val NOTIFICATION_ID = 1001
    }

    private var journeyId: Long = -1
    private var journeyName: String = ""
    private var startTime: Long = 0
    private var totalDistance: Float = 0f
    private var maxSpeed: Float = 0f
    private var minElevation: Double = Double.MAX_VALUE
    private var maxElevation: Double = Double.MIN_VALUE
    private var elevationGain: Double = 0.0
    private var lastElevation: Double = 0.0
    private var pointCount: Int = 0
    private var lastLocation: Location? = null
    private var wakeLock: PowerManager.WakeLock? = null

    private lateinit var locationManager: LocationManager
    private lateinit var db: AppDatabase

    private val locationListener = LocationListener { loc -> handleLocation(loc) }

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ACTION_START -> {
                journeyName = intent.getStringExtra(EXTRA_JOURNEY_NAME) ?: "Journey"
                startTracking()
            }
            ACTION_STOP -> stopTracking()
        }
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun startTracking() {
        startTime = System.currentTimeMillis()
        totalDistance = 0f; maxSpeed = 0f
        minElevation = Double.MAX_VALUE; maxElevation = Double.MIN_VALUE
        elevationGain = 0.0; lastElevation = 0.0; lastLocation = null; pointCount = 0

        // Keep CPU alive so GPS fixes keep arriving even when screen is off
        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager)
            .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GeoTracker::TrackingWakeLock")
            .also { it.acquire(12 * 60 * 60 * 1000L) } // 12-hour safety cap

        lifecycleScope.launch {
            journeyId = db.journeyDao().insertJourney(
                Journey(name = journeyName, startTime = startTime)
            )
        }

        startForeground(NOTIFICATION_ID, buildNotification("Starting GPS…"))

        val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
        for (provider in providers) {
            try {
                if (locationManager.isProviderEnabled(provider)) {
                    locationManager.requestLocationUpdates(provider, 2000L, 5f, locationListener)
                }
            } catch (_: Exception) {}
        }
    }

    private fun stopTracking() {
        locationManager.removeUpdates(locationListener)

        // Snapshot all in-memory stats NOW, before anything is cleared
        val endTime     = System.currentTimeMillis()
        val durationSec = (endTime - startTime) / 1000f
        val avgSpeed    = if (durationSec > 0) totalDistance / durationSec else 0f
        val snapId      = journeyId
        val snapDist    = totalDistance
        val snapMax     = maxSpeed
        val snapMinElev = if (minElevation == Double.MAX_VALUE) 0.0 else minElevation
        val snapMaxElev = if (maxElevation == Double.MIN_VALUE) 0.0 else maxElevation
        val snapGain    = elevationGain
        val snapCount   = pointCount

        stopForeground(STOP_FOREGROUND_REMOVE)
        wakeLock?.let { if (it.isHeld) it.release() }
        wakeLock = null

        if (snapId > 0) {
            // CRITICAL: stopSelf() is called INSIDE the coroutine, after the DB write.
            // Calling it outside would trigger onDestroy() which cancels lifecycleScope
            // before the update coroutine completes — causing all stats to remain zero.
            lifecycleScope.launch {
                db.journeyDao().getJourneyById(snapId)?.let { j ->
                    db.journeyDao().updateJourney(
                        j.copy(
                            endTime       = endTime,
                            totalDistance = snapDist,
                            avgSpeed      = avgSpeed,
                            maxSpeed      = snapMax,
                            minElevation  = snapMinElev,
                            maxElevation  = snapMaxElev,
                            elevationGain = snapGain,
                            pointCount    = snapCount
                        )
                    )
                }
                stopSelf()
            }
        } else {
            stopSelf()
        }
    }

    private fun handleLocation(location: Location) {
        if (journeyId < 0) return

        val speed    = location.speed
        val altitude = location.altitude

        lastLocation?.let { totalDistance += it.distanceTo(location) }
        lastLocation = location

        if (speed > maxSpeed) maxSpeed = speed
        if (altitude < minElevation) minElevation = altitude
        if (altitude > maxElevation) maxElevation = altitude
        if (lastElevation > 0.0 && altitude > lastElevation) elevationGain += altitude - lastElevation
        lastElevation = altitude
        pointCount++

        lifecycleScope.launch {
            db.trackPointDao().insertPoint(
                TrackPoint(
                    journeyId = journeyId,
                    timestamp = System.currentTimeMillis(),
                    latitude  = location.latitude,
                    longitude = location.longitude,
                    altitude  = altitude,
                    speed     = speed,
                    accuracy  = location.accuracy
                )
            )
        }

        val elapsed = System.currentTimeMillis() - startTime
        LocalBroadcastManager.getInstance(this).sendBroadcast(
            Intent(ACTION_UPDATE).apply {
                putExtra(EXTRA_JOURNEY_ID,  journeyId)
                putExtra(EXTRA_LATITUDE,    location.latitude)
                putExtra(EXTRA_LONGITUDE,   location.longitude)
                putExtra(EXTRA_SPEED,       speed)
                putExtra(EXTRA_DISTANCE,    totalDistance)
                putExtra(EXTRA_ELAPSED,     elapsed)
                putExtra(EXTRA_ALTITUDE,    altitude)
                putExtra(EXTRA_ACCURACY,    location.accuracy)
            }
        )

        val km  = totalDistance / 1000f
        val kmh = speed * 3.6f
        val nm  = buildNotification("%.2f km  |  %.1f km/h".format(km, kmh))
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(NOTIFICATION_ID, nm)
    }

    private fun buildNotification(text: String): Notification {
        val pi = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("GeoTracker – Recording")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pi)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        val ch = NotificationChannel(CHANNEL_ID, "Journey Tracking",
            NotificationManager.IMPORTANCE_LOW).apply {
            description = "Active journey GPS tracking"
        }
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(ch)
    }

    override fun onDestroy() {
        wakeLock?.let { if (it.isHeld) it.release() }
        wakeLock = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}
