package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ddanddan.ddanddan.BuildConfig.VERSION_NAME
import com.ddanddan.ui.compose.ColorPalette_Dark
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.Pretendard
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.home.HomeBottomScreen
import com.ddanddan.ddanddan.presentation.navigation.DDanDDanRoute
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DdanScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onTopBarBackClick: () -> Unit = {},
    onNickNameClick: () -> Unit = {},
    onCaloriesClick: () -> Unit = {},
    onAlarmClick: () -> Unit = {},
    onAgreeClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {},
    onLogOutClick: () -> Unit = {},
    viewModel: SettingViewModel = hiltViewModel(),
) {
    DdanScaffold(
        topbarText = stringResource(id = com.ddanddan.base.R.string.setting_topbar_title),
        onClick = {
            onTopBarBackClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DDanDDanColorPalette.current.elevation_color_elevation_level01)
        ) {
            val versionName = VERSION_NAME
            val settingItems = viewModel.settingItems
            val settingItemsBottom = viewModel.settingItemsBottom
            DDanMarginVerticalSpacer(size = 65)
            SettingColumn(
                settingItems = settingItems,
                onIntent = { intent ->
                    when(intent) {
                        SettingIntent.EditNickname -> {
                            onNickNameClick()
                        }
                        else -> {
                            onCaloriesClick()
                        }
                    }
                }
            )
            DDanMarginVerticalSpacer(size = 8)
            SettingColumn(
                settingItems = settingItemsBottom,
                onIntent = { intent ->
                    when(intent) {
                        SettingIntent.AgreeToTerms -> {
                            onAgreeClick()
                        }
                        SettingIntent.DeleteAccount -> {
                            onSignOutClick()
                        }
                        else -> {
                            onLogOutClick()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun SettingColumn(
    settingItems: List<SettingViewModel.SettingItem>,
    onIntent: (SettingIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = DDanDDanColorPalette.current.color_background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        items(settingItems) {item->
            SettingTitle(
                title = stringResource(id = item.titleRes),
                onClick = {
                    onIntent(item.intent)
                }
            )
        }
    }
}

@Composable
fun SettingTitle(title: String, onClick: ()-> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .background(color = DDanDDanColorPalette.current.color_background)
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
                style = DDanDDanTypo.current.HeadLine6,
                color = DDanDDanColorPalette.current.color_text_body_primary
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_l),
                contentDescription = "right_arrow", tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF111111
)
@Composable
fun SettingPreview() {
    val navController = rememberNavController()
    SettingScreen()
}
