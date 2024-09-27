package com.ddanddan.ddanddan.presentation.home.collect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.home.reward.BottomButton
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.component.DDanSnackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetCollectionScreen(
    viewModel: CollectViewModel = hiltViewModel(),
    navController: NavHostController,
    onConfirmClick: () -> Unit = {}
) {
    val selectIndex = remember {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackBarEvent.collect { event ->
            when (event) {
                is SnackBarEvent.ShowSnackBarMsg -> {
                    snackBarHostState.showSnackbar(event.msg)
                }
                is SnackBarEvent.Default-> {
                    snackBarHostState.currentSnackbarData?.dismiss()

                }
            }
        }
    }

    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "펫 보관함",
                        style = DDanDDanTypo.current.HeadLine6,
                        color = DDanDDanColorPalette.current.color_text_headline_primary,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "뒤로 가기",
                            tint = DDanDDanColorPalette.current.color_icon_level01
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DDanDDanColorPalette.current.color_background,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomButton(btnText = "선택 완료", onButtonClick = onConfirmClick)
        },
        snackbarHost = {
            DDanSnackBar(snackBarHostState = snackBarHostState)
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(9) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DDanDDanColorPalette.current.elevation_color_elevation_level02)
                ) {
                    // TODO index에 따라서 뭐가 올지 추후 작업
                    PetItem(index, selectIndex.intValue, {
                        selectIndex.intValue = it
                    }, {
                        viewModel.showSnackBarEvent("새로운 펫을 준비중이에요")
                    })
                }
            }
        }
    }
}

@Composable
fun PetItem(
    index: Int,
    selectIndex: Int,
    onSelectIndex: (Int) -> Unit = {},
    onOtherItemClick: () -> Unit = {}
) {
    val imageRes = when (index) {
        0 -> R.drawable.ic_cat
        1 -> R.drawable.ic_cat
        2 -> R.drawable.ic_cat
        3 -> R.drawable.ic_cat
        else -> R.drawable.ic_question
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            if (index in 0..3) {
                onSelectIndex(index)
            } else {
                onOtherItemClick()
            }
        }) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Pet",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        if (index == selectIndex) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        4.dp,
                        DDanDDanColorPalette.current.color_outline_level01_active,
                        RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}