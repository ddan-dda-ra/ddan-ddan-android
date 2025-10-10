package com.ddanddan.ddanddan.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.domain.entity.Pet
import com.ddanddan.ui.compose.theme.DDanDDanTheme

@Composable
fun EggGachaCard(
    modifier: Modifier = Modifier,
    pet: Pet? = null
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.motion_random_egg)
    )

    Box(
        modifier = modifier
            .size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        // 검은색 둥근 사각형 배경
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(
                    color = Color(0xFF212121),
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 4.dp,
                    color = Color(0xFF333333),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (pet == null) {
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier.size(136.dp),
                    iterations = LottieConstants.IterateForever
                )
            } else {
                Image(
                    modifier = Modifier.size(136.dp),
                    painter = painterResource(pet.type.toAnimal(pet.level)),
                    contentDescription = "pet"
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun EggGachaCardPreview() {
    DDanDDanTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            EggGachaCard()
        }
    }
}
