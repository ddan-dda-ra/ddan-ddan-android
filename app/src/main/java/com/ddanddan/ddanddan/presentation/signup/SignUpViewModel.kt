package com.ddanddan.ddanddan.presentation.signup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
) : ViewModel() {

    private val _signUpProgress = MutableStateFlow<SignUpProgress>(SignUpProgress.Name)
    val signUpProgress: StateFlow<SignUpProgress> = _signUpProgress.asStateFlow()

    fun moveSignUpProgress(progress: SignUpProgress) {
        _signUpProgress.value = progress
    }

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    fun updateUserName(userName: String) {
        _userName.value = userName
    }

    private val _goalCalories = MutableStateFlow<Int>(300)
    val goalCalories: StateFlow<Int> = _goalCalories.asStateFlow()

    fun plusGoalCalories(): Boolean {
        if (_goalCalories.value >= 1000) return false
        _goalCalories.value += 100
        return true
    }

    fun minusGoalCalories(): Boolean {
        if (_goalCalories.value <= 100) return false
        _goalCalories.value -= 100
        return true
    }

    private val _eggColor = MutableStateFlow<Int?>(null)
    val eggColor: StateFlow<Int?> = _eggColor.asStateFlow()

    fun setEggColor(color: Int) {
        _eggColor.value = color
    }
}

enum class SignUpProgress {
    Name, Goal, Egg
}