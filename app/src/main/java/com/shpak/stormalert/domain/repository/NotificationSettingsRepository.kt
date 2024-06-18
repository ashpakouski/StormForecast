package com.shpak.stormalert.domain.repository

interface NotificationSettingsRepository {

    suspend fun areNotificationsEnabled(): Boolean

    suspend fun setNotificationsEnabled(areEnabled: Boolean)
}