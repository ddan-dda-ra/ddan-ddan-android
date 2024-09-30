package com.ddanddan.ddanddan.presentation.setting

import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(): ViewModel() {
    val settingItems = listOf(
        SettingItem(com.ddanddan.base.R.string.setting_title_text1, SettingIntent.EditNickname),
        SettingItem(com.ddanddan.base.R.string.setting_title_text2, SettingIntent.EditTargetCalories),
    )
    val settingItemsBottom = listOf(
        SettingItem(com.ddanddan.base.R.string.setting_title_text4, SettingIntent.AgreeToTerms),
        SettingItem(com.ddanddan.base.R.string.setting_title_text5, SettingIntent.DeleteAccount),
        SettingItem(com.ddanddan.base.R.string.setting_title_text6, SettingIntent.Logout)
    )

    data class SettingItem(val titleRes: Int, val intent: SettingIntent)
}