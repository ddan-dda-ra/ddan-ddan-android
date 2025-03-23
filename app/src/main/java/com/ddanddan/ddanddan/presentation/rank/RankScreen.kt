package com.ddanddan.ddanddan.presentation.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.util.toColor
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.Pretendard
import com.ddanddan.ui.compose.component.DDanMarginHorizontalSpacer
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DDanSnackBar
import com.ddanddan.ui.compose.component.DdanScaffold
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun RankRoute(
    onNavigateHome: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    RankScreen()
}

@Preview
@Composable
fun RankScreen(
    rankState: RankState = RankState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navigatePopUp: () -> Unit = {}
) {
    DdanScaffold(
        topbarText = stringResource(id = R.string.rank_topbar_title),
        snackbarHost = {
            DDanSnackBar(snackBarHostState = snackBarHostState)
        },
        onClick = {
            navigatePopUp()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

        }
    }
}

private fun getPetImage(petTypeEnum: PetTypeEnum, petLevel: Int): Int {
    return when (petTypeEnum) {
        PetTypeEnum.CAT -> when (petLevel) {
            1 -> R.drawable.ic_cat_level1
            2 -> R.drawable.ic_cat_level2
            3 -> R.drawable.ic_cat_level3
            4 -> R.drawable.ic_cat_level4
            else -> R.drawable.ic_cat_level5
        }
        PetTypeEnum.DOG -> when (petLevel) {
            1 -> R.drawable.ic_dog_level1
            2 -> R.drawable.ic_dog_level2
            3 -> R.drawable.ic_dog_level3
            4 -> R.drawable.ic_dog_level4
            else -> R.drawable.ic_dog_level5
        }
        PetTypeEnum.PENGUIN -> when (petLevel) {
            1 -> R.drawable.ic_penguin_level1
            2 -> R.drawable.ic_penguin_level2
            3 -> R.drawable.ic_penguin_level3
            4 -> R.drawable.ic_penguin_level4
            else -> R.drawable.ic_penguin_level5
        }
        PetTypeEnum.HAMSTER -> when (petLevel) {
            1 -> R.drawable.ic_hamster_level1
            2 -> R.drawable.ic_hamster_level2
            3 -> R.drawable.ic_hamster_level3
            4 -> R.drawable.ic_hamster_level4
            else -> R.drawable.ic_hamster_level5
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
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // 상단 모서리만 둥글게
            .background(DDanDDanColorPalette.current.elevation_color_elevation_level02)
    ) {
        SimpleRankerView(
            criteria = rankState.criteria,
//            rank, nickname, contents, mainPetType, petLevel,
            isMyRecord = true
        )
    }
}

@Preview
@Composable
private fun RankListView(
    criteria: RankCriteria = RankCriteria.TOTAL_CALORIES,
    onSystemIconClick: () -> Unit = {}
) {
    val today = LocalDate.now()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        item {
            DDanMarginVerticalSpacer(24)
            Text(
                text = "${today.year}년 ${today.monthValue}월 기준",
                style = DDanDDanTypo.current.Body2,
                fontFamily = Pretendard,
                color = DDanDDanColorPalette.current.color_text_headline_teritary,
            )
            DDanMarginVerticalSpacer(4)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(criteria.toSubTitle()),
                    style = DDanDDanTypo.current.NeoDgm24,
                    fontFamily = NeoDgm,
                    color = DDanDDanColorPalette.current.color_text_headline_secondary,
                )
                DDanMarginHorizontalSpacer(4)
                Image(
                    painter = painterResource(R.drawable.ic_system_line),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 20.dp, height = 24.dp)
                        .padding(bottom = 4.dp)
                        .clickable {
                            onSystemIconClick()
                        }
                )
            }
            DDanMarginVerticalSpacer(32)
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TopRankerView(
                    modifier = Modifier.weight(1f),
                    criteria = criteria,
                    rank = 2
                )
                DDanMarginHorizontalSpacer(13)
                TopRankerView(
                    modifier = Modifier.weight(1f),
                    criteria = criteria,
                    rank = 1
                )
                DDanMarginHorizontalSpacer(13)
                TopRankerView(
                    modifier = Modifier.weight(1f),
                    criteria = criteria,
                    rank = 3
                )
            }
            DDanMarginVerticalSpacer(17)
        }

        // 4~100등
        items(97) { index ->
            SimpleRankerView(rank = index + 4)
        }
    }
}

@Preview
@Composable
private fun RankTapLayout(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState { 2 }
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(RankCriteria.TOTAL_CALORIES, RankCriteria.TOTAL_SUCCEEDED_DAYS)

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
            contentColor = DDanDDanColorPalette.current.color_text_headline_secondary
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

        HorizontalPager(
            state = pagerState
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 페이지별 컨텐츠
                when (page) {
                    0 -> RankListView(RankCriteria.TOTAL_CALORIES)
                    1 -> RankListView(RankCriteria.TOTAL_SUCCEEDED_DAYS)
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
    nickname: String = "일이삼등입니다다다다다다",
    contents: Int = 1024,
    mainPetType: PetTypeEnum = PetTypeEnum.CAT,
    petLevel: Int = 1
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
                painter = painterResource(getPetImage(petTypeEnum = mainPetType, petLevel = petLevel)),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
            )
            DDanMarginVerticalSpacer(10)
            Text(
                text = nickname,
                style = DDanDDanTypo.current.Body2,
                color = DDanDDanColorPalette.current.color_text_headline_teritary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = if (criteria == RankCriteria.TOTAL_CALORIES) "${contents}kcal" else "+${contents}일",
                style = DDanDDanTypo.current.Body1,
                color = DDanDDanColorPalette.current.color_text_body_primary
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
                    top.linkTo(parent.top, margin = if (rank == 1) 0.dp else 19.dp)
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
    nickname: String = "일이삼사오육칠팔구십",
    contents: Int = 987,
    mainPetType: PetTypeEnum = PetTypeEnum.CAT,
    petLevel: Int = 1,
    isMyRecord: Boolean = false
) {
    Row(
        modifier = if (!isMyRecord) Modifier.padding(top = 20.dp) else Modifier.padding(20.dp),
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
                if (!isMyRecord) Modifier.size(width = 24.dp, height = 20.dp)
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
                painter = painterResource(id = getPetImage(mainPetType, petLevel)),
                contentDescription = "왕관 이미지",
                modifier = Modifier.size(42.dp)
            )
        }
        DDanMarginHorizontalSpacer(12)
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = nickname,
            style = DDanDDanTypo.current.Body1,
            fontFamily = Pretendard,
            color = DDanDDanColorPalette.current.color_text_body_teritary
        )
        if (isMyRecord) {
            DDanMarginHorizontalSpacer(8)
            Box(
                modifier = Modifier
                    .size(width = 22.dp, height = 20.dp)
                    .clip(CircleShape)
                    .background(DDanDDanColorPalette.current.color_icon_level01),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "나",
                    style = DDanDDanTypo.current.Caption1,
                    fontFamily = Pretendard,
                    color = DDanDDanColorPalette.current.color_text_body_quinary,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row (
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = if (criteria == RankCriteria.TOTAL_CALORIES) contents.toString() else "+$contents",
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