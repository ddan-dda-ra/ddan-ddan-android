package com.ddanddan.ddanddan.presentation.signup.terms

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.navigation.DDanDDanRoute
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.ext.noRippleClickable

@Composable
fun onTermsScreen(
    navController: NavHostController,
    onAgreeTerms: () -> Unit = { }
) {
    var isFirstTermAgreed by remember { mutableStateOf(false) }
    var isSecondTermAgreed by remember { mutableStateOf(false) }

    fun setTermAgreed(first: Boolean, second: Boolean) {
        isFirstTermAgreed = first
        isSecondTermAgreed = second
    }

    @Composable
    fun getIconColorFilter(isForFirst: Boolean): Color {
        return if (isForFirst && isFirstTermAgreed || !isForFirst && isSecondTermAgreed) DDanDDanColorPalette.current.color_icon_level02_active
        else DDanDDanColorPalette.current.color_icon_level04
    }

    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.signup_terms_title),
                    style = DDanDDanTypo.current.NeoDgm24,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.signup_terms_subtitle),
                    style = DDanDDanTypo.current.Body1,
                    color = DDanDDanColorPalette.current.color_text_body_quaternary
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .noRippleClickable {
                        if (isFirstTermAgreed && isSecondTermAgreed) setTermAgreed(first = false, second = false)
                        else setTermAgreed(first = true, second = true)
                    }
            ) {
                val iconRes = if (isFirstTermAgreed && isSecondTermAgreed) {
                    R.drawable.icon_radio_check_on
                } else {
                    R.drawable.icon_radio_check_non
                }
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = iconRes),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.signup_terms_all),
                    style = DDanDDanTypo.current.HeadLine7,
                    color = DDanDDanColorPalette.current.color_text_body_secondary
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .noRippleClickable { setTermAgreed(first = !isFirstTermAgreed, second = isSecondTermAgreed) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_check_bold),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(getIconColorFilter(isForFirst = true))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.signup_terms_necessary),
                    style = DDanDDanTypo.current.Body1,
                    color = DDanDDanColorPalette.current.color_text_button_secondary_default
                )
                Text(
                    text = stringResource(R.string.signup_terms_service),
                    style = DDanDDanTypo.current.Body1,
                    color = DDanDDanColorPalette.current.color_text_body_secondary
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right_l),
                    tint = DDanDDanColorPalette.current.color_icon_level03,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            navController.navigate(DDanDDanRoute.WEBVIEW.route + "?url=https://www.notion.so/4105267fc3b849fba10b8a3155809255")
                        }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .noRippleClickable { setTermAgreed(first = isFirstTermAgreed, second = !isSecondTermAgreed) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_check_bold),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(getIconColorFilter(isForFirst = false))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.signup_terms_necessary),
                    style = DDanDDanTypo.current.Body1,
                    color = DDanDDanColorPalette.current.color_text_button_secondary_default
                )
                Text(
                    text = stringResource(R.string.signup_terms_personal),
                    style = DDanDDanTypo.current.Body1,
                    color = DDanDDanColorPalette.current.color_text_body_secondary
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right_l),
                    tint = DDanDDanColorPalette.current.color_icon_level03,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            navController.navigate(DDanDDanRoute.WEBVIEW.route + "?url=https://www.notion.so/1d544c615c44412fa51d2ecb9f98116a")
                        }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            StartBtn(
                onClick = onAgreeTerms,
                isFirstTermAgreed = isFirstTermAgreed,
                isSecondTermAgreed = isSecondTermAgreed
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun StartBtn(
    onClick: () -> Unit = {},
    isFirstTermAgreed: Boolean,
    isSecondTermAgreed: Boolean
) {
    val btnContainerColor = if (isFirstTermAgreed && isSecondTermAgreed) {
        DDanDDanColorPalette.current.color_button_active
    } else {
        DDanDDanColorPalette.current.color_button_disabled
    }
    val btnContentColor = if (isFirstTermAgreed && isSecondTermAgreed) {
        DDanDDanColorPalette.current.color_text_button_primary_default
    } else {
        DDanDDanColorPalette.current.color_text_button_primary_disabled
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RectangleShape,
        onClick = {
            if (isFirstTermAgreed && isSecondTermAgreed) onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = btnContainerColor,
            contentColor = btnContentColor
        )
    ) {
        Text(text = stringResource(R.string.signup_terms_button_text), style = DDanDDanTypo.current.HeadLine6)
    }
}