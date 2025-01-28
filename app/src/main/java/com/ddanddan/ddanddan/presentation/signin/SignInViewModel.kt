package com.ddanddan.ddanddan.presentation.signin

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.PostLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
) : ContainerHost<SignInState, SignInSideEffect>, ViewModel() {

    override val container: Container<SignInState, SignInSideEffect>
        = container<SignInState, SignInSideEffect>(SignInState())

    private fun navigateSignUp() = intent {
        postSideEffect(SignInSideEffect.UserNotRegistered)
    }

    private fun navigateHome(accessToken: String, refreshToken: String) = intent {
        postSideEffect(SignInSideEffect.SuccessLogin(accessToken, refreshToken))
    }

    fun dismissProgressBar() = intent {
        reduce { state.copy(isShowProgressBar = false)}
    }

    fun showProgressBar() = intent {
        reduce { state.copy(isShowProgressBar = true) }
    }

    fun loginWithToken(token: String) = intent {
        postLoginUseCase(token)
            .onSuccess {
                if (it.isOnboardingComplete) navigateHome(it.accessToken, it.refreshToken)
                else navigateSignUp()
            }.onFailure {
                postSideEffect(SignInSideEffect.NetworkError("로그인에 실패했습니다."))
            }
    }
}