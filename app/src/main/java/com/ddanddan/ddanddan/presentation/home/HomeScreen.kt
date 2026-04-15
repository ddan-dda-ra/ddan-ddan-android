package com.ddanddan.ddanddan.presentation.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.component.CoachMark
import com.ddanddan.ddanddan.presentation.component.EggCounterBadge
import com.ddanddan.ddanddan.presentation.component.EggGachaCard
import com.ddanddan.ddanddan.service.PhoneDataLayerService
import com.ddanddan.ddanddan.util.event.HomeEvent
import com.ddanddan.ddanddan.util.toBackgroundImage
import com.ddanddan.ddanddan.util.toColor
import com.ddanddan.ddanddan.util.toLottie
import com.ddanddan.domain.entity.Pet
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanActionButton
import com.ddanddan.ui.compose.component.DDanAnimationTooltip
import com.ddanddan.ui.compose.component.DDanLoadingDialog
import com.ddanddan.ui.compose.component.DDanSnackBar
import com.ddanddan.ui.compose.component.showSnackbar
import com.ddanddan.ui.compose.theme.DDanDDanTheme
import com.ddanddan.ui.enums.TooltipType
import com.ddanddan.ui.ext.noRippleClickable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    needRefresh: Boolean,
    onNavigateLevelUp: (level: Int, petType: String) -> Unit,
    onNavigateError: (Int?) -> Unit = {},
    onNavigateGrantNotPermission: () -> Unit = {}
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BODY_SENSORS
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            onNavigateGrantNotPermission()
            return@LaunchedEffect
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BODY_SENSORS
                ) == PackageManager.PERMISSION_GRANTED

                if (!hasPermission) {
                    context.stopService(Intent(context, PhoneDataLayerService::class.java))
                    onNavigateGrantNotPermission()
                } else {
                    context.startService(Intent(context, PhoneDataLayerService::class.java))
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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

    if (homeState.isLoading) {
        DDanLoadingDialog()
    }

    HomeScreen(
        homeState = homeState,
        snackBarHostState = snackBarHostState,
        composition = composition,
        onEatClick = {
            homeViewModel.logEvent(HomeEvent.ClickFeedBtn)
            homeViewModel.postFoodPet()
        },
        onPlayClick = {
            homeViewModel.logEvent(HomeEvent.ClickPlayBtn)
            homeViewModel.postPlayPet()
        },
        onPetClick = {
            homeViewModel.logEvent(HomeEvent.ClickPet)
            homeViewModel.showTooltipState(it, TooltipType.BASIC)
        },
        onTooltipVisibilityChanged = homeViewModel::setTooltipState,
        onCoachMarkDismiss = homeViewModel::dismissCoachMark,
        onEggCounterBadgeClick = homeViewModel::eggCountBadgeClick,
        onEggAnimationComplete = {
            homeViewModel.logEvent(HomeEvent.ClickCancelBtn(path = "select-egg"))
            homeViewModel.onEggAnimationComplete()
        },
        onGrowClick = {
            homeViewModel.logEvent(HomeEvent.ClickBtn(path = "select-egg"))
            homeViewModel.postRandomPet()
        }
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
    onEggCounterBadgeClick: () -> Unit = {},
    onEggAnimationComplete: () -> Unit = {},
    onGrowClick: () -> Unit = {}
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
                        purposeCalorie = homeState.user?.purposeCalorie ?: 0,
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
        
        // 알 뽑기 애니메이션 전체 화면
        if (homeState.isShowingEggAnimation) {
            EggGachaAnimation(
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 203.dp),
                petColor = homeState.pet?.type?.toColor() ?: Color(0xFFFD85FF),
                newPet = homeState.newPet,
                onAnimationComplete = onEggAnimationComplete,
                onGrowClick = onGrowClick
            )
        }
    }
}

@Composable
fun HomeCalorieItem(
    purposeCalorie: Int = 500,
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
            text = purposeCalorie.toString(),
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
            contentScale = ContentScale.FillHeight,
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

        Column {
            Spacer(modifier = Modifier.weight(1f))
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .size(100.dp)
                    .noRippleClickable {
                        onPetClick(true)
                    },
                iterations = LottieConstants.IterateForever
            )

            Spacer(modifier = Modifier.weight(0.262f))
        }
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

@Composable
fun EggGachaAnimation(
    modifier: Modifier,
    petColor: Color,
    newPet: Pet?,
    onAnimationComplete: () -> Unit,
    onGrowClick: () -> Unit
) {
    var isAnimating by remember { mutableStateOf(true) }
    var showResultUI by remember { mutableStateOf(false) }
    var showBlurBackground by remember { mutableStateOf(false) }

    // Y축 회전 애니메이션 (회전문처럼)
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotationY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationY"
    )

    // 스케일 애니메이션 상태
    var targetScale by remember { mutableStateOf(0.3f) }

    // 스케일 애니메이션 (작은 크기에서 원래 크기로)
    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "scale",
        finishedListener = {
            // 스케일 애니메이션이 끝나면 회전도 멈춤
            if (it == 1f) {
                isAnimating = false
                showBlurBackground = true
                showResultUI = true
            }
        }
    )

    // 애니메이션 시작
    LaunchedEffect(Unit) {
        targetScale = 1f  // 크기를 1로 키우기 시작
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.TopCenter
    ) {
        if (showBlurBackground) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 178.dp)
                    .size(210.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.radialGradient(
                            colors = listOf(
                                petColor.copy(alpha = 0.8f),
                                petColor.copy(alpha = 0.5f),
                                petColor.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EggGachaCard(
                modifier = modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.rotationY = if (isAnimating) rotationY else 0f
                        cameraDistance = 12f * density
                    },
                pet = newPet
            )

            // 애니메이션이 끝난 후 나타나는 UI
            if (showResultUI) {
                EggGachaResultUI(
                    pet = newPet,
                    onCloseClick = onAnimationComplete,
                    onGrowClick = onGrowClick
                )
            }
        }
    }
}

@Composable
fun EggGachaResultUI(
    onCloseClick: () -> Unit,
    onGrowClick: () -> Unit,
    pet: Pet?
) {
    // 텍스트 슬라이드 업 애니메이션
    var startTextAnimation by remember { mutableStateOf(false) }
    val textOffsetY by animateFloatAsState(
        targetValue = if (startTextAnimation) 0f else 100f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "textSlideUp"
    )
    val textAlpha by animateFloatAsState(
        targetValue = if (startTextAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "textAlpha"
    )
    
    // 버튼 페이드인 애니메이션
    var startButtonAnimation by remember { mutableStateOf(false) }
    val buttonAlpha by animateFloatAsState(
        targetValue = if (startButtonAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "buttonAlpha"
    )
    
    LaunchedEffect(Unit) {
        startTextAnimation = true
        startButtonAnimation = true
    }
    
    Column(
        modifier = Modifier
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // 텍스트들 (아래에서 위로)
        Column(
            modifier = Modifier
                .graphicsLayer {
                    translationY = textOffsetY
                    alpha = textAlpha
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (pet == null) "어떤 펫이 나올까요?" else "반가워",
                fontFamily = NeoDgm,
                fontSize = 24.sp,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = if (pet == null) "아래 버튼을 눌러 알을 골라주세요" else "새로운 펫을 뽑았어요!",
                style = DDanDDanTypo.current.Body1,
                color = DDanDDanColorPalette.current.color_text_body_quaternary
            )
        }
        
        Spacer(modifier = Modifier.padding(top = 32.dp))
        
        // 버튼들 (페이드인)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 46.dp)
                .graphicsLayer {
                    alpha = buttonAlpha
                },
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onCloseClick() },
                modifier = Modifier
                    .weight(1f),
                contentPadding = PaddingValues(vertical = 17.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DDanDDanColorPalette.current.color_button_alternative,
                    contentColor = DDanDDanColorPalette.current.color_text_button_alternative
                ),
                elevation = ButtonDefaults.elevation(0.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "닫기", style = DDanDDanTypo.current.HeadLine6)
            }

            Button(
                onClick = { onGrowClick() },
                modifier = Modifier
                    .weight(1f),
                contentPadding = PaddingValues(vertical = 17.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DDanDDanColorPalette.current.color_button_default02,
                    contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
                ),
                elevation = ButtonDefaults.elevation(0.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "키우기", style = DDanDDanTypo.current.HeadLine6)
            }
        }
    }
}

@Composable
fun HomeGuidelineOverlay(
    isEatStep: Boolean,
    eatButtonPosition: Offset,
    eatButtonSize: IntSize,
    playButtonPosition: Offset,
    playButtonSize: IntSize,
    onNext: () -> Unit,
    onDismiss: () -> Unit
) {
    val currentPosition = if (isEatStep) eatButtonPosition else playButtonPosition
    val currentSize = if (isEatStep) eatButtonSize else playButtonSize
    var arrowHeight by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .pointerInput(Unit) { detectTapGestures { } }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(color = Color.Black.copy(alpha = 0.7f))
            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(currentPosition.x, currentPosition.y),
                size = Size(currentSize.width.toFloat(), currentSize.height.toFloat()),
                cornerRadius = CornerRadius(8.dp.toPx()),
                blendMode = BlendMode.Clear
            )
        }

        // 화살표 - 버튼 중앙, 18px 위
        Image(
            painter = painterResource(
                id = if (isEatStep) R.drawable.ic_arrow_guideline_l else R.drawable.ic_arrow_guideline_r
            ),
            contentDescription = null,
            modifier = Modifier
                .onGloballyPositioned { arrowHeight = it.size.height }
                .offset(
                    x = with(LocalDensity.current) { (currentPosition.x + currentSize.width / 2).toDp() },
                    y = with(LocalDensity.current) { (currentPosition.y).toDp() - 18.dp - arrowHeight.toDp() }
                )
        )

        // 텍스트 + 버튼 - 화면 정중앙
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isEatStep) "100kcal를 소모할 때 마다\n먹이 1개를 받아요" else "3일동안 목표를 달성하면\n펫을 놀아줄 수 있어요",
                style = DDanDDanTypo.current.NeoDgm24,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isEatStep) "'먹이주기' 버튼을 누르면 먹이를 줄 수 있어요" else "'놀아주기' 버튼을 누르면 놀아줄 수 있어요",
                style = DDanDDanTypo.current.Body1,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(28.dp))
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(DDanDDanColorPalette.current.color_button_active)
                    .noRippleClickable { if (isEatStep) onNext() else onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isEatStep) "다음" else "시작하기",
                    style = DDanDDanTypo.current.HeadLine6,
                    color = DDanDDanColorPalette.current.color_text_button_primary_default
                )
            }
        }
    }
}