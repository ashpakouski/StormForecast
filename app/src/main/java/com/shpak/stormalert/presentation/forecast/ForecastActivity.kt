package com.shpak.stormalert.presentation.forecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastActivity : ComponentActivity() {
    private val viewModel: StormForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadForecast()

        enableEdgeToEdge()

        setContent {
            ForecastListUi(viewModel)
        }
    }
}