package com.ddanddan.ddanddan.presentation.home.reward

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddanddan.ddanddan.R
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.theme.DDanDDanTheme

@Composable
fun ToyAcquireScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(84.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 65.dp),
            textAlign = TextAlign.Center,
            text = "3일 내내 운동을\n 열심히 하셨네요!",
            fontFamily = NeoDgm,
            fontSize = 24.sp,
            color = DDanDDanColorPalette.current.color_text_headline_primary
        )

        Spacer(modifier = Modifier.height(80.dp))
        ToyItem(title = "타이틀입니다.", content = "설명입니다.", imageSource = R.drawable.ic_storage)
        Spacer(modifier = Modifier.height(8.dp))
        ToyItem(title = "타이틀입니다.", content = "설명입니다.", imageSource = R.drawable.ic_storage)
    }
}

@Composable
fun ToyItem(title: String, content: String, imageSource: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        backgroundColor = DDanDDanColorPalette.current.elevation_color_elevation_level02
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp),
                painter = painterResource(id = imageSource),
                contentDescription = "아이템 이미지"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    style = DDanDDanTypo.current.HeadLine5,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )
                Text(
                    text = content,
                    style = DDanDDanTypo.current.SubTitle1,
                    color = DDanDDanColorPalette.current.color_text_body_teritary
                )
            }
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ToyAcquireScreenPreview() {
    DDanDDanTheme(true) {
        ToyAcquireScreen()
    }
}