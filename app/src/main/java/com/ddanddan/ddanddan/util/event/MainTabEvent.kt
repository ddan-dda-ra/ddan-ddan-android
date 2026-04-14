package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class MainTabEvent : AnalyticsEvent {
    override val parameter: Map<String, Any> = emptyMap()

    data object ClickHomeBottomNavi : MainTabEvent() {
        override val title = "click_home_bottom-navi"
    }
    data object ClickRankingBottomNavi : MainTabEvent() {
        override val title = "click_ranking_bottom-navi"
    }
    data object ClickFriendBottomNavi : MainTabEvent() {
        override val title = "click_friend_bottom-navi"
    }
    data object ClickMypageBottomNavi : MainTabEvent() {
        override val title = "click_mypage_bottom-navi"
    }
}