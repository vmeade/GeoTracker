package com.geotracker.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "track_points",
    foreignKeys = [ForeignKey(
        entity = Journey::class,
        parentColumns = ["id"],
        childColumns = ["journeyId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("journeyId")]
)
data class TrackPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val journeyId: Long,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val speed: Float,     // m/s
    val accuracy: Float   // metres
)
