package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ddanddan.presentation.setting.viewModel.SignOutList
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DdanScaffold

@Composable
fun SignOutFirstScreen(
    navController: NavHostController,
    viewModel: SettingViewModel = hiltViewModel(),
    onTopBarBackClick: () -> Unit = {}
) {
    DdanScaffold(
        topbarText = stringResource(id = com.ddanddan.base.R.string.setting_signout_title),
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
            ) {
                Text(
                    text = stringResource(id = com.ddanddan.base.R.string.setting_signout_subtitle1),
                    style = DDanDDanTypo.current.HeadLine3,
                    fontFamily = NeoDgm,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = DDanDDanColorPalette.current.color_background),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                CheckBoxList()
                Spacer(modifier = Modifier.weight(1f))
                SignOutBtn(
                    text = stringResource(id = com.ddanddan.base.R.string.setting_signout_btn_text1),
                    viewModel = viewModel,
                    isEnabled = viewModel.selectedReasons.collectAsState().value.isNotEmpty(),
                    onClick = {
                        navController.navigate("signOutSecondScreen")
                    }
                )
                DDanMarginVerticalSpacer(size = 20)
            }
        }

    }
}

@Composable
fun CheckBoxList(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val selectedReasons = viewModel.selectedReasons.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SignOutList.forEach { reason ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 20.dp)
                    .clickable { viewModel.updateSelection(reason) }
            ) {
                Text(text = reason, modifier = Modifier.padding(start = 8.dp))
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
    viewModel: SettingViewModel = hiltViewModel(),
    isEnabled: Boolean = false,
    onClick: () -> Unit = {}
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
            .height(56.dp)
            .padding(0.dp),
        enabled = isEnabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColors),
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
    val navController = rememberNavController()
    SignOutFirstScreen(navController)
}