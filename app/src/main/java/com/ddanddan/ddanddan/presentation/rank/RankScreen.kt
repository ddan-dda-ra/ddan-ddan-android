package com.ddanddan.ddanddan.presentation.rank

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.ddanddan.util.toColor
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.Pretendard
import com.ddanddan.ui.compose.component.DDanMarginHorizontalSpacer
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DDanTransparentSnackBar
import com.ddanddan.ui.compose.component.DdanScaffold
import com.ddanddan.ui.compose.component.showSnackbar
import com.ddanddan.ui.ext.noRippleClickable
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDate

@Composable
fun RankRoute(
    rankViewModel: RankViewModel = hiltViewModel(),
    navigatePopUp: () -> Unit
) {
    val rankState by rankViewModel.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        rankViewModel.setCriteriaTab(RankCriteria.TOTAL_CALORIES)
    }

    rankViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RankSideEffect.NavigatePopUp -> navigatePopUp()
            is RankSideEffect.NetworkError -> {}
            is RankSideEffect.SnackBarMsg -> {
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        message = sideEffect.msg,
                        iconResId = sideEffect.icon,
                        duration = SnackbarDuration.Short,
                        bottomPadding = 88
                    )
                }
            }
            is RankSideEffect.UserDataEmpty -> {
                rankViewModel.patchDailyCalories()
            }
        }
    }

    RankScreen(
        rankState = rankState,
        snackBarHostState = snackBarHostState,
        navigatePopUp = rankViewModel::onBackButtonClicked,
        changeTab = rankViewModel::setCriteriaTab,
        dismissToolTip = rankViewModel::dismissToolTip,
        showToolTip = rankViewModel::showToolTip,
        onSnackBarEvent = rankViewModel::showSnackBarEvent
    )
}

@Preview
@Composable
fun RankScreen(
    rankState: RankState = RankState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navigatePopUp: () -> Unit = {},
    changeTab: (RankCriteria) -> Unit = {},
    dismissToolTip: () -> Unit = {},
    showToolTip: () -> Unit = {},
    onSnackBarEvent: (String, Int) -> Unit = { _, _ -> }
) {
    DdanScaffold(
        topbarText = stringResource(id = R.string.rank_topbar_title),
        snackbarHost = {
            DDanTransparentSnackBar(snackBarHostState = snackBarHostState)
        },
        onClick = {
            navigatePopUp()
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(DDanDDanColorPalette.current.color_background)
        ) {
            RankTapLayout(
                modifier = Modifier.weight(1f),
                onTabChange = changeTab,
                rankState = rankState,
                showToolTip = showToolTip,
                dismissToolTip = dismissToolTip,
                onOverScroll = onSnackBarEvent
            )
            MyRecordBottomSheet(rankState = rankState)
        }
    }
}

@Preview (showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun MyRecordBottomSheet(
    rankState: RankState = RankState()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(DDanDDanColorPalette.current.elevation_color_elevation_level02)
    ) {
        if (rankState.myRank != null) {
            SimpleRankerView(
                criteria = rankState.criteria,
                rank = rankState.myRank.rank,
                nickname = rankState.myRank.userName,
                contents = if (rankState.criteria == RankCriteria.TOTAL_CALORIES) rankState.myRank.totalCalories else rankState.myRank.totalSucceededDays,
                mainPetType = rankState.myRank.mainPetType,
                petLevel = rankState.myRank.petLevel,
                isBottomSheet = true,
                isMyRecord = true
            )
        }
    }
}

@Preview
@Composable
private fun RankListView(
    rankState: RankState = RankState(),
    showToolTip: () -> Unit = {},
    dismissToolTip: () -> Unit = {}
) {
    val today = LocalDate.now()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        item {
            DDanMarginVerticalSpacer(24)
            Text(
                text = "${today.year}년 ${today.monthValue}월 기준",
                style = DDanDDanTypo.current.Body2,
                fontFamily = Pretendard,
                color = DDanDDanColorPalette.current.color_text_headline_teritary,
                modifier = Modifier.height(22.dp)
            )
            DDanMarginVerticalSpacer(4)
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (title, icon, ranking, tooltip) = createRefs()

                Text(
                    text = stringResource(rankState.criteria.toSubTitle()),
                    style = DDanDDanTypo.current.NeoDgm24,
                    fontFamily = NeoDgm,
                    color = DDanDDanColorPalette.current.color_text_headline_secondary,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Image(
                    painter = painterResource(R.drawable.ic_system_line),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 20.dp, height = 20.dp)
                        .noRippleClickable {
                            if (rankState.showToolTip) dismissToolTip() else showToolTip()
                        }
                        .constrainAs(icon) {
                            bottom.linkTo(title.bottom)
                            start.linkTo(title.end, margin = 8.dp)
                        }
                )

                Column(
                    modifier = Modifier.constrainAs(ranking) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ) {
                    DDanMarginVerticalSpacer(32)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TopRankerView(
                            modifier = Modifier.weight(1f),
                            criteria = rankState.criteria,
                            rank = rankState.silverRank?.rank ?: 2,
                            nickname = rankState.silverRank?.userName,
                            contents = if (rankState.criteria == RankCriteria.TOTAL_CALORIES) rankState.silverRank?.totalCalories
                            else rankState.silverRank?.totalSucceededDays,
                            mainPetType = rankState.silverRank?.mainPetType,
                            petLevel = rankState.silverRank?.petLevel
                        )
                        DDanMarginHorizontalSpacer(13)
                        TopRankerView(
                            modifier = Modifier.weight(1f),
                            criteria = rankState.criteria,
                            rank = 1,
                            nickname = rankState.goldRank?.userName,
                            contents = if (rankState.criteria == RankCriteria.TOTAL_CALORIES) rankState.goldRank?.totalCalories
                            else rankState.goldRank?.totalSucceededDays,
                            mainPetType = rankState.goldRank?.mainPetType,
                            petLevel = rankState.goldRank?.petLevel,
                            isCenter = true
                        )
                        DDanMarginHorizontalSpacer(13)
                        TopRankerView(
                            modifier = Modifier.weight(1f),
                            criteria = rankState.criteria,
                            rank = rankState.bronzeRank?.rank ?: 3,
                            nickname = rankState.bronzeRank?.userName,
                            contents = if (rankState.criteria == RankCriteria.TOTAL_CALORIES) rankState.bronzeRank?.totalCalories
                            else rankState.bronzeRank?.totalSucceededDays,
                            mainPetType = rankState.bronzeRank?.mainPetType,
                            petLevel = rankState.bronzeRank?.petLevel
                        )
                    }
                }

                if (rankState.showToolTip) {
                    ConstraintLayout (
                        modifier = Modifier
                            .constrainAs(tooltip) {
                                top.linkTo(icon.bottom)
                                start.linkTo(icon.start)
                                end.linkTo(icon.end)
                            }
                            .noRippleClickable {
                                dismissToolTip()
                            }
                    ) {
                        val (polygon, msg) = createRefs()
                        Image(
                            painter = painterResource(R.drawable.ic_tooltip_polygon),
                            colorFilter = ColorFilter.tint(DDanDDanColorPalette.current.elevation_color_elevation_level02),
                            modifier = Modifier.size(16.dp)
                                .constrainAs(polygon) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            contentDescription = null
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(DDanDDanColorPalette.current.elevation_color_elevation_level02)
                                .constrainAs(msg) {
                                    top.linkTo(polygon.top, margin = 8.dp)
                                    start.linkTo(parent.start)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            Text(
                                text = stringResource(if (rankState.criteria == RankCriteria.TOTAL_CALORIES) R.string.rank_tooltip_calorie else R.string.rank_tooltip_target),
                                style = DDanDDanTypo.current.SubTitle1,
                                fontFamily = Pretendard,
                                color = DDanDDanColorPalette.current.color_text_headline_secondary,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
            DDanMarginVerticalSpacer(17)
        }

        // 4~100등
        items(rankState.otherRanking.size) { idx ->
            SimpleRankerView(
                criteria = rankState.criteria,
                rank = rankState.otherRanking[idx].rank,
                nickname = rankState.otherRanking[idx].userName,
                contents = if (rankState.criteria == RankCriteria.TOTAL_CALORIES) rankState.otherRanking[idx].totalCalories
                    else rankState.otherRanking[idx].totalSucceededDays,
                mainPetType = rankState.otherRanking[idx].mainPetType,
                petLevel = rankState.otherRanking[idx].petLevel,
                isMyRecord = rankState.otherRanking[idx].userId == rankState.myRank?.userId
            )
        }

        item {
            DDanMarginVerticalSpacer(20)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun RankTapLayout(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState { 2 },
    rankState: RankState = RankState(),
    onTabChange: (RankCriteria) -> Unit = { },
    showToolTip: () -> Unit = { },
    dismissToolTip: () -> Unit = { },
    onOverScroll: (String, Int) -> Unit = { _, _ -> }
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(RankCriteria.TOTAL_CALORIES, RankCriteria.TOTAL_SUCCEEDED_DAYS)

    LaunchedEffect(pagerState.currentPage) {
        onTabChange(tabs[pagerState.currentPage])
    }

    Column(
        modifier = modifier
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .padding(
                            start = if (pagerState.currentPage == 0) 20.dp else 0.dp,
                            end = if (pagerState.currentPage == 1) 20.dp else 0.dp
                        ),
                    color = DDanDDanColorPalette.current.color_outline_level04_active,
                    height = 2.dp
                )
            },
            divider = {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = DDanDDanColorPalette.current.color_outline_level04_disabled
                )
            },
            containerColor = Color.Transparent,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(
                        text = stringResource(title.toTitle()),
                        modifier = Modifier.padding(10.dp),
                        style = DDanDDanTypo.current.HeadLine6,
                        fontFamily = Pretendard,
                        color = if (pagerState.currentPage == index)
                            DDanDDanColorPalette.current.color_text_headline_secondary
                        else
                            DDanDDanColorPalette.current.color_text_body_quinary
                    ) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier.height(42.dp)
                )
            }
        }

        HorizontalPager(state = pagerState) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                    Box(
                        modifier = Modifier.nestedScroll(
                            connection = object : NestedScrollConnection {
                                override fun onPostScroll(
                                    consumed: Offset,
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    if (available.y < 0) {
                                        if (rankState.otherRanking.size + 3 < 100)
                                            onOverScroll("랭킹은 100등까지만 노출해요", R.drawable.ic_system_fill)
                                        else
                                            onOverScroll("랭킹이 아직 ${rankState.otherRanking.size + 3}등까지 밖에 없어요", R.drawable.ic_system_fill)
                                    }
                                    return super.onPostScroll(consumed, available, source)
                                }
                            }
                        )
                    ) {
                        RankListView(rankState = rankState, showToolTip = showToolTip, dismissToolTip = dismissToolTip)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopRankerView(
    modifier: Modifier = Modifier,
    criteria: RankCriteria = RankCriteria.TOTAL_CALORIES,
    rank: Int = 1,
    nickname: String? = "일이삼등입니다다다다다다",
    contents: Int? = 1024,
    mainPetType: PetTypeEnum? = PetTypeEnum.CAT,
    petLevel: Int? = 1,
    isCenter: Boolean = false
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (crownImage, contentColumn) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(contentColumn) {
                    top.linkTo(crownImage.top, margin = 17.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .background(
                    color = DDanDDanColorPalette.current.elevation_color_elevation_level01,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(152.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(mainPetType.toAnimal(petLevel)),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
            )
            DDanMarginVerticalSpacer(10)
            Text(
                text = nickname?:"",
                style = DDanDDanTypo.current.Body2,
                fontFamily = Pretendard,
                color = DDanDDanColorPalette.current.color_text_headline_teritary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(22.dp)
            )
            Text(
                text = if (criteria == RankCriteria.TOTAL_CALORIES) "${contents?:0}kcal" else if ((contents?:0) == 0) "0일" else "+${contents}일",
                style = DDanDDanTypo.current.Body1,
                fontFamily = Pretendard,
                fontWeight = FontWeight.W700,
                color = DDanDDanColorPalette.current.color_text_body_primary,
                modifier = Modifier.height(24.dp)
            )
        }

        Image(
            painter = when (rank) {
                1 -> painterResource(R.drawable.ic_crown_1)
                2 -> painterResource(R.drawable.ic_crown_2)
                else -> painterResource(R.drawable.ic_crown_3)
            },
            contentDescription = "왕관 이미지",
            modifier = Modifier
                .constrainAs(crownImage) {
                    top.linkTo(parent.top, margin = if (isCenter) 0.dp else 19.dp)
                    start.linkTo(contentColumn.start)
                    end.linkTo(contentColumn.end)
                }
                .size(32.dp)
        )
    }
}

@Preview
@Composable
fun SimpleRankerView(
    criteria: RankCriteria = RankCriteria.TOTAL_CALORIES,
    rank: Int = 4,
    nickname: String? = "일이삼사오육칠팔구십",
    contents: Int? = 987,
    mainPetType: PetTypeEnum? = PetTypeEnum.CAT,
    petLevel: Int? = 1,
    isBottomSheet: Boolean = false,
    isMyRecord: Boolean = false
) {
    Row(
        modifier = if (!isBottomSheet) Modifier.padding(top = 20.dp) else Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rank.toString(),
            fontFamily = NeoDgm,
            fontWeight = FontWeight.W400,
            color = DDanDDanColorPalette.current.color_text_headline_secondary,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            modifier =
                if (!isBottomSheet) Modifier.size(width = 24.dp, height = 20.dp)
                else Modifier
                    .wrapContentWidth()
                    .defaultMinSize(minWidth = 24.dp)
                    .height(20.dp)
        )
        DDanMarginHorizontalSpacer(12)
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(mainPetType.toColor()),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = mainPetType.toAnimal(petLevel)),
                contentDescription = "왕관 이미지",
                modifier = Modifier.size(42.dp)
            )
        }
        DDanMarginHorizontalSpacer(12)
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = nickname?:"",
            style = DDanDDanTypo.current.Body1,
            fontFamily = Pretendard,
            color = DDanDDanColorPalette.current.color_text_body_teritary
        )
        if (isMyRecord) {
            DDanMarginHorizontalSpacer(8)
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .clip(CircleShape)
                    .background(DDanDDanColorPalette.current.color_icon_level01),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isBottomSheet) "나" else "me",
                    style = DDanDDanTypo.current.Caption1,
                    fontFamily = Pretendard,
                    color = DDanDDanColorPalette.current.color_text_body_quinary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentWidth().padding(horizontal = 6.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row (
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = if (criteria == RankCriteria.TOTAL_CALORIES || (contents?:0) == 0) "${contents?:0}" else "+${contents?:0}",
                style = DDanDDanTypo.current.Body1,
                fontWeight = FontWeight.W700,
                fontFamily = Pretendard,
                color = DDanDDanColorPalette.current.color_text_body_primary
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = if (criteria == RankCriteria.TOTAL_CALORIES) "kcal" else "일",
                style = DDanDDanTypo.current.Body2,
                fontFamily = Pretendard,
                color = DDanDDanColorPalette.current.color_text_body_secondary
            )
        }
    }
}