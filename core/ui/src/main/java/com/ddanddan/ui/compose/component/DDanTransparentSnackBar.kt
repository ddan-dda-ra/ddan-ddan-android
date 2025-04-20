package com.ddanddan.ui.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.Pretendard

@Composable
fun DDanTransparentSnackBar(
    snackBarHostState: SnackbarHostState
) {
    SnackbarHost(hostState = snackBarHostState) { data ->
        Column (
            modifier = Modifier.padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = DDanDDanColorPalette.current.elevation_color_elevation_level03,
                        shape = RoundedCornerShape(1000.dp)
                    )
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val iconResId = (data.visuals as? DDanSnackbarVisuals)?.iconResId

                    iconResId?.let {
                        Image(
                            painter = painterResource(iconResId),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = data.visuals.message,
                        color = DDanDDanColorPalette.current.color_text_caption_primary_default,
                        style = DDanDDanTypo.current.Body1,
                        fontFamily = Pretendard
                    )
                }
            }
            val bottomPadding = (data.visuals as? DDanSnackbarVisuals)?.bottomPadding
            bottomPadding?.let { DDanMarginVerticalSpacer(it) }
        }
    }
}

data class DDanSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val iconResId: Int? = null,
    val bottomPadding: Int? = null
) : SnackbarVisuals

suspend fun SnackbarHostState.showSnackbar(
    message: String,
    iconResId: Int?,
    bottomPadding: Int? = null ,
    actionLabel: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short
) = showSnackbar(
    DDanSnackbarVisuals(
        message = message,
        actionLabel = actionLabel,
        duration = duration,
        iconResId = iconResId,
        bottomPadding = bottomPadding
    )
)