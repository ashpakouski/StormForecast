package com.shpak.stormalert.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpak.stormalert.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _areNotificationsEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val areNotificationsEnabled = _areNotificationsEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            _areNotificationsEnabled.value = settingsRepository.isKpAlertEnabled()
        }
    }

    fun toggleNotificationsSwitch() {
        _areNotificationsEnabled.value = !_areNotificationsEnabled.value

        viewModelScope.launch {
            settingsRepository.setKpAlertStatus(_areNotificationsEnabled.value)
        }
    }
}