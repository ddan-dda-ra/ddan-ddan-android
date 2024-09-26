package com.ddanddan.ddanddan.presentation.home.reward

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
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
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.Shapes
import com.ddanddan.ui.compose.theme.DDanDDanTheme

@Composable
fun LevelUpOrNetPetScreen(
    imageSource: Int = R.drawable.ic_cat,
    text: String = "",
    btnText: String = "성장하기"
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomButton(btnText = btnText)
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
                    painter = painterResource(id = imageSource),
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
                text = text,
                fontFamily = NeoDgm,
                fontWeight = FontWeight(400),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
        }
    }
}

@Composable
fun BottomButton(btnText: String, onButtonClick: () -> Unit = {}) {
    Button(
        onClick = { onButtonClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DDanDDanColorPalette.current.color_button_active,
            contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
        )
    ) {
        Text(text = btnText, style = DDanDDanTypo.current.HeadLine6)
    }
}