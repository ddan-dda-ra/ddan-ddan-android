package com.ddanddan.ddanddan.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ddanddan.ddanddan.util.toBackgroundImage
import com.ddanddan.ddanddan.util.toLottie
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.component.DDanSnackBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onStorageClick: (String) -> Unit,
    onSettingClick: () -> Unit,
    onNavigateLevelUp: (level: Int, petType: String) -> Unit,
    onNavigateNewPet: (petType: String) -> Unit,
    onNavigateError: (Int?) -> Unit = {}
) {
    val homeState by homeViewModel.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(homeState.pet?.type.toLottie())
    )

    homeViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.NavigatePetCollection -> {
                onStorageClick(sideEffect.petId)
            }
            is HomeSideEffect.NavigateSetting -> {
                onSettingClick()
            }
            is HomeSideEffect.NavigateLevelUp -> {
                onNavigateLevelUp(sideEffect.level, sideEffect.petType.name)
            }
            is HomeSideEffect.NavigateNewPet -> {
                onNavigateNewPet(sideEffect.petType.name)
            }
            is HomeSideEffect.NetworkError -> {
                onNavigateError(sideEffect.code)
            }
            is HomeSideEffect.SnackBarMsg -> {
                snackBarHostState.showSnackbar(sideEffect.msg)
            }
        }
    }

    HomeScreen(
        homeState = homeState,
        snackBarHostState = snackBarHostState,
        composition = composition,
        onStorageClick = homeViewModel::onStorageClick,
        onSettingClick = homeViewModel::onSettingClick,
        onEatClick = homeViewModel::postFoodPet,
        onPlayClick = homeViewModel::postPlayPet,
    )
}

@Composable
fun HomeScreen(
    homeState: HomeState = HomeState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    composition: LottieComposition? = null,
    onStorageClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    onEatClick: () -> Unit = {},
    onPlayClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background,
        snackbarHost = {
            DDanSnackBar(snackBarHostState = snackBarHostState)
        },
        bottomBar = {
            HomeBottomScreen(
                foodCount = homeState.user?.foodQuantity ?: 0,
                toyCount = homeState.user?.toyQuantity ?: 0,
                onEatClick = {
                    onEatClick()
                }, onPlayClick = {
                    onPlayClick()
                }
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.padding(top = 20.dp))
            HomeTopScreen(onStorageClick, onSettingClick)
            Spacer(modifier = Modifier.padding(top = 16.dp))
            HomeCalorieScreen(homeState.user?.purposeCalorie.toString())
            Spacer(modifier = Modifier.padding(top = 14.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(homeState.pet?.type.toBackgroundImage()),
                            contentDescription = "동물 이미지",
                            modifier = Modifier.wrapContentSize()
                        )

                        LottieAnimation(
                            composition = composition,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .offset(y = (-56).dp)
                                .size(100.dp),
                            iterations = LottieConstants.IterateForever
                        )
                    }
                    Spacer(modifier = Modifier.weight(2.228f))
                }
            }
            Spacer(modifier = Modifier.padding(top = 32.dp))
            HomeProgressbarScreen(homeState)
            Spacer(modifier = Modifier.padding(top = 20.dp))
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
fun HomeScreenPreview() {
    HomeScreen()
}