package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class MyPageEvent : AnalyticsEvent {

    data class ClickBackBtn(val touchpoint: String) : MyPageEvent() {
        override val title = "click-back-btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickPetBox(val touchpoint: String) : MyPageEvent() {
        override val title = "click-petbox"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickChangeName(val touchpoint: String) : MyPageEvent() {
        override val title = "click-change-name"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickChangeGoal(val touchpoint: String) : MyPageEvent() {
        override val title = "click-change-goal"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickPushAlarm(val touchpoint: String) : MyPageEvent() {
        override val title = "click-push-alarm"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickTerms(val touchpoint: String) : MyPageEvent() {
        override val title = "click-terms"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickDeleteAccount(val touchpoint: String) : MyPageEvent() {
        override val title = "click-delete-account"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickSavedCTA(val touchpoint: String) : MyPageEvent() {
        override val title = "click-saved-cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickMinusBtn(val touchpoint: String) : MyPageEvent() {
        override val title = "click-minus-btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickServiceTermsBtn(val touchpoint: String) : MyPageEvent() {
        override val title = "click-service-terms-btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickPrivacyTermsBtn(val touchpoint: String) : MyPageEvent() {
        override val title = "click-privacy-terms-btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickCheckboxReason(val touchpoint: String) : MyPageEvent() {
        override val title = "click-checkbox-reason"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickCheckboxRecheck(val touchpoint: String) : MyPageEvent() {
        override val title = "click-checkbox-recheck"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickCTABtn(val touchpoint: String) : MyPageEvent() {
        override val title = "click-CTA-btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
}