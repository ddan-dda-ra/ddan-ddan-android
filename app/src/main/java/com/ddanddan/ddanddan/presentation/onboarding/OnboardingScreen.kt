package com.ddanddan.ddanddan.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddanddan.ddanddan.R
import com.ddanddan.domain.entity.CommonViewPagerEntity
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo

@Composable
fun OnboardingRoute(
    onNavigateSignIn: () -> Unit
) {
    OnboardingScreen(
        onSkipOnboarding = onNavigateSignIn
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
fun OnboardingScreen (
    onSkipOnboarding: () -> Unit = { }
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            OnboardingPager(modifier = Modifier.weight(1f), pagerState = pagerState)
            StartBtn(onClick = onSkipOnboarding)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun OnboardingPager(
    modifier: Modifier,
    pagerState: PagerState
) {
    val onboardingPages = listOf(
        CommonViewPagerEntity(
            stringResource(com.ddanddan.base.R.string.onboarding_subtitle1),
            R.drawable.img_onboarding_1,
            true
        ),
        CommonViewPagerEntity(
            stringResource(com.ddanddan.base.R.string.onboarding_subtitle2),
            R.drawable.img_onboarding_2
        ),
        CommonViewPagerEntity(
            stringResource(com.ddanddan.base.R.string.onboarding_subtitle3),
            R.drawable.img_onboarding_3
        )
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState
        ) { page ->
            Column {
                Text(
                    modifier = Modifier
                        .padding(20.dp),
                    text = onboardingPages[page].title ?: "",
                    style = DDanDDanTypo.current.NeoDgm24,
                    color = DDanDDanColorPalette.current.color_text_headline_primary,
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.fillMaxWidth(), // 가로 전체 차지
                    painter = painterResource(id = onboardingPages[page].image ?: 0),
                    contentDescription = "온보딩 이미지 $page",
                    contentScale = if (onboardingPages[page].isFullWidth) ContentScale.FillWidth else ContentScale.Fit
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        // Indicator 추가
        OnboardingIndicator(
            pageCount = 3, // 페이지 개수
            currentPage = pagerState.currentPage
        )
        Spacer(modifier = Modifier.height(19.dp))
    }

}

@Composable
fun OnboardingIndicator(
modifier: Modifier = Modifier,
pageCount: Int,
currentPage: Int
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(width = 8.dp, height = 8.dp)
                    .background(
                        color = if (index == currentPage) DDanDDanColorPalette.current.color_icon_level02_active else DDanDDanColorPalette.current.color_icon_level02_disabled,
                        shape = RectangleShape
                    )
            )
            if (index < pageCount - 1) { // 마지막 아이템에는 Spacer 추가 X
                Spacer(modifier = Modifier.width(12.dp)) // ✅ 간격 추가
            }
        }
    }
}

@Composable
fun StartBtn(
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RectangleShape,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = DDanDDanColorPalette.current.color_button_active,
            contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
        )
    ) {
        Text(text = stringResource(com.ddanddan.base.R.string.onboarding_button_text), style = DDanDDanTypo.current.HeadLine6)
    }
}