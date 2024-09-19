package com.ddanddan.ui.compose

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimen(
    val defaultHorizontal: Dp = 20.dp
)

val DDanDDanDimen = staticCompositionLocalOf { Dimen() }