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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@Composable
fun NotificationsPermissionConsentDialogRegular() {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        },
        onDismissRequest = { },
        title = {
            Text(
                text = "Want to stay ahead of Magnetic Storms?",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = "Get quick updates on geomagnetic activity like upcoming storms, Kp index changes and daily forecasts. You can adjust your notifications anytime in settings.",
                fontSize = 14.sp
            )
        },
        confirmButton = {
            Button(onClick = {

            }) {
                Text("Yes, notify me")
            }
        },
        dismissButton = {
            TextButton(onClick = { }) {
                Text("No, thanks")
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    )
}