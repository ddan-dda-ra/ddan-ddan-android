package com.ddanddan.ui.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo

@Composable
fun DDanActionButton(
    text: String,
    count: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
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