package com.geotracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.geotracker.database.AppDatabase
import com.geotracker.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase

    private val permLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        if (grants[Manifest.permission.ACCESS_FINE_LOCATION] == true) launchTracking()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        binding.btnStartJourney.setOnClickListener { checkPermsAndStart() }
        binding.btnDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        db.journeyDao().getRecentJourneys().observe(this) { _ ->
            lifecycleScope.launch {
                val count = db.journeyDao().getJourneyCount()
                val dist  = db.journeyDao().getTotalDistance() ?: 0f
                val best  = db.journeyDao().getOverallMaxSpeed() ?: 0f
                binding.tvJourneyCount.text   = count.toString()
                binding.tvTotalDistance.text  = "%.1f km".format(dist / 1000f)
                binding.tvBestSpeed.text      = "%.1f km/h".format(best * 3.6f)
            }
        }
    }

    private fun checkPermsAndStart() {
        val perms = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= 33) perms.add(Manifest.permission.POST_NOTIFICATIONS)

        if (perms.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            launchTracking()
        } else {
            permLauncher.launch(perms.toTypedArray())
        }
    }

    private fun launchTracking() {
        startActivity(Intent(this, TrackingActivity::class.java))
    }
}
