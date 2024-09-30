package com.ddanddan.ui.compose.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.ddanddan.ui.compose.DDanDDanColorPalette
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.base.R
import kotlinx.serialization.json.JsonNull.content

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DdanScaffold(
    topbarText: String = "",
    onClick: (() -> Unit)? = null,
    onConfirmClick: (() -> Unit)? = null,
    isConfirm: Boolean = false,
    isSetting: Boolean = true,
    content: @Composable (PaddingValues) -> Unit,
) {
    val DDanColorPalette = DDanDDanColorPalette
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = topbarText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = DDanDDanTypo.current.HeadLine6,
                            color = DDanColorPalette.current.color_text_headline_primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.weight(1f))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DDanColorPalette.current.color_background,
                    titleContentColor = DDanColorPalette.current.color_text_headline_primary,
                    navigationIconContentColor = DDanColorPalette.current.color_text_headline_primary,
                    actionIconContentColor = DDanColorPalette.current.color_icon_level01
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (onClick != null) {
                                onClick()
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left_l),
                            contentDescription = "Localized description",
                            tint = DDanColorPalette.current.color_icon_level01,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (isConfirm) {
                                if (onConfirmClick != null) {
                                    onConfirmClick()
                                }
                            } else if (!isConfirm) {
                                if (onClick != null) {
                                    onClick()
                                }
                            }
                        },
                        enabled = false
                    ) {
                        Icon(
                            painter = if (isSetting) painterResource(R.drawable.ic_arrow_right_l) else if (isConfirm) painterResource(
                                R.drawable.ic_arrow_right_l
                            ) else painterResource(R.drawable.ic_arrow_right_l),
                            contentDescription = "Localized description",
                            tint = if (isSetting) Color.Transparent else if (isConfirm) Color.Transparent else Color.Transparent
                        )
                    }
                }
            )
        },
        content = { it ->
            content(it)
        }
    )
}