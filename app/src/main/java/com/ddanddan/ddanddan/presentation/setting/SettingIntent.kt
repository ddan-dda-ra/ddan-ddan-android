package com.ddanddan.ddanddan.presentation.setting

sealed class SettingIntent {
    object EditNickname: SettingIntent()
    object EditTargetCalories : SettingIntent()
    object TogglePushNotifications : SettingIntent()
    object AgreeToTerms : SettingIntent()
    object DeleteAccount : SettingIntent()
    object Logout : SettingIntent()
}
