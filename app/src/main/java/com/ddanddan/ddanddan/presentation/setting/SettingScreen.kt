package com.ddanddan.ddanddan.presentation.setting

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ddanddan.ddanddan.BuildConfig.VERSION_NAME
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.ddanddan.util.event.MyPageEvent
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.Pretendard
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DDanToggleButton
import com.ddanddan.ui.compose.component.DDanTwoButtonDialog
import com.ddanddan.ui.compose.component.DdanScaffold
import com.ddanddan.ui.ext.noRippleClickable
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
    onPetCollectionClick: () -> Unit
) {
    val settingState by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingSideEffect.NavigatePopUp -> navigatePopUp(sideEffect.needRefreshHomeScreen)
            is SettingSideEffect.EditNickname -> onNickNameClick()
            is SettingSideEffect.EditTargetCalories -> onCaloriesClick()
            is SettingSideEffect.AgreeToTerms -> onAgreeClick()
            is SettingSideEffect.DeleteAccount -> onSignOutClick()
            is SettingSideEffect.NavigateLogin -> navigateLogin()
            is SettingSideEffect.NavigatePetCollection -> onPetCollectionClick()
            is SettingSideEffect.CustomerService -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://tally.so/r/Gx1GEe"))
                context.startActivity(intent)
            }
            else -> {}
        }
    }

    SettingScreen(
        settingState = settingState,
        navigatePopUp = viewModel::navigatePopUp,
        onSettingItemClick = { titleId ->
            val event = when (titleId) {
                R.string.setting_title_petbox -> MyPageEvent.ClickPetBox(touchpoint = "mypage")
                R.string.setting_title_edit_nickname -> MyPageEvent.ClickChangeName(touchpoint = "mypage")
                R.string.setting_title_edit_calories -> MyPageEvent.ClickChangeGoal(touchpoint = "mypage")
                R.string.setting_title_terms -> MyPageEvent.ClickTerms(touchpoint = "mypage")
                R.string.setting_title_cs -> MyPageEvent.ClickTerms(touchpoint = "cs")
                else -> MyPageEvent.ClickDeleteAccount(touchpoint = "mypage")
            }
            viewModel.logEvent(event)
            viewModel.onSettingItemClick(titleId)
        },
        onLogOutClick = viewModel::showDialog,
        onDialogDismiss = viewModel::dismissDialog,
        onDialogConfirm = viewModel::navigateLogin,
        onPushToggleClick = {
            viewModel.logEvent(MyPageEvent.ClickPushAlarm(touchpoint = "mypage"))
            viewModel.onPushNotificationToggle()
        }
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
    onPushToggleClick: () -> Unit = {}
) {
    DdanScaffold(
        topbarText = stringResource(id = R.string.setting_topbar_title),
        onClick = {
            navigatePopUp()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DDanDDanColorPalette.current.color_background)
                .padding(it)
        ) {
            val versionName = VERSION_NAME
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    modifier = Modifier.height(22.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    text = stringResource(R.string.setting_part_text1),
                    style = DDanDDanTypo.current.Body2,
                    fontFamily = Pretendard,
                    color = DDanDDanColorPalette.current.color_text_headline_teritary
                )
                DDanMarginVerticalSpacer(8)
                SettingBoxColumn(
                    settingItems = settingState.settingItems,
                    onClick = { titleId -> onSettingItemClick(titleId) }
                )
                DDanMarginVerticalSpacer(28)
                Text(
                    modifier = Modifier.height(22.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    text = stringResource(R.string.setting_part_text2),
                    style = DDanDDanTypo.current.Body2,
                    fontFamily = Pretendard,
                    color = DDanDDanColorPalette.current.color_text_headline_teritary
                )
                DDanMarginVerticalSpacer(8)
                SettingToggleTitle(
                    state = settingState,
                    title = stringResource(R.string.setting_title_push),
                    onToggleClick = onPushToggleClick
                )
                DDanMarginVerticalSpacer(28)
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 8.dp,
                color = DDanDDanColorPalette.current.elevation_color_elevation_level01
            )
            SettingColumn(
                settingItems = settingState.settingItemsBottom,
                onClick = { titleId ->
                    if (titleId == R.string.setting_title_logout) {
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
fun SettingBoxColumn(
    settingItems: List<Pair<Int, Int?>>,
    onClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = DDanDDanColorPalette.current.color_background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp) // 아이템 간격 설정
    ) {
        items(settingItems) { item ->
            SettingBoxTitle(
                title = stringResource(id = item.first),
                description = item.second?.let { stringResource(it) },
                onClick = {
                    onClick(item.first)
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
        .height(48.dp)
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

@Preview
@Composable
fun SettingToggleTitle(
    state: SettingState = SettingState(),
    title: String = "전체 푸시 알림",
    onToggleClick: () -> Unit = {}
) {
    Row(modifier = Modifier
        .background(
            color = DDanDDanColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(8.dp)
        )
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 20.dp)
        .noRippleClickable { onToggleClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = DDanDDanTypo.current.HeadLine7,
            fontFamily = Pretendard,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )
        Spacer(modifier = Modifier.weight(1f))
        DDanToggleButton(
            isOn = state.isPushAllowed
        )
    }
}

@Preview
@Composable
fun SettingBoxTitle(title: String = "펫 보관함", description: String? = "null", onClick: () -> Unit = {}) {
    Row(modifier = Modifier
        .background(
            color = DDanDDanColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(8.dp)
        )
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 20.dp)
        .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = DDanDDanTypo.current.HeadLine7,
                fontFamily = Pretendard,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            if (description != null) {
                DDanMarginVerticalSpacer(4)
                Text(
                    text = description,
                    style = DDanDDanTypo.current.Body2,
                    fontFamily = Pretendard,
                    color = DDanDDanColorPalette.current.color_text_headline_teritary
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_arrow_right_l),
            contentDescription = null
        )
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
