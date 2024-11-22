package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.runtime.Immutable

@Immutable
data class SettingState(
    val settingItems: List<Int> = listOf(
        com.ddanddan.base.R.string.setting_title_text1,
        com.ddanddan.base.R.string.setting_title_text2
    ),
    val settingItemsBottom: List<Int> = listOf(
        com.ddanddan.base.R.string.setting_title_text4,
        com.ddanddan.base.R.string.setting_title_text5,
        com.ddanddan.base.R.string.setting_title_text6
    )
)

sealed class SettingSideEffect {
    object EditNickname: SettingSideEffect()
    object EditTargetCalories : SettingSideEffect()
    object TogglePushNotifications : SettingSideEffect()
    object AgreeToTerms : SettingSideEffect()
    object DeleteAccount : SettingSideEffect()
    object Logout : SettingSideEffect()
}
