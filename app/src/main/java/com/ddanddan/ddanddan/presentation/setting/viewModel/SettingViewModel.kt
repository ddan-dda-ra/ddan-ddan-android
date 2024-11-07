package com.ddanddan.ddanddan.presentation.setting.viewModel

import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.presentation.setting.SettingIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _nickName = MutableStateFlow("")
    val nickName = _nickName.asStateFlow()

    fun updateNickName(newName: String) {
        _nickName.value = newName
    }

    private val _target = MutableStateFlow(100)
    val target = _target.asStateFlow()

    fun incrementTarget() {
        if (_target.value < 1000) {
            _target.value += 100
        }
    }

    fun decrementTarget() {
        if (_target.value > 100) {
            _target.value -= 100
        }
    }



    data class SettingItem(val titleRes: Int, val intent: SettingIntent)
}