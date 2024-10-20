package com.ddanddan.ddanddan.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.ui.compose.ColorPalette_Dark
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo

@Composable
fun HomeBottomScreen(
    foodCount: Int = 0,
    toyCount: Int = 0,
    onEatClick: () -> Unit = {},
    onPlayClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButton(modifier = Modifier.weight(1f), text = "먹이주기", count = "${foodCount}개 보유", onClick = onEatClick)
        Spacer(modifier = Modifier.width(12.dp))
        ActionButton(modifier = Modifier.weight(1f), text = "놀아주기", count = "${toyCount}개 보유", onClick = onPlayClick)
    }
}

@Composable
fun ActionButton(text: String, count: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .height(IntrinsicSize.Min)
            .border(
                border = BorderStroke(2.dp, DDanDDanColorPalette.current.Gray200),
                shape = RoundedCornerShape(8.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = DDanDDanColorPalette.current.elevation_color_elevation_level01,
            contentColor = DDanDDanColorPalette.current.Gray500
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = text,
                style = DDanDDanTypo.current.HeadLine6,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = count,
                style = DDanDDanTypo.current.Body3,
                color = DDanDDanColorPalette.current.Gray500
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF111111
)
@Composable
fun HomeBottomScreenPreview() {
    HomeBottomScreen()
}
