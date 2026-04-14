package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class SignUpEvent : AnalyticsEvent {

    data class ClickStartCTA(val touchpoint: String) : SignUpEvent() {
        override val title = "click_start_cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }

    data class ClickNextCTA(val touchpoint: String) : SignUpEvent() {
        override val title = "click_next_cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }

    data class ClickCTA(val touchpoint: String) : SignUpEvent() {
        override val title = "click_cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
}