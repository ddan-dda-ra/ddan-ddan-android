package com.ddanddan.ddanddan.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.util.toImage
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.component.DDanSnackBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onStorageClick: (String) -> Unit = {},
    onSettingClick: () -> Unit = {}
) {
    val homeState by homeViewModel.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    homeViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ToastNetworkError -> {
                Toast.makeText(context, "네트워크 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
            }

            is HomeSideEffect.SnackBarMsg -> {
                snackBarHostState.showSnackbar(sideEffect.msg)
            }
        }
    }

    HomeScreen(
        homeState = homeState,
        snackBarHostState = snackBarHostState,
        onStorageClick = { onStorageClick(homeState.pet?.id ?: "") },
        onSettingClick = onSettingClick,
        onEatClick = homeViewModel::postFoodPet,
        onPlayClick = homeViewModel::postPlayPet,
    )
}

@Composable
fun HomeScreen(
    homeState: HomeState = HomeState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onStorageClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    onEatClick: () -> Unit = {},
    onPlayClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background,
        snackbarHost = {
            DDanSnackBar(snackBarHostState = snackBarHostState)
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
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(homeState.pet?.type.toImage()),
                    contentDescription = "동물 이미지",
                    modifier = Modifier.wrapContentSize()
                )
            }
            Spacer(modifier = Modifier.padding(top = 32.dp))
            HomeProgressbarScreen(homeState)
            Spacer(modifier = Modifier.padding(top = 20.dp))
            HomeBottomScreen(
                foodCount = homeState.user?.foodQuantity ?: 0,
                toyCount = homeState.user?.toyQuantity ?: 0,
                onEatClick = {
                    onEatClick()
                }, onPlayClick = {
                    onPlayClick()
                }
            )
        }
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
fun HomeScreenPreview() {
    HomeScreen()
}