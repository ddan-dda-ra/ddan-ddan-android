package com.ddanddan.ddanddan.presentation.friends

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer

@Composable
@Preview
fun AddedFriendScreen(
    friendPetType: PetTypeEnum = PetTypeEnum.DOG,
    friendPetLevel: Int = 1,
    onNavigateFriends: () -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.motion_confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    val isFinished = progress >= 0.4f

    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (confetti, col, pet, btn) = createRefs()
            LottieAnimation(
                modifier = Modifier.constrainAs(confetti) {
                    top.linkTo(parent.top)
                    bottom.linkTo(btn.top, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth().padding(horizontal = 40.dp),
                composition = composition,
                iterations = 1
            )

            Column(
                modifier = Modifier.constrainAs(col) {
                    top.linkTo(confetti.bottom, margin = (-54).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.added_friend_description_done),
                    style = DDanDDanTypo.current.HeadLine3,
                    fontFamily = NeoDgm,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )
                DDanMarginVerticalSpacer(10)
                // 두 번째 텍스트는 애니메이션 끝난 뒤에 표시
                AnimatedVisibility(
                    visible = isFinished,
                    enter = fadeIn(animationSpec = tween(600)) + slideInVertically(initialOffsetY = { 20 }),
                    exit = fadeOut()
                ) {
                    Text(
                        text = stringResource(R.string.added_friend_description_start),
                        style = DDanDDanTypo.current.Body1,
                        color = DDanDDanColorPalette.current.color_text_body_quaternary
                    )
                }
            }

            AnimatedVisibility(
                visible = isFinished,
                modifier = Modifier.constrainAs(pet) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(confetti.top, margin = 7.dp)
                    bottom.linkTo(confetti.bottom)
                },
                enter = fadeIn() + scaleIn(),
                exit = fadeOut()
            ) {
                Image(
                    painter = painterResource(id = friendPetType.toAnimal(friendPetLevel)),
                    contentDescription = null
                )
            }

            CheckBtn(
                modifier = Modifier.fillMaxWidth().constrainAs(btn) {
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                },
                onClick = onNavigateFriends
            )
        }
    }
}

@Composable
fun CheckBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RectangleShape,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = DDanDDanColorPalette.current.color_button_active,
            contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
        )
    ) {
        Text(text = stringResource(R.string.added_friend_button_text), style = DDanDDanTypo.current.HeadLine6)
    }
}