package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class OnboardingEvent : AnalyticsEvent {

    data class ClickCTA(val touchpoint: String) : OnboardingEvent() {
        override val title = "click_CTA"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickDisagreeDialogBtn(val touchpoint: String) : OnboardingEvent() {
        override val title = "click_disagree_dialog-btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickAgreeDialogCTA(val touchpoint: String) : OnboardingEvent() {
        override val title = "click_agree_dialog-cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
}