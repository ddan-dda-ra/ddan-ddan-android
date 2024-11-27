package com.ddanddan.ddanddan.presentation.signin

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddanddan.domain.repository.AuthRepository
import com.ddanddan.domain.repository.UserRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Init)
    val signInState: StateFlow<SignInState> = _signInState

    fun login(token: String) {
        viewModelScope.launch {
            repository.login(token)
                .onSuccess {
                    if (it) authRepository.enableAutoLogin()
                    _signInState.value =
                        if (it) SignInState.Success else SignInState.UserNotRegistered
                }
                .onFailure {
                    _signInState.value = SignInState.Failure("회원 정보 로딩 실패")
                }
        }
    }

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error == null) {
            token?.accessToken?.let { login(it) }
        }
    }

    fun loginWithKakao(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    } else {
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = mCallback)
                    }
                } else if (token != null) {
                    Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    token.accessToken.let { login(it) }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = mCallback)
        }
    }
}

sealed interface SignInState {
    object Init : SignInState
    object Success : SignInState
    object UserNotRegistered : SignInState
    data class Failure(val msg: String) : SignInState
}