package com.ddanddan.ddanddan.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.ddanddan.ddanddan.domain.entity.MainPet
import com.ddanddan.ddanddan.presentation.theme.DDanDDanTheme
import com.ddanddan.ddanddan.util.PetUtils
import com.ddanddan.ui.compose.NeoDgm
import kotlin.math.roundToInt

enum class DisplayState {
    IMAGE, TEXT
}

@Composable
fun CalorieProgressBar(
    calories: Double,
    goalCalories: Double?,
    mainPet: MainPet,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 24.dp
) {
    val displayedCalories = calculateDisplayedCalories(calories)
    val progress = calculateProgress(displayedCalories, goalCalories)
    val isGoalAchieved = checkGoalAchieved(displayedCalories, goalCalories)

    var displayState by remember { mutableStateOf(DisplayState.IMAGE) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable { toggleDisplayState(displayState) { newState -> displayState = newState } }
    ) {
        CircularProgressBar(progress = progress, strokeWidth = strokeWidth, mainPet = mainPet)

        when (displayState) {
            DisplayState.IMAGE -> MainPetImage(mainPet)
            DisplayState.TEXT -> CalorieInfo(displayedCalories, isGoalAchieved, mainPet)
        }
    }
}

@Composable
fun CircularProgressBar(progress: Double, strokeWidth: Dp, mainPet: MainPet) {
    val progressColor = PetUtils.getProgressBarColor(mainPet)
    val colors = DDanDDanColorPalette.current

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawProgressBarBackground(colors.color_elevation_lev02, strokeWidth.toPx())
        drawProgressBar(progress, progressColor, strokeWidth.toPx())
    }
}

private fun DrawScope.drawProgressBarBackground(color: Color, strokeWidth: Float) {
    drawArc(
        color = color,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(width = strokeWidth)
    )
}

private fun DrawScope.drawProgressBar(progress: Double, color: Color, strokeWidth: Float) {
    drawArc(
        color = color,
        startAngle = -90f, // 12시 방향 시작
        sweepAngle = (360 * progress).toFloat(),
        useCenter = false,
        style = Stroke(width = strokeWidth, cap = StrokeCap.Butt) // 모서리 각지게
    )
}

@Composable
fun MainPetImage(mainPet: MainPet) {
    val resourceId = PetUtils.getDrawableResourceId(mainPet)

    Image(
        painter = painterResource(id = resourceId),
        contentDescription = "Pet Image",
        modifier = Modifier
            .fillMaxSize()
            .padding(34.dp)
    )
}

@Composable
fun CalorieInfo(displayedCalories: Double, isGoalAchieved: Boolean, mainPet: MainPet) {
    val textColor = determineTextColor(isGoalAchieved, mainPet)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Text(
            text = displayedCalories.roundToInt().toString(),
            fontSize = 48.sp,
            fontFamily = NeoDgm,
            color = textColor,
            modifier = Modifier.alignByBaseline()
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "kcal",
            fontSize = 16.sp,
            fontFamily = NeoDgm,
            color = textColor,
            modifier = Modifier.alignByBaseline()
        )
    }
}

@Composable
private fun determineTextColor(isGoalAchieved: Boolean, mainPet: MainPet): Color {
    return if (isGoalAchieved) {
        PetUtils.getProgressBarColor(mainPet) // 캐릭터 색상
    } else {
        Color.White // 기본 색상
    }
}

/** Util functions **/

private fun calculateDisplayedCalories(calories: Double): Double {
    return calories.takeIf { !it.isNaN() } ?: 0.0
}

private fun calculateProgress(calories: Double, goal: Double?): Double {
    return when {
        goal == null || goal <= 0 -> 0.0
        else -> (calories / goal).coerceIn(0.0, 1.0)
    }
}

private fun checkGoalAchieved(calories: Double, goal: Double?): Boolean {
    return calories >= (goal ?: Double.MAX_VALUE)
}

private fun toggleDisplayState(
    currentState: DisplayState,
    onStateChanged: (DisplayState) -> Unit
) {
    val newState = when (currentState) {
        DisplayState.IMAGE -> DisplayState.TEXT
        DisplayState.TEXT -> DisplayState.IMAGE
    }
    onStateChanged(newState)
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 300,
    heightDp = 300
)
@Composable
fun PreviewCalorieProgressBar() {
    DDanDDanTheme {
        val sampleCalories = 750.0
        val sampleGoalCalories = 1000.0
        val sampleMainPet = MainPet(
            expPercent = 50.0,
            id = "1",
            level = 4,
            type = "CAT"
        )

        CalorieProgressBar(
            calories = sampleCalories,
            goalCalories = sampleGoalCalories,
            mainPet = sampleMainPet,
            modifier = Modifier.fillMaxSize()
        )
    }
}