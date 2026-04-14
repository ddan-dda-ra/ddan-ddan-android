package com.ddanddan.ddanddan.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.data.provider.KakaoProvider
import com.ddanddan.base.R
import com.ddanddan.ddanddan.util.event.LoginEvent
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.component.DDanLoadingDialog
import com.ddanddan.ui.compose.component.DDanSnackBar
import com.google.firebase.messaging.FirebaseMessaging
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignInRoute(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateSignUp: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val signInState by signInViewModel.collectAsState()

    signInViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignInSideEffect.SuccessLogin -> onNavigateHome()
            is SignInSideEffect.UserNotRegistered -> onNavigateSignUp()
            is SignInSideEffect.NetworkError -> snackBarHostState.showSnackbar(sideEffect.msg)
        }
    }

    SignInScreen(
        signInState = signInState,
        snackBarHostState = snackBarHostState,
        onProgressBarDismiss = signInViewModel::dismissProgressBar,
        onProgressBarShow = signInViewModel::showProgressBar,
        onKakaoClick = {
            signInViewModel.logEvent(LoginEvent.ClickKakaoBtn(touchpoint = "sign-up"))
        },
        onLoginWithToken = { token ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                val deviceToken = if (it.isSuccessful) it.result else null
                signInViewModel.loginWithToken(token, deviceToken)
            }
        }
    )
}

@Composable
fun SignInScreen (
    signInState: SignInState = SignInState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onProgressBarDismiss: () -> Unit = { },
    onProgressBarShow: () -> Unit = { },
    onKakaoClick: () -> Unit = {},
    onLoginWithToken: (String) -> Unit = { }
) {
    val context = LocalContext.current // Context 가져오기
    val kakaoProvider = remember { KakaoProvider(context) }

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
            Spacer(modifier = Modifier.height(196.dp))
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
            KakaoButton(
                onButtonClick = {
                    onKakaoClick()
                    onProgressBarShow()
                    kakaoProvider.loginWithKakao { token, error ->
                        if (error == null) token?.accessToken?.let {
                            onLoginWithToken(it)
                        }
                        else {
                            onProgressBarDismiss()
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
    if (signInState.isShowProgressBar) {
        DDanLoadingDialog(
            onDismiss = onProgressBarDismiss
        )
    }
}

@Composable
fun KakaoButton(
    onButtonClick: () -> Unit = {}
) {
    Button(
        onClick = { onButtonClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(56.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DDanDDanColorPalette.current.color_graphic_kakao,
            contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
        )
    ) {
        Text(text = stringResource(R.string.signin_button_text), style = DDanDDanTypo.current.HeadLine6)
    }

}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF111111)
fun SignInScreenPreview() {
    SignInScreen()
}