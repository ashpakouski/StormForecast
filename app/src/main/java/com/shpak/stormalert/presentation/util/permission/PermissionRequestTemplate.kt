package com.shpak.stormalert.presentation.util.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.shpak.stormalert.presentation.util.ActivityLifecycle
import com.shpak.stormalert.presentation.util.permission.PermissionRequestState.GRANTED
import com.shpak.stormalert.presentation.util.permission.PermissionRequestState.NOT_REQUESTED
import com.shpak.stormalert.presentation.util.permission.PermissionRequestState.PERMANENTLY_DENIED
import com.shpak.stormalert.presentation.util.permission.PermissionRequestState.SHOW_RATIONALE
import com.shpak.stormalert.presentation.util.permission.PermissionRequestState.UNSPECIFIED

@Composable
fun PermissionRequestTemplate(
    permission: String,
    viewModel: GenericPermissionRequestViewModel = hiltViewModel(),
    onNotRequested: (@Composable (onProceed: () -> Unit) -> Unit)? = null,
    onGranted: (@Composable () -> Unit)? = null,
    onRationale: (@Composable (onProceed: () -> Unit) -> Unit)? = null,
    onPermanentlyDenied: (@Composable (onProceed: () -> Unit) -> Unit)? = null
) {
    val context = LocalContext.current
    val activity = (context as? ComponentActivity)
        ?: throw Exception("Method should be called from ComponentActivity context")

    fun updateStatus() {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED

        val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity, permission
        )

        when {
            isPermissionGranted -> viewModel.onGranted()
            shouldShowRationale -> viewModel.onShouldShowRationale()
            else -> viewModel.onPermanentlyDeniedOrNotRequested()
        }
    }

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { _ ->
            updateStatus()
        }
    )

    fun launchRequest() {
        viewModel.onRequest()
        permissionResultLauncher.launch(permission)
    }

    ActivityLifecycle(
        onStart = { updateStatus() }
    )

    when (viewModel.state) {
        NOT_REQUESTED -> onNotRequested?.invoke(::launchRequest) ?: SideEffect { launchRequest() }
        SHOW_RATIONALE -> onRationale?.invoke(::launchRequest)
        PERMANENTLY_DENIED -> onPermanentlyDenied?.invoke(context::openAppSettings)
        GRANTED -> onGranted?.invoke()
        UNSPECIFIED -> Unit
    }
}

private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}