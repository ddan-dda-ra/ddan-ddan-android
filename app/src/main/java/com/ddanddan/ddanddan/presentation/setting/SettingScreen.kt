package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import com.ddanddan.ddanddan.BuildConfig.VERSION_NAME
import com.ddanddan.ui.compose.ColorPalette_Dark
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.Pretendard
import com.ddanddan.ddanddan.R
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DdanScaffold

@Composable
fun SettingScreen(navController: NavController) {
    DdanScaffold(
        topbarText = stringResource(id = com.ddanddan.base.R.string.setting_topbar_title),
        onClick = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DDanDDanColorPalette.current.elevation_color_elevation_level01)
        ) {
            val versionName = VERSION_NAME
            DDanMarginVerticalSpacer(size = 60)
        }
    }
}

@Composable
fun SettingTitle(title: String, onClick: ()-> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .background(color = ColorPalette_Dark.color_background)
        .height(46.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = DDanDDanTypo.current.HeadLine7,
                color = ColorPalette_Dark.color_text_body_primary
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_l),
                contentDescription = "right_arrow", tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}