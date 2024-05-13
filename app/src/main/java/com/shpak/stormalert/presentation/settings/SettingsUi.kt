package com.shpak.stormalert.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        SettingsSwitch(
            title = "Storm ahead",
            subtitle = "Storm with a Kp index >= N is forecasted to occur in the near future",
            isChecked = true,
            onCheckedChange = {},
            actionButtonTitle = "Configure alert",
            onActionButtonClick = {}
        )

        Spacer(modifier = Modifier.size(height = 16.dp, width = 1.dp))

        SettingsSwitch(
            title = "Sudden increase",
            subtitle = "Current Kp index shows an unexpected increase compared to forecasted values",
            isChecked = false,
            onCheckedChange = {},
            actionButtonTitle = "Configure alert",
            onActionButtonClick = {}
        )

        Spacer(modifier = Modifier.size(height = 16.dp, width = 1.dp))

        SettingsSwitch(
            title = "Daily forecast",
            subtitle = "Receive a daily summary of the expected magnetic activity level for the following day",
            isChecked = true,
            onCheckedChange = {},
            actionButtonTitle = "Configure alert",
            onActionButtonClick = {}
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
    onActionButtonClick: (() -> Unit)? = null
) {
    assert((actionButtonTitle == null && onActionButtonClick == null) || (actionButtonTitle != null && onActionButtonClick != null))

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(color = Color.Black),
                onClick = {},
            )
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
                onCheckedChange = onCheckedChange
            )
        }

        if (actionButtonTitle != null && onActionButtonClick != null) {
            Text(
                text = actionButtonTitle,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable(onClick = onActionButtonClick)
            )
        }
    }
}