package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DdanScaffold

@Composable
fun EditNicknameScreen(
    onTopBarBackClick: () -> Unit = {},
) {
    DdanScaffold(
        topbarText = stringResource(id = R.string.editname_topbar_title),
        onClick = {
            onTopBarBackClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = DDanDDanColorPalette.current.color_background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            DDanMarginVerticalSpacer(size = 108)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(76.dp)
                    .background(color = DDanDDanColorPalette.current.color_background),
            ){
                Text(
                    text = stringResource(id = R.string.editname_screen_title),
                    style = DDanDDanTypo.current.HeadLine3,
                    fontFamily = NeoDgm,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )
            }
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
                DDanMarginVerticalSpacer(size = 12)
                EditNameField()
                Spacer(modifier = Modifier.weight(1f))
                EditCardBtn(
                    text = stringResource(id = R.string.editname_button_text),
                )
                DDanMarginVerticalSpacer(size = 20)
            }
        }
    }
}

@Composable
fun EditNameField(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val maxChar = 10
    val nickName by viewModel.nickName.collectAsState()

    OutlinedTextField(
        value = nickName,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight(),
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
                viewModel.updateNickName(newText)
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
    viewModel: SettingViewModel = hiltViewModel(),
    isEnabled: Boolean = false,
) {
    // 각 필드의 현재 상태를 수집
    val name = viewModel.nickName.collectAsState().value
    val isAllValid = name.isNotEmpty()

    val buttonColors =
        if (isAllValid)  DDanDDanColorPalette.current.color_button_active else  DDanDDanColorPalette.current.color_button_disabled
    val textColors =
        if (isAllValid)  DDanDDanColorPalette.current.color_text_button_primary_default else  DDanDDanColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = isEnabled,
        onClick = {
//            if(isAllValid) { viewModel.updateUserInfo() }
        },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColors),
    ) {
        Text(
            text = text,
            style = DDanDDanTypo.current.HeadLine6,
            color = textColors
        )
    }
}