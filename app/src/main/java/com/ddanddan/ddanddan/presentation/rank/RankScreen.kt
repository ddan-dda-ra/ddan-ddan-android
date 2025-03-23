package com.ddanddan.ddanddan.presentation.rank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.util.toColor
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.Pretendard
import com.ddanddan.ui.compose.component.DDanMarginHorizontalSpacer
import com.ddanddan.ui.compose.component.DDanSnackBar
import com.ddanddan.ui.compose.component.DdanScaffold

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