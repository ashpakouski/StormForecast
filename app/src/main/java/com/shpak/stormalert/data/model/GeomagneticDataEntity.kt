package com.shpak.stormalert.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.shpak.stormalert.data.mappers.DateConverter
import java.util.Date

@Entity(
    tableName = "forecast_history",
    indices = [Index(value = ["date_scheduled"])]
)
@TypeConverters(DateConverter::class)
data class GeomagneticDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "date_scheduled")
    val dateScheduled: Date,

    @ColumnInfo(name = "kp_value")
    val kpValue: Double,

    @ColumnInfo(name = "date_requested")
    val dateRequested: Date
)