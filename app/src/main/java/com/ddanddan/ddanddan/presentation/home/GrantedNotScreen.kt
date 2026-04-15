package com.ddanddan.ddanddan.presentation.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ddanddan.ddanddan.presentation.splash.SplashSideEffect
import com.ddanddan.ddanddan.presentation.splash.SplashViewModel
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.theme.DDanDDanTheme
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun GrantedNotRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    onNavigateSignIn: () -> Unit,
) {
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.NavigateSignIn -> onNavigateSignIn()
            is SplashSideEffect.NavigateHome -> onNavigateHome()
            else -> {}
        }
    }
    GrantedNotScreen(
        onPermissionGranted = {
            viewModel.isAutoLoginEnabled()
        }
    )
}

@Composable
fun GrantedNotScreen(
    onPermissionGranted: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        }
    }

    val requestPermission = {
        permissionLauncher.launch(Manifest.permission.BODY_SENSORS)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BODY_SENSORS
                ) == PackageManager.PERMISSION_GRANTED

                if (hasPermission) {
                    onPermissionGranted()
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val openAppSettings = {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }

    val onButtonClick = {
        activity?.let {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(it, Manifest.permission.BODY_SENSORS)) {
                requestPermission()
            } else {
                openAppSettings()
            }
        } ?: run {
            openAppSettings()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DDanDDanColorPalette.current.color_background)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(DDanDDanColorPalette.current.elevation_color_elevation_level01)
                .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "생체 신호 센서 접근 권한을 허용해주세요",
                style = DDanDDanTypo.current.HeadLine6,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "서비스 이용을 위하여\n생체 신호 센서 접근 권한이 필요합니다.",
                style = DDanDDanTypo.current.Body2,
                color = DDanDDanColorPalette.current.color_text_body_teritary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onPermissionGranted() },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 17.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DDanDDanColorPalette.current.color_button_alternative,
                        contentColor = DDanDDanColorPalette.current.color_text_button_alternative
                    ),
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "허용 안함", style = DDanDDanTypo.current.HeadLine6, color = DDanDDanColorPalette.current.color_text_button_alternative)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onButtonClick() },
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 17.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DDanDDanColorPalette.current.color_button_active,
                        contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
                    ),
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "허용", style = DDanDDanTypo.current.HeadLine6, color = DDanDDanColorPalette.current.color_text_button_primary_default)
                }
            }
        }
    }
}

@Preview
@Composable
fun GrantedNotScreenPreview() {
    DDanDDanTheme {
        GrantedNotScreen(
            onPermissionGranted = {}
        )
    }
}