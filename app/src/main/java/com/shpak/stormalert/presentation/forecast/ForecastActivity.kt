package com.shpak.stormalert.presentation.forecast

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.shpak.stormalert.presentation.common.FullscreenComponentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastActivity : FullscreenComponentActivity() {
    private val viewModel: StormForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadForecast()

        setContent {
            ForecastListUi(viewModel)
        }
    }
}