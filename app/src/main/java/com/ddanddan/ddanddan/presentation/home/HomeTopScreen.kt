package com.ddanddan.ddanddan.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddanddan.ddanddan.R

@Composable
fun HomeTopScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(painter = painterResource(id = R.drawable.ic_storage), contentDescription = "보관함")
        Image(painter = painterResource(id = R.drawable.ic_setting), contentDescription = "설정")
    }
}

@Composable
@Preview(showBackground = true)
fun HomeTopScreenPreview() {
    HomeTopScreen()
}