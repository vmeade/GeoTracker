package com.geotracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journeys")
data class Journey(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val startTime: Long,
    val endTime: Long = 0,
    val totalDistance: Float = 0f,   // metres
    val avgSpeed: Float = 0f,         // m/s
    val maxSpeed: Float = 0f,         // m/s
    val minElevation: Double = 0.0,
    val maxElevation: Double = 0.0,
    val elevationGain: Double = 0.0,
    val pointCount: Int = 0
)
