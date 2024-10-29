package com.ddanddan.watch.presentation

/**
 * 이미지와 텍스트로 칼로리 값을 표시합니다.
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

enum class DisplayState {
    IMAGE, TEXT
}

@Composable
fun CalorieProgressBar(
    calories: Double,
    goalCalories: Double?,
    modifier: Modifier = Modifier
) {
    val displayedCalories = calories.takeIf { !it.isNaN() } ?: 0.0
    val progress = calculateProgress(displayedCalories, goalCalories)

    var displayState by remember { mutableStateOf(DisplayState.TEXT) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable {
                displayState = when (displayState) {
                    DisplayState.IMAGE -> DisplayState.TEXT
                    DisplayState.TEXT -> DisplayState.IMAGE
                }
            }
    ) {
        CircularProgressIndicator(
            progress = progress.toFloat(),
            strokeWidth = 12.dp,
            modifier = Modifier.fillMaxSize()
        )

        when (displayState) {
            DisplayState.IMAGE -> {
                Image(
                    painter = painterResource(id = com.google.android.horologist.compose.tools.R.drawable.sample_image),
                    contentDescription = "Calorie Image",
                    modifier = Modifier.size(64.dp)
                )
            }
            DisplayState.TEXT -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = displayedCalories.roundToInt().toString(),
                        fontSize = 48.sp,
                        style = MaterialTheme.typography.caption3,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "kcal",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.caption3,
                        color = Color.White
                    )
                }
            }
        }
    }
}

private fun calculateProgress(calories: Double, goal: Double?): Double {
    return when {
        goal == null || goal <= 0 -> 0.0
        else -> (calories / goal).coerceIn(0.0, 1.0)
    }
}