package com.geotracker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geotracker.database.AppDatabase
import com.geotracker.database.Journey
import com.geotracker.database.TrackPoint
import com.geotracker.databinding.ActivityDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var db: AppDatabase

    private val importLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? -> if (uri != null) importJourney(uri) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "My Journeys"
        }

        db = AppDatabase.getInstance(this)
        val adapter = JourneyAdapter { journey ->
            startActivity(Intent(this, JourneyDetailActivity::class.java).apply {
                putExtra("journey_id", journey.id)
            })
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Swipe left or right to delete a journey
        val swipeCb = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val pos     = vh.bindingAdapterPosition
                val journey = adapter.currentList[pos]
                AlertDialog.Builder(this@DashboardActivity)
                    .setTitle("Delete Journey")
                    .setMessage("Delete \"${journey.name}\"?\nThis cannot be undone.")
                    .setPositiveButton("Delete") { _, _ ->
                        lifecycleScope.launch { db.journeyDao().deleteJourney(journey) }
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                        adapter.notifyItemChanged(pos)
                    }
                    .setCancelable(false)
                    .show()
            }
        }
        ItemTouchHelper(swipeCb).attachToRecyclerView(binding.recyclerView)

        db.journeyDao().getAllJourneys().observe(this) { journeys ->
            adapter.submitList(journeys)
            binding.tvEmpty.visibility = if (journeys.isEmpty()) View.VISIBLE else View.GONE

            lifecycleScope.launch {
                val count = db.journeyDao().getJourneyCount()
                val dist  = db.journeyDao().getTotalDistance() ?: 0f
                val best  = db.journeyDao().getOverallMaxSpeed() ?: 0f
                binding.tvSummaryCount.text = "$count journeys"
                binding.tvSummaryDist.text  = "%.1f km total".format(dist / 1000f)
                binding.tvSummaryBest.text  = "Best: %.1f km/h".format(best * 3.6f)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_import) {
            importLauncher.launch(arrayOf("application/json", "application/octet-stream", "*/*"))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun importJourney(uri: Uri) {
        lifecycleScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    val text = contentResolver.openInputStream(uri)?.bufferedReader()?.readText()
                        ?: error("Could not read file")
                    val root    = JSONObject(text)
                    val jObj    = root.getJSONObject("journey")
                    val ptArray = root.getJSONArray("trackPoints")

                    val journey = Journey(
                        name          = jObj.getString("name"),
                        startTime     = jObj.getLong("startTime"),
                        endTime       = jObj.getLong("endTime"),
                        totalDistance = jObj.getDouble("totalDistanceMeters").toFloat(),
                        avgSpeed      = (jObj.getDouble("avgSpeedKmh") / 3.6).toFloat(),
                        maxSpeed      = (jObj.getDouble("maxSpeedKmh") / 3.6).toFloat(),
                        minElevation  = jObj.getDouble("minElevationMeters"),
                        maxElevation  = jObj.getDouble("maxElevationMeters"),
                        elevationGain = jObj.getDouble("elevationGainMeters"),
                        pointCount    = jObj.getInt("pointCount")
                    )
                    val newId = db.journeyDao().insertJourney(journey)

                    for (i in 0 until ptArray.length()) {
                        val p = ptArray.getJSONObject(i)
                        db.trackPointDao().insertPoint(
                            TrackPoint(
                                journeyId = newId,
                                timestamp = p.getLong("timestamp"),
                                latitude  = p.getDouble("latitude"),
                                longitude = p.getDouble("longitude"),
                                altitude  = p.getDouble("altitudeMeters"),
                                speed     = (p.getDouble("speedKmh") / 3.6).toFloat(),
                                accuracy  = p.getDouble("accuracyMeters").toFloat()
                            )
                        )
                    }
                    journey.name
                }
                Toast.makeText(this@DashboardActivity,
                    "Imported \"$result\"", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@DashboardActivity,
                    "Import failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean { onBackPressedDispatcher.onBackPressed(); return true }
}
