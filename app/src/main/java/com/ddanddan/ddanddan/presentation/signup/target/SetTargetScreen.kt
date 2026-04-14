package com.ddanddan.ddanddan.presentation.signup.target

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.setting.target.EditTargetBtn
import com.ddanddan.ddanddan.presentation.signup.SignUpSideEffect
import com.ddanddan.ddanddan.presentation.signup.SignUpState
import com.ddanddan.ddanddan.presentation.signup.SignUpViewModel
import com.ddanddan.ddanddan.util.event.SignUpEvent
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginHorizontalSpacer
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SetTargetRoute(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    onNavigatePetType: () -> Unit,
    onNavigateSetName: () -> Unit
) {
    val signUpState by signUpViewModel.collectAsState()

    signUpViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.NavigateFirstEgg -> {
                onNavigatePetType()
            }
            is SignUpSideEffect.NavigateNickname -> {
                onNavigateSetName()
            }
            else -> {}
        }
    }

    SetTargetScreen(
        signUpState = signUpState,
        onPlusBtnClick = signUpViewModel::incrementTarget,
        onMinusBtnClick = signUpViewModel::decrementTarget,
        onNextBtnClick = {
            signUpViewModel.logEvent(SignUpEvent.ClickCTA(touchpoint = "sign-up-goal"))
            onNavigatePetType()
        }
    )
}

@Composable
@Preview
fun SetTargetScreen(
    signUpState: SignUpState = SignUpState(),
    onPlusBtnClick: () -> Unit = {},
    onMinusBtnClick: () -> Unit = {},
    onNextBtnClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(52.dp))
            DDanMarginVerticalSpacer(size = 32)
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = R.string.edittarget_screen_title),
                style = DDanDDanTypo.current.HeadLine3,
                fontFamily = NeoDgm,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            DDanMarginVerticalSpacer(size = 56)
            SetTargetControl(
                target = signUpState.calorie,
                onPlusBtnClick = onPlusBtnClick,
                onMinusBtnClick = onMinusBtnClick
            )
            Spacer(modifier = Modifier.weight(1f))
            EditTargetBtn(
                text = stringResource(R.string.signup_target_button_text),
                target = signUpState.calorie,
                onClick = onNextBtnClick
            )
            DDanMarginVerticalSpacer(size = 20)
        }

    }

}

@Composable
fun SetTargetControl(
    target: Int,
    onPlusBtnClick: () -> Unit = {},
    onMinusBtnClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.btn_system_minus_fill),
            contentDescription = "image description",
            contentScale = ContentScale.None,
            modifier = Modifier.clickable {
                onMinusBtnClick()
            }
        )
        DDanMarginHorizontalSpacer(size = 20)
        Box(
            modifier = Modifier
                .size(width = 88.dp, height = 80.dp)
                .background(
                    color = DDanDDanColorPalette.current.elevation_color_elevation_level01,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = target.toString(),
                style = DDanDDanTypo.current.HeadLine4,
                color = DDanDDanColorPalette.current.color_text_button_secondary_default,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        DDanMarginHorizontalSpacer(size = 20)
        Image(
            painter = painterResource(id = R.drawable.icon_system_plus),
            contentDescription = "image description",
            contentScale = ContentScale.None,
            modifier = Modifier.clickable {
                onPlusBtnClick()
            }
        )
    }
}