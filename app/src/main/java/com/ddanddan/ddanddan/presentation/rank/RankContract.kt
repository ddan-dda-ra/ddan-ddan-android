package com.ddanddan.ddanddan.presentation.rank

import com.ddanddan.ddanddan.R
import javax.annotation.concurrent.Immutable

@Immutable
data class RankState(
    val criteria: RankCriteria = RankCriteria.TOTAL_CALORIES
)

enum class RankCriteria { TOTAL_CALORIES, TOTAL_SUCCEEDED_DAYS }

fun RankCriteria.toTitle(): Int = when (this) {
    RankCriteria.TOTAL_CALORIES -> R.string.rank_tablayout_calorie_title
    RankCriteria.TOTAL_SUCCEEDED_DAYS -> R.string.rank_tablayout_target_title
}

fun RankCriteria.toSubTitle(): Int = when (this) {
    RankCriteria.TOTAL_CALORIES -> R.string.rank_tablayout_calorie_subtitle
    RankCriteria.TOTAL_SUCCEEDED_DAYS -> R.string.rank_tablayout_target_subtitle
}