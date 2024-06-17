package com.shpak.stormalert.presentation.forecast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpak.stormalert.domain.repository.GeomagneticRepository
import com.shpak.stormalert.domain.repository.UiInteractionRepository
import com.shpak.stormalert.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StormForecastViewModel @Inject constructor(
    private val geomagneticRepository: GeomagneticRepository,
    private val uiInteractionRepository: UiInteractionRepository
) : ViewModel() {

    var state by mutableStateOf(StormForecastState())
        private set

    fun loadForecast() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            state = state.copy(
                isPreNotificationsPermissionDialogActive = !uiInteractionRepository.isPreNotificationPermissionDialogShown()
            )

            val forecast = geomagneticRepository.getGeomagneticForecast()

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
        }

        state = state.copy(isPreNotificationsPermissionDialogActive = false)
    }
}