package com.shpak.stormalert.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpak.stormalert.domain.repository.GeomagneticRepository
import com.shpak.stormalert.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StormForecastViewModel @Inject constructor(
    private val geomagneticRepository: GeomagneticRepository
) : ViewModel() {

    var state by mutableStateOf(StormForecastState())
        private set

    fun loadForecast() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val forecast = geomagneticRepository.getGeomagneticForecast()

            state = when (forecast) {
                is Resource.Success -> {
                    state.copy(isLoading = false, forecast = forecast.data)
                }

                is Resource.Error -> {
                    state.copy(isLoading = false, isError = true)
                }
            }

            Log.d("TAG", "${forecast.data}")
        }
    }
}