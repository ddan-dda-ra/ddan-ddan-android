package com.ddanddan.ddanddan.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddanddan.ddanddan.util.provider.KakaoProvider
import com.ddanddan.domain.repository.AuthRepository
import com.ddanddan.domain.repository.UserRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
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
}

sealed interface SignInState {
    object Init : SignInState
    object Success : SignInState
    object UserNotRegistered : SignInState
    data class Failure(val msg: String) : SignInState
}