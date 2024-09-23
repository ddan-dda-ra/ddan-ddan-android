package com.ddanddan.ddanddan.presentation.home.reward

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ddanddan.ddanddan.R
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.theme.DDanDDanTheme

@Composable
fun LevelUpScreen() {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
                painter = painterResource(id = R.drawable.ic_cat),
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
            text = "lv2로\n업그레이드 되었어요!",
            fontFamily = NeoDgm,
            fontWeight = FontWeight(400),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )
    }
}