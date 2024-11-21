package com.ddanddan.ui.compose.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ddanddan.base.R
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.ext.draw9Patch
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun DDanAnimationTooltip(
    tooltipText: String,
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    onVisibilityChanged: (Boolean) -> Unit = {},
) {
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(1200)
            onVisibilityChanged(false)
        }
    }

    val offsetY: Float by animateFloatAsState(
        targetValue = if (isVisible) 0f else 50f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    if (alpha > 0f) {
        Box(
            modifier = modifier
                .offset {
                    IntOffset(0, (offsetY * density).roundToInt())
                }
                .graphicsLayer {
                    this.alpha = alpha
                }
                .draw9Patch(LocalContext.current, R.drawable.ic_tooltip)
                .wrapContentSize()
        ) {
            Text(
                text = tooltipText,
                color = DDanDDanColorPalette.current.color_text_button_primary_default,
                style = DDanDDanTypo.current.Body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 11.dp, bottom = 23.dp)
            )
        }
    }
}