package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class LevelEvent : AnalyticsEvent {

    data object ClickCTA : LevelEvent() {
        override val title = "click_cta"
        override val parameter = emptyMap<String, Any>()
    }
}