package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DdanScaffold

@Composable
fun EditTargetScreen(
    onTopBarBackClick: () -> Unit = {},
) {
    DdanScaffold(
        topbarText = stringResource(id = com.ddanddan.base.R.string.edittarget_topbar_title),
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
            DDanMarginVerticalSpacer(size = 24)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(128.dp)
                    .background(color = DDanDDanColorPalette.current.color_background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = stringResource(id = com.ddanddan.base.R.string.edittarget_screen_title),
                    style = DDanDDanTypo.current.HeadLine3,
                    fontFamily = NeoDgm,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )
            }
            DDanMarginVerticalSpacer(size = 24)
            EditTargetControl()
            Spacer(modifier = Modifier.weight(1f))
            EditTargetBtn(
                text = stringResource(id = com.ddanddan.base.R.string.edittarget_button_text),
            )
            DDanMarginVerticalSpacer(size = 20)
        }
    }
}

@Composable
fun EditTargetControl(
    viewModel: SettingViewModel = hiltViewModel()
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
                viewModel.decrementTarget()
            }
        )

        Text(
            text = viewModel.target.collectAsState().value.toString(),
            style = DDanDDanTypo.current.HeadLine4,
            color = DDanDDanColorPalette.current.color_text_button_primary_default,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.icon_system_plus),
            contentDescription = "image description",
            contentScale = ContentScale.None,
            modifier = Modifier.clickable {
                viewModel.incrementTarget()
            }
        )
    }
}


@Composable
fun EditTargetBtn(
    text: String,
    viewModel: SettingViewModel = hiltViewModel(),
    isEnabled: Boolean = false,
) {
    // 각 필드의 현재 상태를 수집
    val target = viewModel.target.collectAsState().value
    val isAllValid = target in 100..1000

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