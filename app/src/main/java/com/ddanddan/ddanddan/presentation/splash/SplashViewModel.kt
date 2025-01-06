package com.ddanddan.ddanddan.presentation.splash

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun isAutoLoginEnabled(): Boolean = authRepository.getAutoLogin()
    fun isFirstAfterInstall(): Boolean = authRepository.isFirstAfterInstall()

    companion object {
    }
}