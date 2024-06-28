package com.shpak.stormalert.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shpak.stormalert.data.util.get
import com.shpak.stormalert.data.util.set
import com.shpak.stormalert.domain.repository.NotificationSettingsRepository
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("notification_settings_data_store")

class AppNotificationSettingsRepository @Inject constructor(
    private val context: Context
) : NotificationSettingsRepository {

    private companion object {
        val KEY_ARE_NOTIFICATIONS_ENABLED = booleanPreferencesKey("key_notifications_enabled")
        val KEY_IS_DAILY_FORECAST_ENABLED = booleanPreferencesKey("key_daily_forecast_enabled")
    }

    override suspend fun areNotificationsEnabled(): Boolean =
        context.dataStore.get(KEY_ARE_NOTIFICATIONS_ENABLED, false)

    override suspend fun setNotificationsEnabled(areEnabled: Boolean) =
        context.dataStore.set(KEY_ARE_NOTIFICATIONS_ENABLED, areEnabled)

    override suspend fun isDailyForecastEnabled(): Boolean =
        context.dataStore.get(KEY_IS_DAILY_FORECAST_ENABLED, true)

    override suspend fun setDailyForecastEnabled(isEnabled: Boolean) =
        context.dataStore.set(KEY_IS_DAILY_FORECAST_ENABLED, isEnabled)
}