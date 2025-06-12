package com.ddanddan.ddanddan.presentation.splash

sealed class SplashSideEffect {
    object NetworkError: SplashSideEffect()
    object NavigateOnboarding: SplashSideEffect()
    object NavigateSignIn: SplashSideEffect()
    object NavigateGrantNotPermission: SplashSideEffect()
    object NavigateHome: SplashSideEffect()
}
