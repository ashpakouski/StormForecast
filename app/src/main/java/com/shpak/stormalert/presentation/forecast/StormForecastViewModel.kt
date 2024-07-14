package com.shpak.stormalert.presentation.forecast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpak.stormalert.data.util.DailySummaryJob
import com.shpak.stormalert.data.util.JobScheduler
import com.shpak.stormalert.domain.repository.GeomagneticForecastRepository
import com.shpak.stormalert.domain.repository.NotificationSettingsRepository
import com.shpak.stormalert.domain.repository.UiInteractionRepository
import com.shpak.stormalert.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StormForecastViewModel @Inject constructor(
    private val forecastRepository: GeomagneticForecastRepository,
    private val uiInteractionRepository: UiInteractionRepository,
    private val notificationSettingsRepository: NotificationSettingsRepository,
    private val jobScheduler: JobScheduler
) : ViewModel() {

    var state by mutableStateOf(StormForecastState())
        private set

    fun loadForecast() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            state = state.copy(
                isPreNotificationsPermissionDialogActive = !uiInteractionRepository.isPreNotificationPermissionDialogShown()
            )

            val forecast = forecastRepository.getGeomagneticForecast()

            state = when (forecast) {
                is Resource.Success -> {
                    state.copy(isLoading = false, forecast = forecast.data)
                }

                is Resource.Error -> {
                    state.copy(isLoading = false, isError = true)
                }
            }
        }
    }

    fun onPreNotificationsPermissionDialogResult(isRequestApproved: Boolean) {
        viewModelScope.launch {
            uiInteractionRepository.setPreNotificationPermissionDialogShown()
            notificationSettingsRepository.setNotificationsEnabled(isRequestApproved)
        }

        state = state.copy(isPreNotificationsPermissionDialogActive = false)

        if (isRequestApproved) {
            scheduleTestWork()
        }
    }

    private fun scheduleTestWork() {
        DailySummaryJob.schedule(jobScheduler)
    }
}