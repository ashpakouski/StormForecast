package com.shpak.stormalert.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shpak.stormalert.data.model.GeomagneticDataEntity

@Database(entities = [GeomagneticDataEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun forecastHistoryDao(): ForecastHistoryDao
}