package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class RankingEvent : AnalyticsEvent {

    data class ClickTab(val touchpoint: String) : RankingEvent() {
        override val title = "click_tab"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickTooltip(val touchpoint: String) : RankingEvent() {
        override val title = "click_tooltip"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickMyRanking(val touchpoint: String) : RankingEvent() {
        override val title = "click_my-ranking"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
}