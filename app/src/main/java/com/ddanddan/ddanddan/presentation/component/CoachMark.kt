package com.ddanddan.ddanddan.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ddanddan.ddanddan.R
import com.ddanddan.ui.ext.noRippleClickable

@Composable
fun CoachMark(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable { onDismiss() }
        ) {
            // 어두운 배경 (전체 화면)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
            )
            
            // EggCounterBadge와 빛나는 효과
            Box(
                modifier = modifier
            ) {
                // 가장 큰 외부 그라데이션 원 (EggCounterBadge 중심 기준으로 offset)
                Box(
                    modifier = Modifier
                        .offset(x = (-30).dp, y = (-30).dp) // 중심으로 이동
                        .size(100.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.25f),
                                    Color.White.copy(alpha = 0.12f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
                
                // 중간 그라데이션 원
                Box(
                    modifier = Modifier
                        .offset(x = (-20).dp, y = (-20).dp)
                        .size(80.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.35f),
                                    Color.White.copy(alpha = 0.18f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
                
                // 내부 밝은 그라데이션 원
                Box(
                    modifier = Modifier
                        .offset(x = (-10).dp, y = (-10).dp)
                        .size(60.dp)
                        .shadow(
                            elevation = 15.dp,
                            shape = CircleShape,
                            ambientColor = Color.White,
                            spotColor = Color.White
                        )
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.45f),
                                    Color.White.copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
                
                // 실제 컨텐츠 (EggCounterBadge) - 원래 위치 유지
                content()
                
                // 툴팁 이미지 (컨텐츠 아래 8dp, 시작점으로부터 12dp)
                Image(
                    painter = painterResource(id = R.drawable.ic_tooltip_new_pet),
                    contentDescription = "새로운 펫 툴팁",
                    modifier = Modifier
                        .offset(x = (-4).dp, y = 48.dp)
                        .wrapContentSize()
                )
            }
        }
    }
}
