package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ddanddan.ddanddan.BuildConfig.VERSION_NAME
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DDanTwoButtonDialog
import com.ddanddan.ui.compose.component.DdanScaffold
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    navigatePopUp: (Boolean) -> Unit,
    onNickNameClick: () -> Unit,
    onCaloriesClick: () -> Unit,
    onAgreeClick: () -> Unit,
    onSignOutClick: () -> Unit,
    navigateLogin: () -> Unit,
) {
    val settingState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingSideEffect.NavigatePopUp -> navigatePopUp(sideEffect.needRefreshHomeScreen)
            is SettingSideEffect.EditNickname -> onNickNameClick()
            is SettingSideEffect.EditTargetCalories -> onCaloriesClick()
            is SettingSideEffect.AgreeToTerms -> onAgreeClick()
            is SettingSideEffect.DeleteAccount -> onSignOutClick()
            is SettingSideEffect.NavigateLogin -> navigateLogin()
            else -> {}
        }
    }

    SettingScreen(
        settingState = settingState,
        navigatePopUp = viewModel::navigatePopUp,
        onSettingItemClick = viewModel::onSettingItemClick,
        onLogOutClick = viewModel::showDialog,
        onDialogDismiss = viewModel::dismissDialog,
        onDialogConfirm = viewModel::navigateLogin
    )
}

@Composable
fun SettingScreen(
    settingState: SettingState = SettingState(),
    navigatePopUp: () -> Unit = {},
    onSettingItemClick: (Int) -> Unit = {},
    onLogOutClick: () -> Unit = {},
    onDialogDismiss: () -> Unit = {},
    onDialogConfirm: () -> Unit = {},
) {
    DdanScaffold(
        topbarText = stringResource(id = com.ddanddan.base.R.string.setting_topbar_title),
        onClick = {
            navigatePopUp()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DDanDDanColorPalette.current.elevation_color_elevation_level01)
                .padding(it)
        ) {
            val versionName = VERSION_NAME
            SettingColumn(
                settingItems = settingState.settingItems,
                onClick = { titleId -> onSettingItemClick(titleId) }
            )
            DDanMarginVerticalSpacer(size = 8)
            SettingColumn(
                settingItems = settingState.settingItemsBottom,
                onClick = { titleId ->
                    if (titleId == com.ddanddan.base.R.string.setting_title_text6) {
                        onLogOutClick()
                    } else {
                        onSettingItemClick(titleId)
                    }
                }
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .padding(start = 20.dp),
                text = "앱 버전 $versionName",
                style = DDanDDanTypo.current.Body2,
                color = DDanDDanColorPalette.current.color_text_body_quinary,
            )
        }
    }
    if (settingState.isShowLogoutDialog) {
        DDanTwoButtonDialog(
            title = "정말 로그아웃 하시겠습니까?",
            cancelText = "취소",
            confirmText = "로그아웃",
            onClickCancel = onDialogDismiss,
            onClickConfirm = onDialogConfirm
        )
    }
}

@Composable
fun SettingColumn(
    settingItems: List<Int>,
    onClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = DDanDDanColorPalette.current.color_background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        items(settingItems) { item ->
            SettingTitle(
                title = stringResource(id = item),
                onClick = {
                    onClick(item)
                }
            )
        }
    }
}

@Composable
fun SettingTitle(title: String, onClick: () -> Unit) {
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
