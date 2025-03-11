package com.ddanddan.ddanddan.presentation.home.reward.pet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.home.reward.level.BottomButton
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.NeoDgm

@Composable
fun NewPetRoute(
    petType: PetTypeEnum,
    onButtonClick: () -> Unit
) {
    NewPetScreen(
        petType = petType,
        onButtonClick = onButtonClick
    )
}

@Composable
fun NewPetScreen(
    petType: PetTypeEnum = PetTypeEnum.CAT,
    onButtonClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomButton("시작하기", onButtonClick = onButtonClick)
        },
        backgroundColor = DDanDDanColorPalette.current.color_background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(216.dp))
            ConstraintLayout {
                val (backgroundImage, overlayImage) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_effect),
                    contentDescription = "배경 이미지",
                    modifier = Modifier.constrainAs(backgroundImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                )

                Image(
                    painter = painterResource(id = petType.toAnimal(1)),
                    contentDescription = "배경 이미지",
                    modifier = Modifier.constrainAs(overlayImage) {
                        top.linkTo(backgroundImage.top, margin = 40.dp)
                        start.linkTo(backgroundImage.start)
                        bottom.linkTo(backgroundImage.bottom, margin = 20.dp)
                        end.linkTo(backgroundImage.end)
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "새로운 펫을 키울 수 있어요",
                fontFamily = NeoDgm,
                fontWeight = FontWeight(400),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
        }
    }
}