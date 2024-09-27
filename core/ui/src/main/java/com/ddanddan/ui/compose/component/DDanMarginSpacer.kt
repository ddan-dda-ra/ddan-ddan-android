package com.ddanddan.ui.compose.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DdanMarginVerticalSpacer(
    size: Int
) {
    Spacer(modifier = Modifier.width(0.dp).height(size.dp))
}

@Composable
fun DdanMarginHorizontalSpacer(
    size: Int
) {
    Spacer(modifier = Modifier.height(0.dp).width(size.dp))
}