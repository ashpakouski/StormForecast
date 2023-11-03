package com.shpak.stormalert.domain.repository

interface SettingsRepository {

    suspend fun isKpAlertEnabled(): Boolean

    suspend fun setKpAlertStatus(isEnabled: Boolean)
}