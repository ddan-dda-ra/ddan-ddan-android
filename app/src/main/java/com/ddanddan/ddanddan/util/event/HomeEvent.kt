package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class HomeEvent : AnalyticsEvent {
    override val parameter: Map<String, Any> = emptyMap()

    data object ClickFeedBtn : HomeEvent() {
        override val title = "click_feed_btn"
    }
    data object ClickPlayBtn : HomeEvent() {
        override val title = "click_play_btn"
    }
    data object ClickPet : HomeEvent() {
        override val title = "click_pet"
    }
    data object ClickNewPetBtn : HomeEvent() {
        override val title = "click_new-pet_btn"
    }
    data class ClickCancelBtn(val path: String) : HomeEvent() {
        override val title = "click_cancel_btn"
        override val parameter = mapOf("path" to path)
    }
    data class ClickBtn(val path: String) : HomeEvent() {
        override val title = "click_btn"
        override val parameter = mapOf("path" to path)
    }
}