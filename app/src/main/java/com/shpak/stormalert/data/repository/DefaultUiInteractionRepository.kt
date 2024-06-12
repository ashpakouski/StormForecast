package com.shpak.stormalert.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shpak.stormalert.data.util.get
import com.shpak.stormalert.data.util.set
import com.shpak.stormalert.domain.repository.UiInteractionRepository
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dialog_interaction_data_store")

class DefaultUiInteractionRepository @Inject constructor(
    private val context: Context
) : UiInteractionRepository {

    private companion object {
        val KEY_PRE_NOTIFICATIONS_PERMISSION_DIALOG_SHOWN =
            booleanPreferencesKey("key_pre_notifications_permission_dialog_shown")
    }

    override suspend fun isPreNotificationPermissionDialogShown(): Boolean =
        context.dataStore.get(KEY_PRE_NOTIFICATIONS_PERMISSION_DIALOG_SHOWN, false)

    override suspend fun setPreNotificationPermissionDialogShown() =
        context.dataStore.set(KEY_PRE_NOTIFICATIONS_PERMISSION_DIALOG_SHOWN, true)
}