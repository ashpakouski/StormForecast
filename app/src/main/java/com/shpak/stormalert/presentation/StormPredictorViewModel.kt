package com.shpak.stormalert.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpak.stormalert.domain.repository.GeomagneticRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StormPredictorViewModel @Inject constructor(
    private val geomagneticRepository: GeomagneticRepository
) : ViewModel() {

    fun requestData() {
        viewModelScope.launch {
            val forecast = geomagneticRepository.getGeomagneticForecast()

            Log.d("TAG", "${forecast.data}")
        }
    }
}