package com.geotracker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

data class WindSample(
    val speedMs: Float,       // m/s
    val directionDeg: Float,  // degrees FROM (0=N, 90=E)
    val gustsMs: Float        // m/s
)

data class WindData(
    val timestamps: List<Long>,       // Unix ms UTC
    val windSpeedMs: List<Float>,
    val windDirectionDeg: List<Float>,
    val windGustsMs: List<Float>
) {
    fun interpolateAt(timeMs: Long): WindSample {
        if (timestamps.isEmpty()) return WindSample(0f, 0f, 0f)
        if (timestamps.size == 1) return WindSample(windSpeedMs[0], windDirectionDeg[0], windGustsMs[0])

        val idx = timestamps.binarySearch(timeMs)
        if (idx >= 0) return WindSample(windSpeedMs[idx], windDirectionDeg[idx], windGustsMs[idx])

        val ins = -(idx + 1)
        if (ins == 0) return WindSample(windSpeedMs[0], windDirectionDeg[0], windGustsMs[0])
        if (ins >= timestamps.size) return WindSample(windSpeedMs.last(), windDirectionDeg.last(), windGustsMs.last())

        val i0 = ins - 1
        val i1 = ins
        val t0 = timestamps[i0].toDouble()
        val t1 = timestamps[i1].toDouble()
        val frac = ((timeMs - t0) / (t1 - t0)).toFloat().coerceIn(0f, 1f)

        val speed = windSpeedMs[i0] + frac * (windSpeedMs[i1] - windSpeedMs[i0])
        val gusts = windGustsMs[i0] + frac * (windGustsMs[i1] - windGustsMs[i0])

        // Circular interpolation for direction
        val d0 = windDirectionDeg[i0].toDouble()
        val d1 = windDirectionDeg[i1].toDouble()
        val diff = ((d1 - d0 + 540.0) % 360.0) - 180.0
        val dir = ((d0 + frac * diff + 360.0) % 360.0).toFloat()

        return WindSample(speed, dir, gusts)
    }
}

object WeatherRepository {

    private val dateFmt = SimpleDateFormat("yyyy-MM-dd", Locale.US).also {
        it.timeZone = TimeZone.getTimeZone("UTC")
    }

    suspend fun fetchWindData(
        lat: Double, lon: Double,
        startMs: Long, endMs: Long
    ): WindData? = withContext(Dispatchers.IO) {
        try {
            val startDate = dateFmt.format(Date(startMs))
            val endDate   = dateFmt.format(Date(maxOf(startMs, endMs)))

            val sevenDaysAgo = System.currentTimeMillis() - 7L * 24 * 3600_000L
            val base = if (startMs < sevenDaysAgo)
                "https://archive-api.open-meteo.com/v1/archive"
            else
                "https://api.open-meteo.com/v1/forecast"

            val url = "$base?latitude=$lat&longitude=$lon" +
                "&start_date=$startDate&end_date=$endDate" +
                "&hourly=wind_speed_10m,wind_direction_10m,wind_gusts_10m" +
                "&wind_speed_unit=ms&timezone=UTC"

            val json   = JSONObject(URL(url).readText())
            val hourly = json.getJSONObject("hourly")
            val times  = hourly.getJSONArray("time")
            val speeds = hourly.getJSONArray("wind_speed_10m")
            val dirs   = hourly.getJSONArray("wind_direction_10m")
            val gusts  = hourly.getJSONArray("wind_gusts_10m")

            val hourlyFmt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US).also {
                it.timeZone = TimeZone.getTimeZone("UTC")
            }

            val tsList  = ArrayList<Long>(times.length())
            val spList  = ArrayList<Float>(times.length())
            val dirList = ArrayList<Float>(times.length())
            val gtList  = ArrayList<Float>(times.length())

            for (i in 0 until times.length()) {
                val ts = hourlyFmt.parse(times.getString(i))?.time ?: continue
                tsList .add(ts)
                spList .add(if (speeds.isNull(i)) 0f else speeds.getDouble(i).toFloat())
                dirList.add(if (dirs.isNull(i))   0f else dirs.getDouble(i).toFloat())
                gtList .add(if (gusts.isNull(i))  0f else gusts.getDouble(i).toFloat())
            }

            WindData(tsList, spList, dirList, gtList)
        } catch (_: Exception) {
            null
        }
    }
}
