package com.ddanddan.ddanddan.presentation.setting.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.setting.SettingSideEffect
import com.ddanddan.ddanddan.presentation.setting.SettingState
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DDanSnackBar
import com.ddanddan.ui.compose.component.DdanScaffold
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun EditNickNameRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigatePopUp: () -> Unit = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val settingState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingSideEffect.NavigatePopUp -> {
                navigatePopUp()
            }

            is SettingSideEffect.SuccessChange -> {
                navigatePopUp()
            }

            is SettingSideEffect.NetworkError -> {
                snackBarHostState.showSnackbar(sideEffect.msg)
            }

            else -> {}
        }
    }

    EditNickNameScreen(
        settingState = settingState,
        snackBarHostState = snackBarHostState,
        navigatePopUp = viewModel::navigatePopUp,
        onValueChange = viewModel::changeNickName,
        onEditBtnClick = viewModel::onEditBtnClick
    )
}

@Composable
fun EditNickNameScreen(
    settingState: SettingState = SettingState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navigatePopUp: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onEditBtnClick: () -> Unit = {}
) {
    DdanScaffold(
        topbarText = stringResource(id = R.string.editname_topbar_title),
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
                text = stringResource(id = R.string.editname_screen_title),
                style = DDanDDanTypo.current.HeadLine3,
                fontFamily = NeoDgm,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            DDanMarginVerticalSpacer(size = 32)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .wrapContentHeight()
                    .background(color = DDanDDanColorPalette.current.color_background),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = R.string.editname_textfield_text),
                    style = DDanDDanTypo.current.Body2,
                    color = DDanDDanColorPalette.current.color_text_body_quaternary
                )
                DDanMarginVerticalSpacer(size = 8)
                EditNameField(
                    nickName = settingState.nickName,
                    onValueChange = onValueChange
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            EditCardBtn(
                text = stringResource(id = R.string.editname_button_text),
                nickName = settingState.nickName,
                onClick = onEditBtnClick
            )
            DDanMarginVerticalSpacer(size = 20)
        }
    }
}

@Composable
fun EditNameField(
    nickName: String,
    onValueChange: (String) -> Unit = {},
) {
    val maxChar = 10

    OutlinedTextField(
        value = nickName,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
                onValueChange(newText)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = DDanDDanColorPalette.current.color_text_body_primary,
            focusedBorderColor = DDanDDanColorPalette.current.elevation_color_elevation_level01,
            unfocusedBorderColor = DDanDDanColorPalette.current.elevation_color_elevation_level01,
            unfocusedLabelColor = DDanDDanColorPalette.current.color_text_body_quinary,
            focusedLabelColor = DDanDDanColorPalette.current.color_text_body_quinary,
            backgroundColor = DDanDDanColorPalette.current.elevation_color_elevation_level01,
        ),
    )
}

@Composable
fun EditCardBtn(
    text: String,
    nickName: String,
    onClick: () -> Unit = {}
) {
    // 각 필드의 현재 상태를 수집
    val isAllValid = nickName.isNotEmpty()

    val buttonColors =
        if (isAllValid) DDanDDanColorPalette.current.color_button_active else DDanDDanColorPalette.current.color_button_disabled
    val textColors =
        if (isAllValid) DDanDDanColorPalette.current.color_text_button_primary_default else DDanDDanColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(0.dp),
        enabled = isAllValid,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors,
            disabledContainerColor = buttonColors
        ),
    ) {
        Text(
            text = text,
            style = DDanDDanTypo.current.HeadLine6,
            color = textColors
        )
    }
}

@Composable
@Preview
fun EditNicknameScreenPreview() {
    EditNickNameScreen()
}