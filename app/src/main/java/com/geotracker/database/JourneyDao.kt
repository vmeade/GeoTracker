package com.geotracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JourneyDao {
    @Query("SELECT * FROM journeys ORDER BY startTime DESC")
    fun getAllJourneys(): LiveData<List<Journey>>

    @Query("SELECT * FROM journeys ORDER BY startTime DESC LIMIT 5")
    fun getRecentJourneys(): LiveData<List<Journey>>

    @Query("SELECT * FROM journeys WHERE id = :id")
    suspend fun getJourneyById(id: Long): Journey?

    @Query("SELECT COUNT(*) FROM journeys WHERE endTime > 0")
    suspend fun getJourneyCount(): Int

    @Query("SELECT SUM(totalDistance) FROM journeys WHERE endTime > 0")
    suspend fun getTotalDistance(): Float?

    @Query("SELECT MAX(maxSpeed) FROM journeys WHERE endTime > 0")
    suspend fun getOverallMaxSpeed(): Float?

    @Insert
    suspend fun insertJourney(journey: Journey): Long

    @Update
    suspend fun updateJourney(journey: Journey)

    @Delete
    suspend fun deleteJourney(journey: Journey)
}
