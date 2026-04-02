package com.ddanddan.ddanddan.util.event

import com.ddanddan.ddanddan.util.AnalyticsEvent

sealed class FriendsEvent : AnalyticsEvent {

    data object ClickFriendListGroup : FriendsEvent() {
        override val title = "click_friend_list_group"
        override val parameter = emptyMap<String, Any>()
    }

    data class ClickAddFriendBtn(val state: String) : FriendsEvent() {
        override val title = "click_add_friend_btn"
        override val parameter = mapOf("state" to state)
    }

    data object ClickDeleteFriendBtn : FriendsEvent() {
        override val title = "click_delete_friend_btn"
        override val parameter = emptyMap<String, Any>()
    }

    data object ClickCheerUpBtn : FriendsEvent() {
        override val title = "click_cheer_up_btn"
        override val parameter = emptyMap<String, Any>()
    }

    data class ClickCloseBtn(val touchpoint: String) : FriendsEvent() {
        override val title = "click_close_btn"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }

    data class ClickCancelDialogCTA(val touchpoint: String) : FriendsEvent() {
        override val title = "click_cancel_dialog_cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }

    data class ClickDeleteDialogCTA(val touchpoint: String) : FriendsEvent() {
        override val title = "click_delete_dialog_cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }

    data class ClickAddFriendDialogCTA(val touchpoint: String) : FriendsEvent() {
        override val title = "click_add_friend_dialog_cta"
        override val parameter = mapOf("touchpoint" to touchpoint)
    }
}