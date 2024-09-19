package com.ddanddan.ddanddan.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddanddan.ui.compose.NeoDgm

@Composable
fun HomeCalorieScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "245",
            fontFamily = NeoDgm,
            fontSize = 52.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "/",
            fontFamily = NeoDgm,
            fontSize = 42.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "500",
            fontFamily = NeoDgm,
            fontSize = 22.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = "kcal",
            fontFamily = NeoDgm,
            fontSize = 22.sp,
            color = Color.White
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
fun HomeCalorieScreenPreview() {
    HomeCalorieScreen()
}