package com.ddanddan.ddanddan.presentation.splash

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.repository.AuthRepository
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _autoLoginState = MutableStateFlow<AutoLoginState>(AutoLoginState.Init)
    val autoLoginState: StateFlow<AutoLoginState> = _autoLoginState

    fun isAutoLoginEnabled(): Boolean = authRepository.getAutoLogin()

    fun autoLoginWithKakao() {
        if (AuthApiClient.instance.hasToken()) {
            // 저장된 액세스 토큰 가져오기
            val accessToken = TokenManagerProvider.instance.manager.getToken()?.accessToken

            // 토큰의 유효성 정보 확인
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    _autoLoginState.value = AutoLoginState.Failure("토큰 정보 불러오기 실패")
                } else if (tokenInfo != null) {
                    if (accessToken != null) {
                        _autoLoginState.value = AutoLoginState.Success(accessToken)
                    } else {
                        _autoLoginState.value = AutoLoginState.Failure("잘못된 토큰 정보")
                    }
                } else {
                    _autoLoginState.value = AutoLoginState.Failure("토큰 정보 불러오기 실패")
                }
            }
        } else {
            _autoLoginState.value = AutoLoginState.Failure("토큰 없음")
        }
    }

    companion object {
        private const val LOG_KAKAO_LOGIN = "KAKAO_LOGIN"
    }
}

sealed interface AutoLoginState {
    object Init : AutoLoginState
    data class Success(val token: String) : AutoLoginState
    data class Failure(val msg: String) : AutoLoginState
}