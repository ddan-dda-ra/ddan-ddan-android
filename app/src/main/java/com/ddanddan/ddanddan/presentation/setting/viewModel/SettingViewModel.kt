package com.ddanddan.ddanddan.presentation.setting.viewModel

import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.presentation.setting.SettingSideEffect
import com.ddanddan.ddanddan.presentation.setting.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() :
    ContainerHost<SettingState, SettingSideEffect>, ViewModel() {
    override val container =
        container<SettingState, SettingSideEffect>(SettingState())

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

    private val _selectedReasons = MutableStateFlow<List<String>>(emptyList())
    val selectedReasons = _selectedReasons.asStateFlow()

    fun updateSelection(reason: String) {
        _selectedReasons.value = if (_selectedReasons.value.contains(reason)) {
            _selectedReasons.value - reason
        } else {
            _selectedReasons.value + reason
        }
    }

    fun onSettingItemClick(titleId: Int) = intent {
        val sideEffect = when (titleId) {
            com.ddanddan.base.R.string.setting_title_text1 -> SettingSideEffect.EditNickname
            com.ddanddan.base.R.string.setting_title_text2 -> SettingSideEffect.EditTargetCalories
            com.ddanddan.base.R.string.setting_title_text4 -> SettingSideEffect.AgreeToTerms
            com.ddanddan.base.R.string.setting_title_text5 -> SettingSideEffect.DeleteAccount
            else -> SettingSideEffect.Logout
        }
        postSideEffect(sideEffect)
    }
}