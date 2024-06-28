package com.shpak.stormalert.domain.repository

interface NotificationSettingsRepository {

    suspend fun areNotificationsEnabled(): Boolean

    suspend fun setNotificationsEnabled(areEnabled: Boolean)

    suspend fun isDailyForecastEnabled(): Boolean

    suspend fun setDailyForecastEnabled(isEnabled: Boolean)
}