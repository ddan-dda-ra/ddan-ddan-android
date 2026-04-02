package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class LoginEvent : AnalyticsEvent {

    data class ClickKakaoBtn(val touchpoint: String) : LoginEvent() {
        override val title = "click_kakao_btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
    data class ClickAppleBtn(val touchpoint: String) : LoginEvent() {
        override val title = "click_apple_btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
}