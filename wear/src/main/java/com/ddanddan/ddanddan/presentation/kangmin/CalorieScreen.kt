package com.ddanddan.ddanddan.presentation.kangmin

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import coil.compose.AsyncImage
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.theme.DDanDDanTheme
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.ext.noRippleClickable
import timber.log.Timber

// 펫 색을 받지 못했을 때(구버전 폰 등) 쓰는 기본 진행 바 색
private val DefaultProgressColor = Color(0xFFF8A5FF)

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CalorieRoute(
    viewModel: CalorieViewModel = hiltViewModel()
) {
    var isCalorieTextDisplay by remember { mutableStateOf(false) }
    val todayCalories by viewModel.caloriesValue.collectAsState()
    val goalCalories by viewModel.targetCalories.collectAsState()
    val petType by viewModel.petType.collectAsState()
    val petLevel by viewModel.petLevel.collectAsState()
    val petImageUrl by viewModel.petImageUrl.collectAsState()
    val petColorHex by viewModel.petColor.collectAsState()
    // 파싱에 실패하면 null이 되어 기본 색(핑크)으로 그려진다
    val petColor = remember(petColorHex) {
        petColorHex?.let { runCatching { Color(android.graphics.Color.parseColor(it)) }.getOrNull() }
    }

    Timber.tag("kangmi").d("todayCalories: $todayCalories, goalCalories: $goalCalories")
    CalorieScreen(
        todayCalories = todayCalories,
        goalCalories = goalCalories.toDouble(),
        petType = petType,
        petLevel = petLevel,
        petImageUrl = petImageUrl,
        petColor = petColor,
        isCalorieTextDisplay = isCalorieTextDisplay,
        onDisplayStateChanged = { isCalorieTextDisplay = !isCalorieTextDisplay }
    )
}

@Composable
fun CalorieScreen(
    modifier: Modifier = Modifier,
    todayCalories: Double = 0.0,
    goalCalories: Double = 400.0,
    petType: String = "CAT",
    petLevel: Int = 1,
    petImageUrl: String? = null,
    petColor: Color? = null,
    isCalorieTextDisplay: Boolean = true,
    onDisplayStateChanged: () -> Unit = {}
) {
    val progress = calculateProgress(todayCalories, goalCalories)
    CircularProgressWatchFace(
        modifier = modifier,
        progress = progress,
        progressColor = petColor ?: DefaultProgressColor,
        onDisplayStateChanged = onDisplayStateChanged
    ) {
        if (isCalorieTextDisplay) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = todayCalories.toInt().toString(),
                    style = DDanDDanTypo.current.NeoDgm48,
                    color = Color.White
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = "kcal",
                    style = DDanDDanTypo.current.NeoDgm16,
                    letterSpacing = 1.sp,
                    color = Color.White
                )
            }

        }
        else {
            val bundledPet = painterResource(id = petType.toAnimal(petLevel))
            if (petImageUrl != null) {
                // 서버 카탈로그 이미지. 로딩 중이거나 받기 실패하면 번들 이미지로 대체한다
                AsyncImage(
                    model = petImageUrl,
                    contentDescription = "Pet Image",
                    placeholder = bundledPet,
                    error = bundledPet,
                    modifier = Modifier.size(100.dp)
                )
            } else {
                Image(
                    painter = bundledPet,
                    contentDescription = "Pet Image"
                )
            }
        }
    }
}

@Composable
fun CircularProgressWatchFace(
    modifier: Modifier = Modifier,
    progress: Double = 0.1, // 0.0 ~ 1.0 범위의 진행도
    progressColor: Color = DefaultProgressColor, // 핑크색 프로그레스 바
    backgroundColor: Color = Color(0xFF333333), // 배경색 (어두운 회색)
    strokeWidth: Dp = 8.dp, // 프로그레스 바 두께
    onDisplayStateChanged: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f) // 정사각형 비율 유지
            .background(DDanDDanColorPalette.current.color_background, shape = CircleShape)
            .noRippleClickable {
                onDisplayStateChanged()
            },
        contentAlignment = Alignment.Center
    ) {
        // 배경 원
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidthPx = strokeWidth.toPx()
            // 선이 Canvas 밖으로 넘어가지 않게하는 로직
            // strokeWidthPx를 빼지않으면 선이 꽉차서 보이지않음
            val diameter = size.minDimension - strokeWidthPx
            // 원을 Canvas 정중앙에 배치하기 위한 좌상단 좌표
            val topLeft = Offset(
                (size.width - diameter) / 2,
                (size.height - diameter) / 2
            )
            val size = Size(diameter, diameter)

            // 배경 원 그리기
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )

            // 프로그레스 원 그리기
            drawArc(
                color = progressColor,
                startAngle = 270f, // 12시 방향에서 시작
                sweepAngle = 360f * progress.toFloat(),
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Butt)
            )
        }

        // 내부 컨텐츠를 위한 Box
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(strokeWidth + 4.dp)  // 선 두께 + 추가 패딩
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

private fun calculateProgress(calories: Double, goal: Double?): Double {
    return when {
        goal == null || goal <= 0 -> 0.0
        else -> (calories / goal).coerceIn(0.0, 1.0)
    }
}

@Composable
@WearPreviewDevices
fun CalorieScreenPreview() {
    DDanDDanTheme {
        CalorieScreen()
    }
}
