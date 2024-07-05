package com.shpak.stormalert.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shpak.stormalert.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        stringResource(R.string.settings_screen_title),
                        fontWeight = FontWeight.W500
                    )
                },
            )
        },
    ) { innerPadding ->
        SettingsScreen(viewModel, innerPadding)
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
    ) {
        SettingsSection(
            name = stringResource(R.string.settings_section_notifications),
            modifier = Modifier.padding(16.dp)
        ) {
            SettingsSwitch(
                title = stringResource(R.string.setting_daily_forecast_title),
                subtitle = stringResource(R.string.setting_daily_forecast_description),
                isChecked = viewModel.state.isDailyForecastEnabled,
                onCheckedChange = viewModel::onDailyForecastSwitchChange
            )
        }

        SettingsSection(
            name = stringResource(R.string.settings_section_available_soon),
            modifier = Modifier.padding(16.dp)
        ) {
            SettingsSwitch(
                title = stringResource(R.string.setting_storm_ahead_title),
                subtitle = stringResource(R.string.setting_storm_ahead_description),
                isChecked = false,
                onCheckedChange = {},
                isEnabled = false
            )

            SettingsSwitch(
                title = stringResource(R.string.setting_sudden_increase_title),
                subtitle = stringResource(R.string.setting_sudden_increase_description),
                isChecked = false,
                onCheckedChange = {},
                isEnabled = false
            )
        }
    }
}

@Composable
private fun SettingsSection(
    name: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun SettingsSwitch(
    title: String,
    subtitle: String? = null,
    isChecked: Boolean,
    onCheckedChange: (isChecked: Boolean) -> Unit,
    actionButtonTitle: String? = null,
    onActionButtonClick: (() -> Unit)? = null,
    isEnabled: Boolean = true
) {
    assert((actionButtonTitle == null && onActionButtonClick == null) || (actionButtonTitle != null && onActionButtonClick != null))

    Column(
        modifier = Modifier.alpha(if (isEnabled) 1.0f else 0.5f)
    ) {
        DecoratedSwitch(
            title = title,
            subtitle = subtitle,
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
            isEnabled = isEnabled
        )

        if (actionButtonTitle != null && onActionButtonClick != null) {
            ConfigurationButton(
                title = actionButtonTitle,
                onActionButtonClick = onActionButtonClick,
                modifier = Modifier.padding(
                    top = 4.dp
                )
            )
        }
    }
}

@Composable
private fun DecoratedSwitch(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (isChecked: Boolean) -> Unit,
    subtitle: String? = null,
    isEnabled: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!isChecked)
            }
    ) {
        Column(
            modifier = Modifier
                .weight(1.0f)
                .padding(end = 16.dp)
        ) {
            Text(text = title, fontSize = 22.sp)

            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.alpha(0.8f)
                )
            }
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            enabled = isEnabled
        )
    }
}

@Composable
private fun ConfigurationButton(
    title: String,
    onActionButtonClick: (() -> Unit),
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.clickable(onClick = onActionButtonClick)
    )
}