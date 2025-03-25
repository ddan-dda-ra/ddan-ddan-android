package com.ddanddan.ui.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddanddan.base.R
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo

@Preview
@Composable
fun DDanActionButton(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_action_apple,
    text: String = "먹이주기",
    count: Int = 3,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_bg_action_btn),
            contentDescription = "버튼 배경",
            modifier = Modifier.fillMaxHeight()
                .aspectRatio(141f / 95f)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = "아이콘"
            )
            DDanMarginVerticalSpacer(4)
            Row {
                Text(
                    modifier = Modifier.height(22.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    text = text,
                    style = DDanDDanTypo.current.NeoDgm16,
                    letterSpacing = (-1).sp,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )
                DDanMarginHorizontalSpacer(4)
                Text(
                    modifier = Modifier.height(22.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    text = "x",
                    style = DDanDDanTypo.current.NeoDgm16,
                    letterSpacing = (-1).sp,
                    color = DDanDDanColorPalette.current.color_text_headline_teritary
                )
                DDanMarginHorizontalSpacer(4)
                Text(
                    modifier = Modifier.height(22.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    text = count.toString(),
                    style = DDanDDanTypo.current.NeoDgm16,
                    letterSpacing = 1.sp,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )
            }
            DDanMarginVerticalSpacer(3)
        }
    }
}