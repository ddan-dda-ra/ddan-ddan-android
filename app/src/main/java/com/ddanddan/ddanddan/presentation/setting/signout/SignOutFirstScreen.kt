package com.ddanddan.ddanddan.presentation.setting.signout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.setting.SettingSideEffect
import com.ddanddan.ddanddan.presentation.setting.SettingState
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DdanScaffold
import com.ddanddan.ui.ext.noRippleClickable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignOutFirstRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigatePopUp: () -> Unit,
    navigateSignOutSecond: () -> Unit
) {
    val settingState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingSideEffect.NavigatePopUp -> navigatePopUp()
            is SettingSideEffect.NavigateSignOutSecond -> navigateSignOutSecond()
            else -> {}
        }
    }

    SignOutFirstScreen(
        settingState = settingState,
        navigatePopUp = viewModel::navigatePopUp,
        onReasonClick = viewModel::updateSelection,
        onSignOutBtnClick = viewModel::navigateSignOutSecond
    )
}

@Composable
fun SignOutFirstScreen(
    settingState: SettingState = SettingState(),
    navigatePopUp: () -> Unit = {},
    onReasonClick: (String) -> Unit = {},
    onSignOutBtnClick: () -> Unit = {}
) {
    DdanScaffold(
        topbarText = stringResource(id = com.ddanddan.base.R.string.setting_signout_title),
        onClick = {
            navigatePopUp()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            DDanMarginVerticalSpacer(size = 20)
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = com.ddanddan.base.R.string.setting_signout_subtitle1),
                style = DDanDDanTypo.current.HeadLine3,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            DDanMarginVerticalSpacer(size = 60)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                CheckBoxList(
                    selectedReasons = settingState.selectedReasons,
                    signOutList = settingState.signOutList,
                    onReasonClick = onReasonClick
                )
                Spacer(modifier = Modifier.weight(1f))
                SignOutBtn(
                    text = stringResource(id = com.ddanddan.base.R.string.setting_signout_btn_text1),
                    isEnabled = settingState.selectedReasons.isNotEmpty(),
                    onClick = {
                        onSignOutBtnClick()
                    }
                )
                DDanMarginVerticalSpacer(size = 20)
            }
        }

    }
}

@Composable
fun CheckBoxList(
    selectedReasons: List<String> = emptyList(),
    signOutList: List<String> = emptyList(),
    onReasonClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        signOutList.forEach { reason ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 13.dp, horizontal = 20.dp)
                    .noRippleClickable { onReasonClick(reason) }
            ) {
                Text(
                    text = reason,
                    modifier = Modifier,
                    color = DDanDDanColorPalette.current.color_text_body_secondary,
                    style = DDanDDanTypo.current.HeadLine7
                )
                Spacer(modifier = Modifier.weight(1f)) // Pushes the icon to the right
                val iconRes = if (selectedReasons.contains(reason)) {
                    R.drawable.icon_radio_check_on
                } else {
                    R.drawable.icon_radio_check_non
                }
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun SignOutBtn(
    text: String,
    isEnabled: Boolean = false,
    onClick: () -> Unit = {}
) {
    val buttonColors =
        if (isEnabled) DDanDDanColorPalette.current.color_button_active else DDanDDanColorPalette.current.color_button_disabled
    val textColors =
        if (isEnabled) DDanDDanColorPalette.current.color_text_button_primary_default else DDanDDanColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(0.dp),
        enabled = isEnabled,
        onClick = onClick,
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
fun SignOutFirstPreview() {
    SignOutFirstScreen()
}