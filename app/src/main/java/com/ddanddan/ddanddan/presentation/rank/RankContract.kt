package com.ddanddan.ddanddan.presentation.rank

import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.home.collect.PetCollectionSideEffect
import com.ddanddan.domain.entity.Rank
import javax.annotation.concurrent.Immutable

@Immutable
data class RankState(
    val criteria: RankCriteria = RankCriteria.TOTAL_CALORIES,
    val myRank: Rank? = null,
    val otherRanking: List<Rank> = listOf(),
    val goldRank: Rank? = null,
    val silverRank: Rank? = null,
    val bronzeRank: Rank? = null,
    val showToolTip: Boolean = false,
    val isPatchAttempted: Boolean = false
)

sealed class RankSideEffect {
    object NavigatePopUp : RankSideEffect()
    data class NetworkError(val msg: String): RankSideEffect()
    data class SnackBarMsg(val msg: String, val icon: Int) : RankSideEffect()
    object UserDataEmpty: RankSideEffect()
}

enum class RankCriteria { TOTAL_CALORIES, TOTAL_SUCCEEDED_DAYS }

fun RankCriteria.toTitle(): Int = when (this) {
    RankCriteria.TOTAL_CALORIES -> R.string.rank_tablayout_calorie_title
    RankCriteria.TOTAL_SUCCEEDED_DAYS -> R.string.rank_tablayout_target_title
}

fun RankCriteria.toSubTitle(): Int = when (this) {
    RankCriteria.TOTAL_CALORIES -> R.string.rank_tablayout_calorie_subtitle
    RankCriteria.TOTAL_SUCCEEDED_DAYS -> R.string.rank_tablayout_target_subtitle
}