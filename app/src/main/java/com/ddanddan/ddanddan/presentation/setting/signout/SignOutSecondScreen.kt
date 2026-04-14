package com.ddanddan.ddanddan.presentation.setting.signout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.setting.SettingSideEffect
import com.ddanddan.ddanddan.presentation.setting.SettingState
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.component.DDanMarginHorizontalSpacer
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DdanScaffold
import com.ddanddan.ui.ext.noRippleClickable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.ddanddan.base.R.drawable
import com.ddanddan.ddanddan.util.event.MyPageEvent

@Composable
fun SignOutSecondRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigatePopUp: () -> Unit,
    navigateOnBoarding: () -> Unit
) {
    val settingState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingSideEffect.NavigatePopUp -> navigatePopUp()
            is SettingSideEffect.NavigateOnBoarding -> navigateOnBoarding()
            else -> {}
        }
    }

    SignOutSecondScreen(
        settingState = settingState,
        navigatePopUp = viewModel::navigatePopUp,
        onToggleClick = {
            viewModel.logEvent(MyPageEvent.ClickCheckboxRecheck(touchpoint = "mypage-delect-account"))
            viewModel.onToggleClick()
        },
        onDeleteUserClick = {
            viewModel.logEvent(MyPageEvent.ClickCTABtn(touchpoint = "mypage-delect-account"))
            viewModel.deleteUser()
        }
    )
}

@Composable
fun SignOutSecondScreen(
    settingState: SettingState = SettingState(),
    navigatePopUp: () -> Unit = {},
    onToggleClick: () -> Unit = {},
    onDeleteUserClick: () -> Unit = {}
) {
    DdanScaffold(
        onClick = { navigatePopUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DDanMarginVerticalSpacer(size = 36)
            Text(
                text = "${settingState.nickName}님\n탈퇴하기 전 확인해 주세요",
                style = DDanDDanTypo.current.NeoDgm24,
                color = DDanDDanColorPalette.current.color_text_headline_primary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            DDanMarginVerticalSpacer(size = 8)
            Text(
                text = stringResource(id = R.string.setting_signout_text1),
                style = DDanDDanTypo.current.Body1,
                color = DDanDDanColorPalette.current.color_text_body_quaternary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            DDanMarginVerticalSpacer(size = 56)
            SignOutImageColumn()
            Spacer(modifier = Modifier.weight(1f))
            SignOutConfirmCheck(
                isCheckBoxChecked = settingState.isCheckBoxChecked,
                onToggleClick = onToggleClick
            )
            DDanMarginVerticalSpacer(size = 20)
            SettingSignOutBtn2(
                text = stringResource(id = R.string.setting_signout_btn2),
                isCheckBoxChecked = settingState.isCheckBoxChecked,
                onDeleteUserClick = onDeleteUserClick
            )
        }
    }
}

@Composable
fun SettingSignOutBtn2(
    text: String,
    isCheckBoxChecked: Boolean,
    onDeleteUserClick: () -> Unit = {}
) {
    val buttonColors =
        if (isCheckBoxChecked) DDanDDanColorPalette.current.color_button_active else DDanDDanColorPalette.current.color_button_disabled
    val textColors =
        if (isCheckBoxChecked) DDanDDanColorPalette.current.Gray900 else DDanDDanColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(bottom = 20.dp),
        enabled = isCheckBoxChecked,
        onClick = { onDeleteUserClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors,
            disabledContainerColor = buttonColors
        ),
        shape = RoundedCornerShape(size = 0.dp)
    ) {
        Text(
            text = text,
            style = DDanDDanTypo.current.HeadLine6,
            color = textColors
        )
    }
}

@Composable
fun SignOutImageColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(drawable.ic_signout_img),
            contentDescription = null
        )
    }
}

@Composable
fun SignOutConfirmCheck(
    isCheckBoxChecked: Boolean,
    onToggleClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .noRippleClickable { onToggleClick() }
            .padding(start = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = if (isCheckBoxChecked) drawable.ic_check_box_select else drawable.ic_check_box_default),
            contentDescription = null
        )
        DDanMarginHorizontalSpacer(size = 8)
        Text(
            text = stringResource(id = R.string.setting_signout_text2),
            style = DDanDDanTypo.current.Body1,
            color = DDanDDanColorPalette.current.color_text_body_teritary,
        )

    }
}