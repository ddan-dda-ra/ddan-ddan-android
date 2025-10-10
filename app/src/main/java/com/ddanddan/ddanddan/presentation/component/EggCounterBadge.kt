package com.ddanddan.ddanddan.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddanddan.ddanddan.R
import com.ddanddan.ui.compose.ColorPalette_Dark
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.theme.DDanDDanTheme

@Composable
fun EggCounterBadge(
    eggCount: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        // 검은색 원형 배경
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
                .background(DDanDDanColorPalette.current.elevation_color_elevation_level01)
        ) {
            // 아이콘 (안쪽 여백 8dp)
            Image(
                painter = painterResource(id = if(eggCount > 0) R.drawable.ic_egg_true else R.drawable.ic_egg_false),
                contentDescription = "알 아이콘",
                modifier = Modifier.wrapContentSize().padding(8.dp)
            )
        }

        // 카운트 배지 (오른쪽 하단에 2dp 겹치게 배치)
        val text = if (eggCount > 99) "99+" else "$eggCount"

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .offset(x = 24.dp, y = 24.dp) // 오른쪽 하단으로 2dp 겹치게 배치
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .clip(CircleShape)
                    .background(if (eggCount > 0) ColorPalette_Dark.color_icon_level02_alternative else ColorPalette_Dark.color_icon_level04)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// 미리보기
@Composable
@Preview
fun EggCounterBadgePreview() {
    DDanDDanTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            EggCounterBadge(eggCount = 0)
            EggCounterBadge(eggCount = 1)
            EggCounterBadge(eggCount = 5)
            EggCounterBadge(eggCount = 100)
        }
    }
}
