package com.shpak.stormalert.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpak.stormalert.data.util.DailySummaryJob
import com.shpak.stormalert.data.util.JobScheduler
import com.shpak.stormalert.domain.repository.NotificationSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val jobScheduler: JobScheduler
) : ViewModel() {

    var state by mutableStateOf(SettingsScreenState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                isDailyForecastEnabled = notificationSettingsRepository.isDailyForecastEnabled()
            )
        }
    }

    fun onDailyForecastSwitchChange(shouldSendDailyForecast: Boolean) {
        if (shouldSendDailyForecast) {
            DailySummaryJob.schedule(jobScheduler)
        } else {
            DailySummaryJob.cancel(jobScheduler)
        }

        viewModelScope.launch {
            notificationSettingsRepository.setDailyForecastEnabled(shouldSendDailyForecast)
        }

        state = state.copy(isDailyForecastEnabled = shouldSendDailyForecast)
    }
}