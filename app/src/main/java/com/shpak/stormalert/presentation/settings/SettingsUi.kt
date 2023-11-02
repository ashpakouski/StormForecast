package com.shpak.stormalert.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shpak.stormalert.R
import com.shpak.stormalert.presentation.ui.theme.StormAlertTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsUi(viewModel: SettingsViewModel) {
    StormAlertTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        title = {
                            Text(
                                stringResource(R.string.settings_title),
                                fontWeight = FontWeight(500)
                            )
                        },
                    )
                },
            ) { innerPadding ->
                SettingsScreen(viewModel, innerPadding)
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    viewModel: SettingsViewModel,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        SettingsGroup(R.string.settings_group_notifications) {
            SettingsSwitch(
                icon = R.drawable.ic_launcher_foreground,
                iconDesc = R.string.error_forecast_fetch_failed,
                name = R.string.app_name,
                state = viewModel.areNotificationsEnabled.collectAsState()
            ) {
                viewModel.toggleNotificationsSwitch()
            }
        }
    }
}