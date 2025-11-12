package com.ddanddan.ddanddan.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddanddan.ddanddan.R
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer

@Composable
fun DDanDDanBottomBar(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    val items = listOf(
        DDanDDanNavItem(DDanDDanRoute.HOME.route, stringResource(R.string.bottom_navi_home), R.drawable.ic_home),
        DDanDDanNavItem(DDanDDanRoute.RANKING.route, stringResource(R.string.bottom_navi_ranking), R.drawable.ic_trophy),
        DDanDDanNavItem(DDanDDanRoute.FRIENDS.route, stringResource(R.string.bottom_navi_friends), R.drawable.ic_friends),
        DDanDDanNavItem(DDanDDanRoute.SETTING.route, stringResource(R.string.bottom_navi_mypage), R.drawable.ic_mypage)
    )

    Column(
        modifier = Modifier
            .background(
                color = DDanDDanColorPalette.current.color_background
            )
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = DDanDDanColorPalette.current.color_divider_level02
        )

        NavigationBar(
            modifier = Modifier
                .height(68.dp)
                .padding(horizontal = 24.dp),
            containerColor = DDanDDanColorPalette.current.color_background,
            contentColor = DDanDDanColorPalette.current.color_icon_level04
        ) {
            items.forEach { item ->
                val isSelected = currentRoute?.substringBefore("?") == item.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemClick(item.route) },
                    alwaysShowLabel = false,
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(item.iconRes),
                                contentDescription = item.label,
                                tint = if (isSelected) {
                                    DDanDDanColorPalette.current.color_icon_level01
                                } else {
                                    DDanDDanColorPalette.current.color_icon_level04
                                },
                                modifier = Modifier.size(24.dp)
                            )

                            DDanMarginVerticalSpacer(4)

                            Text(
                                text = item.label,
                                color = if (isSelected) {
                                    DDanDDanColorPalette.current.color_icon_level01
                                } else {
                                    DDanDDanColorPalette.current.color_icon_level04
                                },
                                fontSize = 11.sp,
                                lineHeight = 11.sp,
                                style = DDanDDanTypo.current.Caption1,
                                maxLines = 1
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DDanDDanColorPalette.current.color_icon_level01,
                        selectedTextColor = DDanDDanColorPalette.current.color_icon_level01,
                        unselectedIconColor = DDanDDanColorPalette.current.color_icon_level04,
                        unselectedTextColor = DDanDDanColorPalette.current.color_icon_level04,
                        indicatorColor = Color.Transparent
                    )
                )

            }
        }
    }
}

data class DDanDDanNavItem(
    val route: String,
    val label: String,
    val iconRes: Int
)