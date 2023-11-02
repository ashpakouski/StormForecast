package com.shpak.stormalert.presentation.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _areNotificationsEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val areNotificationsEnabled = _areNotificationsEnabled.asStateFlow()

    fun toggleNotificationsSwitch() {
        _areNotificationsEnabled.value = !_areNotificationsEnabled.value
    }
}