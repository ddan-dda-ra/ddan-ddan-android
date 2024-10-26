package com.ddanddan.watch.presentation

/**
 * Displays a heart rate value with icon and label.
 */
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import kotlin.math.roundToInt

@Composable
fun CircularProgressWithCalories(
    calories: Double,
    goalCalories: Double?,
    modifier: Modifier = Modifier
) {
    val displayedCalories = if (calories.isNaN()) 0.0 else calories
    val progress = if (goalCalories != null && goalCalories > 0) {
        (displayedCalories / goalCalories).coerceIn(0.0, 1.0)
    } else {
        0.0
    }

    // 이미지를 보여줄지 텍스트를 보여줄지 관리하는 상태
    var isShowingImage by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable { isShowingImage = !isShowingImage }  // 클릭 시 상태 전환
    ) {
        // 원형 프로그레스바
        CircularProgressIndicator(
            progress = progress.toFloat(),
            strokeWidth = 12.dp,
            modifier = Modifier.fillMaxSize()
        )

        // 상태에 따라 이미지 또는 텍스트를 보여줌
        if (isShowingImage) {
            // 이미지를 표시하는 상태일 때
            Image(
                painter = painterResource(id = coil.compose.base.R.drawable.ic_100tb),  // 이미지 리소스 설정
                contentDescription = "Calorie Image",
                modifier = Modifier.size(64.dp)  // 이미지 크기 설정
            )
        } else {
            // 텍스트를 표시하는 상태일 때
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 칼로리 숫자 부분 (크기 48sp)
                Text(
                    text = displayedCalories.roundToInt().toString(),  // 숫자 텍스트
                    fontSize = 48.sp,
                    style = MaterialTheme.typography.caption3,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(4.dp))  // 숫자와 단위 사이에 약간의 간격 추가

                // 칼로리 단위 부분 (크기 16sp)
                Text(
                    text = "kcal",  // 단위 텍스트
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.caption3,
                    color = Color.White
                )
            }
        }
    }
}
