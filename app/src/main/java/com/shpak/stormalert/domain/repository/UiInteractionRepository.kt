package com.shpak.stormalert.domain.repository

interface UiInteractionRepository {
    suspend fun isPreNotificationPermissionDialogShown(): Boolean
    suspend fun setPreNotificationPermissionDialogShown()
}