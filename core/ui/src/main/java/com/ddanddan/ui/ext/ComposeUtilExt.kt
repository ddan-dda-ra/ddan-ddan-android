package com.ddanddan.ui.ext

import android.content.Context
import android.graphics.Rect
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.content.ContextCompat

/**
 * 릴플 효과 없는 클릭 이벤트
 **/
fun Modifier.noRippleClickable(
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

/**
 * 나인 패치 가져오는 로직
 */
fun Modifier.draw9Patch(
    context: Context,
    @DrawableRes ninePatchRes: Int,
) = composed {
    val drawable = remember(context, ninePatchRes) {
        ContextCompat.getDrawable(context, ninePatchRes)
    }

    drawBehind {
        drawIntoCanvas {
            drawable?.let { ninePatch ->
                ninePatch.run {
                    bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
                    draw(it.nativeCanvas)
                }
            }
        }
    }
}