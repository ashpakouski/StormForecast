package com.shpak.stormalert.presentation.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shpak.stormalert.R
import com.shpak.stormalert.presentation.ui.theme.DialogTheme

@Composable
fun PreNotificationsPermissionRequestDialog(
    onClickPositive: () -> Unit,
    onClickNegative: () -> Unit
) {
    DialogTheme {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.pre_notifications_permission_request_dialog_title),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.pre_notifications_permission_request_dialog_description),
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                ButtonPositive(onClick = onClickPositive)
            },
            dismissButton = {
                ButtonNegative(onClick = onClickNegative)
            },
            onDismissRequest = onClickNegative
        )
    }
}

@Composable
private fun ButtonPositive(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.pre_notifications_permission_request_dialog_button_positive),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ButtonNegative(onClick: () -> Unit) {
    TextButton(
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.pre_notifications_permission_request_dialog_button_negative),
        )
    }
}