package com.ddanddan.ddanddan.presentation.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    onNavigateOnboarding: () -> Unit
) {
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.NavigateSignIn -> onNavigateSignIn()
            is SplashSideEffect.NavigateHome -> onNavigateHome()
            is SplashSideEffect.NavigateOnboarding -> onNavigateOnboarding()
            else -> {}
        }
    }
    GrantedNotScreen(
        onPermissionGranted = {
            viewModel.isFirstAfterInstall()
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

    // 권한 요청 런처
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        }
    }

    // 권한 요청 함수
    val requestPermission = {
        permissionLauncher.launch(Manifest.permission.BODY_SENSORS)
    }

    // 앱이 포그라운드로 돌아올 때마다 권한 상태 확인
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

    // 설정 화면으로 이동하는 함수
    val openAppSettings = {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }

    // 버튼 클릭 시 호출되는 함수
    val onButtonClick = {
        activity?.let {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(it, Manifest.permission.BODY_SENSORS)) {
                // 아직 시스템 팝업을 띄울 수 있으면 팝업 표시
                requestPermission()
            } else {
                // 더 이상 시스템 팝업을 띄울 수 없으면 설정으로 이동
                openAppSettings()
            }
        } ?: run {
            // activity가 null이면 안전하게 설정으로 이동
            openAppSettings()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "권한이 필요합니다",
            style = DDanDDanTypo.current.HeadLine5,
            textAlign = TextAlign.Center,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "이 앱은 건강 데이터를 수집하여 칼로리 소모량을 계산하기 위해 생체 신호 센서 권한이 필요합니다. 권한을 허용하지 않으면 앱의 핵심 기능을 사용하실 수 없습니다.",
            style = DDanDDanTypo.current.HeadLine5,
            textAlign = TextAlign.Center,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DDanDDanColorPalette.current.color_button_active,
                contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
            ),
            elevation = ButtonDefaults.elevation(0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            androidx.compose.material.Text(
                text = "권한 허용하기",
                style = DDanDDanTypo.current.HeadLine6
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "권한을 허용하지 않으면 앱을 사용하실 수 없습니다.",
            style = DDanDDanTypo.current.Body2,
            color = DDanDDanColorPalette.current.color_text_body_secondary,
            textAlign = TextAlign.Center
        )
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