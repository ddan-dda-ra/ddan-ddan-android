package com.ddanddan.ddanddan.presentation.setting.target

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
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.setting.SettingSideEffect
import com.ddanddan.ddanddan.presentation.setting.SettingState
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ddanddan.util.event.MyPageEvent
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginHorizontalSpacer
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DDanSnackBar
import com.ddanddan.ui.compose.component.DdanScaffold
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun EditTargetRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigatePopUp: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val settingState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingSideEffect.NavigatePopUp -> navigatePopUp()
            is SettingSideEffect.SuccessChange -> navigatePopUp()
            is SettingSideEffect.NetworkError -> {
                snackBarHostState.showSnackbar(sideEffect.msg)
            }

            else -> {}
        }
    }

    EditTargetScreen(
        settingState = settingState,
        snackBarHostState = snackBarHostState,
        navigatePopUp = viewModel::navigatePopUp,
        onPlusBtnClick = viewModel::incrementTarget,
        onMinusBtnClick = {
            viewModel.logEvent(MyPageEvent.ClickMinusBtn(touchpoint = "mypage-change-goal"))
            viewModel.decrementTarget()
        },
        onEditBtnClick = {
            viewModel.logEvent(MyPageEvent.ClickSavedCTA(touchpoint = "mypage-change-goal"))
            viewModel.onEditBtnClick()
        }
    )
}

@Composable
fun EditTargetScreen(
    settingState: SettingState = SettingState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navigatePopUp: () -> Unit = {},
    onPlusBtnClick: () -> Unit = {},
    onMinusBtnClick: () -> Unit = {},
    onEditBtnClick: () -> Unit = {}
) {
    DdanScaffold(
        topbarText = stringResource(id = R.string.edittarget_topbar_title),
        snackbarHost = {
            DDanSnackBar(snackBarHostState = snackBarHostState)
        },
        onClick = {
            navigatePopUp()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            DDanMarginVerticalSpacer(size = 32)
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = R.string.edittarget_screen_title),
                style = DDanDDanTypo.current.HeadLine3,
                fontFamily = NeoDgm,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            DDanMarginVerticalSpacer(size = 56)
            EditTargetControl(
                target = settingState.calorie,
                onPlusBtnClick = onPlusBtnClick,
                onMinusBtnClick = onMinusBtnClick
            )
            Spacer(modifier = Modifier.weight(1f))
            EditTargetBtn(
                text = stringResource(id = R.string.edittarget_button_text),
                target = settingState.calorie,
                onClick = onEditBtnClick
            )
            DDanMarginVerticalSpacer(size = 20)
        }
    }
}

@Composable
fun EditTargetControl(
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
            androidx.compose.material3.Text(
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


@Composable
fun EditTargetBtn(
    text: String,
    target: Int,
    onClick: () -> Unit = {}
) {
    // 각 필드의 현재 상태를 수집
    val isAllValid = target in 100..1000

    val buttonColors =
        if (isAllValid) DDanDDanColorPalette.current.color_button_active else DDanDDanColorPalette.current.color_button_disabled
    val textColors =
        if (isAllValid) DDanDDanColorPalette.current.color_text_button_primary_default else DDanDDanColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = isAllValid,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors,
            disabledContainerColor = buttonColors
        ),
        shape = RoundedCornerShape(0.dp),
    ) {
        Text(
            text = text,
            style = DDanDDanTypo.current.HeadLine6,
            color = textColors
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
fun EditTargetPreview() {
    EditTargetScreen()
}