package com.geotracker.database

import androidx.room.*

@Dao
interface TrackPointDao {
    @Query("SELECT * FROM track_points WHERE journeyId = :journeyId ORDER BY timestamp ASC")
    suspend fun getPointsForJourney(journeyId: Long): List<TrackPoint>

    @Insert
    suspend fun insertPoint(point: TrackPoint): Long
}
