package com.ddanddan.ddanddan.presentation.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddanddan.ddanddan.util.toColor
import com.ddanddan.domain.entity.Pet
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm

@Composable
fun HomeProgressbarScreen(
    homeState: HomeState,
    modifier: Modifier = Modifier
) {
    val totalSegments = 24
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // LV.5 박스
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(DDanDDanColorPalette.current.elevation_color_elevation_level02)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "LV.${homeState.pet?.level}",
                    color = DDanDDanColorPalette.current.color_text_headline_primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = NeoDgm
                )
            }

            Text(
                text = "${homeState.pet?.expPercent}%",
                color = DDanDDanColorPalette.current.color_text_headline_primary,
                style = DDanDDanTypo.current.SubTitle1
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        DDanDDanProgressbar(
            totalSegments = totalSegments,
            progress = ((homeState.pet?.expPercent ?: 0.0) * 0.01).toFloat(),
            progressColor = homeState.pet?.type.toColor()
        )
    }
}

@Composable
fun DDanDDanProgressbar(
    totalSegments: Int,
    progress: Float,
    progressColor: Color
) {
    val defaultSegmentColor = Color.Transparent
    val filledSegments = (progress * totalSegments).toInt()
    val strokeColor = DDanDDanColorPalette.current.color_divider_level03


    // 프로그레스 바
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(strokeColor)
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DDanDDanColorPalette.current.color_background),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(totalSegments) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(if (index < filledSegments) progressColor else defaultSegmentColor)
                    )
                }
            }
        }

        val color = DDanDDanColorPalette.current.color_background

        Canvas(modifier = Modifier.fillMaxSize()) {
            val cornerSize = 4.dp.toPx()
            val strokeWidth = 4.dp.toPx()

            drawRect(
                color,
                Offset(-strokeWidth, -strokeWidth),
                Size(cornerSize + strokeWidth, cornerSize + strokeWidth)
            )
            drawRect(
                color,
                Offset(size.width - cornerSize, -strokeWidth),
                Size(cornerSize + strokeWidth, cornerSize + strokeWidth)
            )
            drawRect(
                color,
                Offset(-strokeWidth, size.height - cornerSize),
                Size(cornerSize + strokeWidth, cornerSize + strokeWidth)
            )
            drawRect(
                color,
                Offset(size.width - cornerSize, size.height - cornerSize),
                Size(cornerSize + strokeWidth, cornerSize + strokeWidth)
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFF111111
)
fun HomeProgressbarScreenPreview() {
    HomeProgressbarScreen(
        homeState = HomeState(pet = Pet("cat", PetTypeEnum.CAT, 1, 1.0))
    )
}