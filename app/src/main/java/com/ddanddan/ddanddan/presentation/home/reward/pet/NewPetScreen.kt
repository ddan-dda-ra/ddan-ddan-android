package com.ddanddan.ddanddan.presentation.home.reward.pet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.home.reward.level.BottomButton
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.theme.DDanDDanTheme

@Composable
fun NewPetRoute(
    onButtonClick: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.motion_confetti))

    NewPetScreen(
        composition = composition,
        onButtonClick = onButtonClick
    )
}

@Composable
fun NewPetScreen(
    composition: LottieComposition? = LottieComposition(),
    onButtonClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomButton("시작하기", onButtonClick = onButtonClick)
        },
        backgroundColor = DDanDDanColorPalette.current.color_background
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(182.dp))
                LottieAnimation(
                    modifier = Modifier
                        .fillMaxWidth().padding(horizontal = 40.dp),
                    composition = composition,
                    iterations = 1
                )
            }

            Text(
                modifier = Modifier.align(Alignment.BottomCenter).offset(y = (-10).dp),
                text = "이제 새로운 펫을\n뽑을 수 있어요",
                fontFamily = NeoDgm,
                fontWeight = FontWeight(400),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
        }

    }
}

@Preview
@Composable
fun NewPetScreenPreview() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.motion_confetti))

    DDanDDanTheme {
        NewPetScreen(composition)
    }
}