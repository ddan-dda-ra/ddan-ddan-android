package com.ddanddan.watch.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.ddanddan.watch.R
import com.ddanddan.watch.theme.PassiveDataTheme

@Composable
fun NotSupportedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.HeartBroken,
            contentDescription = stringResource(id = R.string.not_supported_calories_message),
            tint = Color.Red
        )
        Text(
            text = stringResource(id = R.string.not_supported_calories_message),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true
)
@Composable
fun NotSupportedScreenPreview() {
    PassiveDataTheme {
        NotSupportedScreen()
    }
}
