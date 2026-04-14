package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class LevelEvent : AnalyticsEvent {

    data class ClickCTA(val touchpoint: String) : LevelEvent() {
        override val title = "click_cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
}