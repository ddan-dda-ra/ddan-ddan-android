package com.ddanddan.ddanddan.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorPalette(
    val color_graphic_pink: Color = Color.Unspecified,
    val color_graphic_green: Color = Color.Unspecified,
    val color_graphic_blue: Color = Color.Unspecified,
    val color_graphic_purple: Color = Color.Unspecified,

    val color_elevation_lev02: Color = Color.Unspecified,
    val color_background: Color = Color.Unspecified
)

val ColorPalette_Dark = ColorPalette(
    color_graphic_pink = Color(color = 0xFFFD85FF),
    color_graphic_green = Color(color = 0xFF46F8A2),
    color_graphic_purple = Color(color = 0xFF9B6CFF),
    color_graphic_blue = Color(color = 0xFF4E95FF),

    color_elevation_lev02 = Color(color = 0xFF333333),
    color_background = Color(color = 0xFF111111)
)

val ColorPalette_Light = ColorPalette(
    color_graphic_pink = Color(color = 0xFFFD85FF),
    color_graphic_green = Color(color = 0xFF46F8A2),
    color_graphic_purple = Color(color = 0xFF9B6CFF),
    color_graphic_blue = Color(color = 0xFF4E95FF),

    color_elevation_lev02 = Color(color = 0xFF333333),
    color_background = Color(color = 0xFFFFFFFF)
)

val DDanDDanColorPalette = staticCompositionLocalOf { ColorPalette() }
