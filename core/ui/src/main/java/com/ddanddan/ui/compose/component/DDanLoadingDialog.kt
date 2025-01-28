package com.ddanddan.ui.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ddanddan.ui.compose.DDanDDanTypo

@Preview
@Composable
fun DDanLoadingDialog(
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Color.White, // ProgressBar 색상
                modifier = Modifier.size(48.dp) // ProgressBar 크기
            )
            Spacer(modifier = Modifier.height(10.dp)) // TextView와의 간격
            Text(
                text = "Loading ...",
                color = Color.White,
                style = DDanDDanTypo.current.NeoDgm16, // XML의 font 설정
            )
        }
    }
}