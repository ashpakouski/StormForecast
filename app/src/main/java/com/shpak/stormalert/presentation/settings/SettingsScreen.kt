package com.shpak.stormalert.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
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
//        SettingsSwitch(
//            title = "Storm ahead",
//            subtitle = "Storm with a Kp index >= N is forecasted to occur in the near future",
//            isChecked = true,
//            onCheckedChange = {},
//            actionButtonTitle = "Configure alert",
//            onActionButtonClick = {},
//            paddingHorizontal = 16.dp
//        )
//
//        Spacer(modifier = Modifier.size(height = 16.dp, width = 1.dp))
//
//        SettingsSwitch(
//            title = "Sudden increase",
//            subtitle = "Current Kp index shows an unexpected increase compared to forecasted values",
//            isChecked = false,
//            onCheckedChange = {},
//            actionButtonTitle = "Configure alert",
//            onActionButtonClick = {},
//            paddingHorizontal = 16.dp
//        )
//
//        Spacer(modifier = Modifier.size(height = 16.dp, width = 1.dp))

        SettingsSwitch(
            title = "Daily forecast",
            subtitle = "Receive a daily summary of the expected magnetic activity level for the following day",
            isChecked = viewModel.state.isDailyForecastEnabled,
            onCheckedChange = viewModel::onDailyForecastSwitchChange,
            paddingHorizontal = 16.dp
        )
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
    paddingHorizontal: Dp = 0.dp
) {
    assert((actionButtonTitle == null && onActionButtonClick == null) || (actionButtonTitle != null && onActionButtonClick != null))

    Column {
        SwitchDecorated(
            title = title,
            subtitle = subtitle,
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
            paddingHorizontal = paddingHorizontal
        )

        if (actionButtonTitle != null && onActionButtonClick != null) {
            ConfigurationButton(
                title = actionButtonTitle,
                onActionButtonClick = onActionButtonClick,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = paddingHorizontal)
            )
        }
    }
}

@Composable
private fun SwitchDecorated(
    title: String,
    subtitle: String? = null,
    isChecked: Boolean,
    onCheckedChange: (isChecked: Boolean) -> Unit,
    paddingHorizontal: Dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!isChecked)
            }
    ) {
        Spacer(modifier = Modifier.width(paddingHorizontal))

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
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(paddingHorizontal))
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