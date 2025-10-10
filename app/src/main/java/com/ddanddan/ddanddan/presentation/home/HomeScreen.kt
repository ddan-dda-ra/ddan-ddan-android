package com.ddanddan.ddanddan.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.component.EggCounterBadge
import com.ddanddan.ddanddan.presentation.component.CoachMark
import com.ddanddan.ddanddan.util.toBackgroundImage
import com.ddanddan.ddanddan.util.toLottie
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanActionButton
import com.ddanddan.ui.compose.component.DDanAnimationTooltip
import com.ddanddan.ui.enums.TooltipType
import com.ddanddan.ui.ext.noRippleClickable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.ddanddan.ddanddan.service.PhoneDataLayerService
import com.ddanddan.ui.compose.component.DDanSnackBar
import com.ddanddan.ui.compose.component.showSnackbar
import com.ddanddan.ui.compose.theme.DDanDDanTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    needRefresh: Boolean,
    onNavigateLevelUp: (level: Int, petType: String) -> Unit,
    onNavigateError: (Int?) -> Unit = {}
) {
    val context = LocalContext.current

    context.startService(Intent(context, PhoneDataLayerService::class.java))

    val homeState by homeViewModel.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            homeState.pet?.type.toLottie(
                homeState.pet?.level,
                homeState.isPlayAndEatLottie
            )
        )
    )

    val vibrator = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    val basicTooltipMessages = stringArrayResource(id = R.array.basic_tooltip_msg)
    val playTooltipMessages = stringArrayResource(id = R.array.play_tooltip_msg)
    val eatTooltipMessages = stringArrayResource(id = R.array.eat_tooltip_msg)

    LaunchedEffect(homeState.isShowTooltipState) {
        if (homeState.isShowTooltipState) {
            when (homeState.tooltipType) {
                TooltipType.BASIC ->
                    homeViewModel.setCurrentTooltipMsg(basicTooltipMessages.random())

                TooltipType.EAT ->
                    homeViewModel.setCurrentTooltipMsg(eatTooltipMessages.random())

                TooltipType.PLAY ->
                    homeViewModel.setCurrentTooltipMsg(playTooltipMessages.random())
            }
        }
    }

    LaunchedEffect(homeState.isPlayAndEatLottie) {
        if (homeState.isPlayAndEatLottie) {
            val pattern = longArrayOf(0, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100)
            val amplitudes =
                intArrayOf(0, 40, 60, 80, 100, 100, 80, 60, 40, 60, 80, 100, 100, 80, 60, 40)
            val effect = VibrationEffect.createWaveform(pattern, amplitudes, -1)
            vibrator.vibrate(effect)
        } else {
            vibrator.cancel()
        }
    }

    LaunchedEffect(needRefresh) {
        if (needRefresh) {
            homeViewModel.getHomeInfo()
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    val notificationPermissionState =
        if (Build.VERSION.SDK_INT >= 33) rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
        else null

    homeViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.NavigateLevelUp -> {
                onNavigateLevelUp(sideEffect.level, sideEffect.petType.name)
            }

            is HomeSideEffect.NetworkError -> {
                onNavigateError(sideEffect.code)
            }

            is HomeSideEffect.SnackBarMsg -> {
                snackBarHostState.showSnackbar(
                    message = sideEffect.msg,
                    iconResId = R.drawable.ic_system_fill,
                    duration = SnackbarDuration.Short,
                    bottomPadding = 0
                )
            }

            is HomeSideEffect.AskNotification -> {
                @OptIn(ExperimentalPermissionsApi::class)
                if (notificationPermissionState?.status is PermissionStatus.Denied) {
                    notificationPermissionState.launchPermissionRequest()
                }
            }
        }
    }

    HomeScreen(
        homeState = homeState,
        snackBarHostState = snackBarHostState,
        composition = composition,
        onEatClick = homeViewModel::postFoodPet,
        onPlayClick = homeViewModel::postPlayPet,
        onPetClick = { homeViewModel.showTooltipState(it, TooltipType.BASIC) },
        onTooltipVisibilityChanged = homeViewModel::setTooltipState,
        onCoachMarkDismiss = homeViewModel::dismissCoachMark,
        onEggCounterBadgeClick = homeViewModel::eggCountBadgeClick
    )
}

@Composable
fun HomeScreen(
    homeState: HomeState = HomeState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    composition: LottieComposition? = null,
    onEatClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onPetClick: (Boolean) -> Unit = {},
    onTooltipVisibilityChanged: (Boolean) -> Unit = {},
    onCoachMarkDismiss: () -> Unit = {},
    onEggCounterBadgeClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = DDanDDanColorPalette.current.color_background,
            snackbarHost = {
                DDanSnackBar(snackBarHostState = snackBarHostState)
            },
            bottomBar = {
                HomeBottomItem(
                    foodCount = homeState.user?.foodQuantity ?: 0,
                    toyCount = homeState.user?.toyQuantity ?: 0,
                    onEatClick = onEatClick,
                    onPlayClick = onPlayClick
                )
            }) { paddingValues ->
            Box {
                Column(
                    modifier = Modifier.zIndex(1f)
                ) {
                    EggCounterBadge(
                        modifier = Modifier
                            .padding(top = 12.dp, start = 16.dp)
                            .noRippleClickable {
                                onEggCounterBadgeClick()
                            },
                        eggCount = homeState.user?.tickets ?: 0
                    )

                    if (homeState.isShowEggZeroTooltip) {
                        Image(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 6.dp)
                                .noRippleClickable {
                                    onEggCounterBadgeClick()
                                },
                            painter = painterResource(R.drawable.ic_tooltip_egg_zero),
                            contentDescription = "tooltip_zero",
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(top = 32.dp))
                    HomeCalorieItem(
                        purposeCalorie = homeState.user?.purposeCalorie.toString(),
                        currentCalories = homeState.currentCalories.toInt().toString()
                    )
                    Spacer(modifier = Modifier.padding(top = 14.dp))
                    PetContent(
                        modifier = Modifier
                            .weight(1f),
                        homeState = homeState,
                        composition = composition,
                        onPetClick = onPetClick,
                        onTooltipVisibilityChanged = onTooltipVisibilityChanged
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    HomeProgressbarItem(homeState)
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                }
            }
        }
        
        // 코치마크 오버레이 (Scaffold 위에)
        CoachMark(
            isVisible = homeState.firstEggCountBadge,
            onDismiss = onCoachMarkDismiss,
            modifier = Modifier.padding(top = 12.dp, start = 16.dp)
        ) {
            EggCounterBadge(
                eggCount = 1
            )
        }
    }
}

@Composable
fun HomeCalorieItem(
    purposeCalorie: String = "500",
    currentCalories: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = currentCalories,
            fontFamily = NeoDgm,
            fontSize = 52.sp,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "/",
            fontFamily = NeoDgm,
            fontSize = 42.sp,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = purposeCalorie,
            fontFamily = NeoDgm,
            fontSize = 22.sp,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "kcal",
            fontFamily = NeoDgm,
            fontSize = 22.sp,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )
    }
}

@Composable
private fun PetContent(
    modifier: Modifier,
    homeState: HomeState,
    composition: LottieComposition?,
    onPetClick: (Boolean) -> Unit,
    onTooltipVisibilityChanged: (Boolean) -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(homeState.pet?.type.toBackgroundImage()),
            contentDescription = "동물 이미지",
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        )

        DDanAnimationTooltip(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -(156.dp)),
            tooltipText = homeState.currentTooltipMsg,
            isVisible = homeState.isShowTooltipState,
            onVisibilityChanged = onTooltipVisibilityChanged
        )

        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-48).dp)
                .size(100.dp)
                .noRippleClickable {
                    onPetClick(true)
                },
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
fun HomeBottomItem(
    foodCount: Int = 0,
    toyCount: Int = 0,
    onEatClick: () -> Unit = {},
    onPlayClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DDanActionButton(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_action_apple,
            text = "먹이주기",
            count = foodCount,
            onClick = onEatClick
        )
        Spacer(modifier = Modifier.width(12.dp))
        DDanActionButton(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_action_star,
            text = "놀아주기",
            count = toyCount,
            onClick = onPlayClick
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
fun HomeScreenPreview() {
    DDanDDanTheme {
        HomeScreen()
    }
}