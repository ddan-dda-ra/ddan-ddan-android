package com.ddanddan.ddanddan.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddanddan.ddanddan.R

@Composable
fun HomeScreen(
    onStorageClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    onEatClick: () -> Unit = {},
    onPlayClick: () -> Unit = {}
) {
    Column {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        HomeTopScreen(onStorageClick, onSettingClick)
        Spacer(modifier = Modifier.padding(top = 16.dp))
        HomeCalorieScreen()
        Spacer(modifier = Modifier.padding(top = 14.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_animal),
                contentDescription = "동물 이미지",
                modifier = Modifier.wrapContentSize()
            )
        }
        Spacer(modifier = Modifier.padding(top = 32.dp))
        HomeProgressbarScreen(progress = 0.25f, progressColor = Color(0xFFFD85FF), level = 5)
        Spacer(modifier = Modifier.padding(top = 20.dp))
        HomeBottomScreen(onEatClick, onPlayClick)
    }
}


@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
fun HomeScreenPreview() {
    HomeScreen()
}