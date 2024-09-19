package com.ddanddan.ui.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.ddanddan.ui.compose.ColorPalette_Dark
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanDimen
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.Dimen
import com.ddanddan.ui.compose.Pink40
import com.ddanddan.ui.compose.Pink80
import com.ddanddan.ui.compose.Purple40
import com.ddanddan.ui.compose.Purple80
import com.ddanddan.ui.compose.PurpleGrey40
import com.ddanddan.ui.compose.PurpleGrey80
import com.ddanddan.ui.compose.Shapes
import com.ddanddan.ui.compose.Type
import com.ddanddan.ui.compose.Typography


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    // Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

    )

@Composable
fun DDanDDanTheme(
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val customColorPalette = ColorPalette_Dark

    CompositionLocalProvider(
        DDanDDanColorPalette provides customColorPalette,
        DDanDDanTypo provides Type(),
        DDanDDanDimen provides Dimen()
    ) {
        MaterialTheme(
            typography = Typography,
            content = content,
            shapes = Shapes,
            colors = colors
        )
    }
}