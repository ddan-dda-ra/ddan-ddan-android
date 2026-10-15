package com.ddanddan.ddanddan.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ddanddan.ddanddan.util.lottieUrlFor
import com.ddanddan.ddanddan.util.toLottie
import com.ddanddan.domain.entity.PetCatalog

/**
 * 카탈로그에 해당 펫 타입/레벨의 lottie URL이 있으면 그걸 재생하고,
 * 없으면(카탈로그 미로딩·새 타입 등) 번들에 있는 로컬 애니메이션으로 대체한다.
 */
@Composable
fun PetLottie(
    petType: String?,
    level: Int?,
    catalog: PetCatalog?,
    modifier: Modifier = Modifier,
    isPlayEat: Boolean = false
) {
    val lottieUrl = catalog.lottieUrlFor(petType, level, isPlayEat)
    val compositionSpec = lottieUrl?.let { LottieCompositionSpec.Url(it) }
        ?: LottieCompositionSpec.RawRes(petType.toLottie(level, isPlayEat))
    val composition by rememberLottieComposition(compositionSpec)

    LottieAnimation(
        composition = composition,
        modifier = modifier,
        iterations = LottieConstants.IterateForever
    )
}
