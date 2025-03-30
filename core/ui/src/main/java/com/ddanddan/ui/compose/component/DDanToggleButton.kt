package com.ddanddan.ui.compose.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddanddan.ui.compose.DDanDDanColorPalette

@Preview
@Composable
fun DDanToggleButton(
    isOn: Boolean = true,
) {
    // 아이콘 크기 및 위치 애니메이션
    val iconSize by animateDpAsState(
        targetValue = if (isOn) 24.dp else 16.dp,
        label = "icon size"
    )

    val iconOffset by animateDpAsState(
        targetValue = if (isOn) 22.dp else 6.dp,
        label = "icon offset"
    )

    Box(
        modifier = Modifier
            .size(width = 48.dp, height = 28.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(if (isOn) DDanDDanColorPalette.current.color_button_active
                else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (!isOn) DDanDDanColorPalette.current.color_outline_level03
                    else Color.Transparent,
                shape = RoundedCornerShape(50.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = iconOffset)
                .size(iconSize)
                .clip(CircleShape)
                .background(
                    color = if (isOn) DDanDDanColorPalette.current.color_icon_level01
                        else DDanDDanColorPalette.current.color_button_alternative
                )
        )
    }
}