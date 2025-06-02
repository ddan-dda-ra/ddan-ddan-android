package com.ddanddan.ddanddan.presentation.kangmin.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.NotSupportedScreen
import com.ddanddan.ddanddan.presentation.theme.DDanDDanTheme
import com.ddanddan.ui.ext.noRippleClickable
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun PermissionRoute(
    viewModel: PermissionViewModel = hiltViewModel(),
    navigateToCalorie: () -> Unit,
    navigateToNotSupported: () -> Unit
) {
    val context = LocalContext.current
    val permission = Manifest.permission.ACTIVITY_RECOGNITION

    var permissionGranted by remember { mutableStateOf(checkPermission(context, permission)) }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionGranted = isGranted
        }

    LaunchedEffect(Unit) {
        if (!permissionGranted) {
            permissionLauncher.launch(permission)
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PermissionSideEffect.NavigateCalories -> {
                Log.d("kangmi", "navigateCalories")
                navigateToCalorie()
            }

            is PermissionSideEffect.NavigateNotSupportCalories -> {
                Log.d("kangmi", "Not")
                navigateToNotSupported()
            }
        }
    }

    when {
        !permissionGranted -> {
            PermissionRequestScreen(
                onGoToSettings = { openAppSettings(context) }
            )
        }

        permissionGranted -> {
            viewModel.checkCalorieSupportAndRegister()
        }
    }
}

@Composable
fun PermissionRequestScreen(
    modifier: Modifier = Modifier,
    onGoToSettings: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            autoCentering = AutoCenteringParams(itemIndex = 0)
        ) {
            item {
                Text(
                    text = stringResource(R.string.need_physical_permission_message),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                        .noRippleClickable { onGoToSettings() },
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colors.primary,
                    shadowElevation = 4.dp
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        text = stringResource(R.string.open_setting_page),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

fun checkPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

@WearPreviewDevices
@Composable
fun PermissionRequestPreview() {
    DDanDDanTheme {
        PermissionRequestScreen(onGoToSettings = {})
    }
}