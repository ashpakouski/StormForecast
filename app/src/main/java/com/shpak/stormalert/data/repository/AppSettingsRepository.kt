package com.shpak.stormalert.data.repository

import android.content.Context
import androidx.preference.PreferenceManager
import com.shpak.stormalert.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppSettingsRepository @Inject constructor(context: Context) : SettingsRepository {
    companion object {
        private const val KEY_KP_ALERT_STATUS = "kp_alert_status"
    }

    private val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)

    override suspend fun isKpAlertEnabled(): Boolean {
        return withContext(Dispatchers.IO) {
            preferenceManager.getBoolean(KEY_KP_ALERT_STATUS, true)
        }
    }

    override suspend fun setKpAlertStatus(isEnabled: Boolean) {
        withContext(Dispatchers.IO) {
            val editor = preferenceManager.edit()
            editor.putBoolean(KEY_KP_ALERT_STATUS, isEnabled)
            editor.commit()
        }
    }
}