package com.ddanddan.ddanddan.presentation.splash

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.base.R
import com.ddanddan.ddanddan.util.NetworkManager
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.component.DDanSnackBar
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SplashRoute(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    onNavigateSignIn: () -> Unit,
    onNavigateGrantNotPermission: () -> Unit,
    onNavigateOnboarding: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    splashViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.NavigateSignIn -> onNavigateSignIn()
            is SplashSideEffect.NavigateHome -> onNavigateHome()
            is SplashSideEffect.NavigateOnboarding -> onNavigateOnboarding()
            is SplashSideEffect.NavigateGrantNotPermission -> onNavigateGrantNotPermission()
            is SplashSideEffect.NetworkError -> snackBarHostState.showSnackbar("인터넷 연결이 필요합니다.")
        }
    }

    SplashScreen(
        snackBarHostState = snackBarHostState,
        onCheckNavigate = splashViewModel::isFirstAfterInstall,
        onNetworkDisconnected = splashViewModel::disconnectedNetwork,
        onGrantNotPermission = splashViewModel::grantNotPermission
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
fun SplashScreen(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onCheckNavigate: () -> Unit = { },
    onNetworkDisconnected: () -> Unit = { },
    onGrantNotPermission: () -> Unit = { }
) {
    val context = LocalContext.current

    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onCheckNavigate()
        } else {
            onGrantNotPermission()
        }
    }

    LaunchedEffect(Unit) {
        if (!NetworkManager.checkNetworkState(context)) {
            onNetworkDisconnected()
            return@LaunchedEffect
        }
        delay(3000)

        hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BODY_SENSORS
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        if (hasPermission) {
            onCheckNavigate()
        } else {
            permissionLauncher.launch(Manifest.permission.BODY_SENSORS)
        }
    }

    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background,
        snackbarHost = {
            DDanSnackBar(snackBarHostState = snackBarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            ConstraintLayout {
                val (logoImage, dumbbellImage) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_ddanddan),
                    contentDescription = "로고 이미지",
                    modifier = Modifier.constrainAs(logoImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_dumbbell),
                    contentDescription = "아령 이미지",
                    modifier = Modifier.constrainAs(dumbbellImage) {
                        top.linkTo(parent.top, margin = 65.dp)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Text(text = "~", style = DDanDDanTypo.current.NeoDgm24, color = DDanDDanColorPalette.current.GreyWhite)
                Spacer(modifier = Modifier.width((5.37).dp))
                Text(text = "START", style = DDanDDanTypo.current.NeoDgm24, color = DDanDDanColorPalette.current.GreyWhite)
                Spacer(modifier = Modifier.width((5.37).dp))
                Text(text = "~", style = DDanDDanTypo.current.NeoDgm24, color = DDanDDanColorPalette.current.GreyWhite)
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}