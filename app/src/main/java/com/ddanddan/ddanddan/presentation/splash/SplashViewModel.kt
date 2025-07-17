package com.ddanddan.ddanddan.presentation.splash

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.GetAutoLoginUseCase
import com.ddanddan.domain.usecase.GetFirstInstallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAutoLoginUseCase: GetAutoLoginUseCase,
    private val getFirstInstallUseCase: GetFirstInstallUseCase
) : ContainerHost<Unit, SplashSideEffect>, ViewModel() {
    override val container: Container<Unit, SplashSideEffect>
        = container(Unit)

    private fun isAutoLoginEnabled() = intent {
        val isEnabled = getAutoLoginUseCase()
        if (isEnabled) postSideEffect(SplashSideEffect.NavigateHome)
        else postSideEffect(SplashSideEffect.NavigateSignIn)
    }

    fun isFirstAfterInstall() = intent {
        val isFirst = getFirstInstallUseCase()
        if (isFirst) postSideEffect(SplashSideEffect.NavigateOnboarding)
        else isAutoLoginEnabled()
    }

    fun disconnectedNetwork() = intent {
        postSideEffect(SplashSideEffect.NetworkError)
    }

    fun grantNotPermission() = intent {
        postSideEffect(SplashSideEffect.NavigateGrantNotPermission)
    }
}