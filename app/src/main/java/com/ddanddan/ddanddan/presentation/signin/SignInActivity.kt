package com.ddanddan.ddanddan.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ddanddan.ddanddan.BuildConfig
import com.ddanddan.ddanddan.BuildConfig.KAKAO_APP_KEY
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivitySigninBinding
import com.ddanddan.ddanddan.presentation.MainActivity
import com.ddanddan.ddanddan.presentation.signup.SignUpActivity
import com.ddanddan.ddanddan.presentation.signup.terms.TermsActivity
import com.ddanddan.ui.base.BindingActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignInActivity
    : BindingActivity<ActivitySigninBinding>(R.layout.activity_signin){

    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observer()
        setClickListener()

        if (AuthApiClient.instance.hasToken()) {
            // 저장된 액세스 토큰 가져오기
            val accessToken = TokenManagerProvider.instance.manager.getToken()?.accessToken

            if (accessToken != null) {
                Log.d(LOG_KAKAO_LOGIN, "Access Token: $accessToken")
            } else {
                Log.d(LOG_KAKAO_LOGIN, "Access Token is null")
            }

            // 토큰의 유효성 정보 확인
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Log.e(LOG_KAKAO_LOGIN, "토큰 정보 불러오기 실패", error)
                } else if (tokenInfo != null) {
                    Log.d(LOG_KAKAO_LOGIN, "토큰 정보 확인 성공: ${tokenInfo.id}")
                    if (accessToken != null) {
                        viewModel.login(accessToken)
                    }
                }
            }
        }
    }

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error == null) {
            token?.accessToken?.let { viewModel.login(it) }
        }
    }

    private fun setClickListener() {
        with(binding) {
            btnKakao.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SignInActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@SignInActivity) { token, error ->
                        if (error != null) {
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            } else {
                                UserApiClient.instance.loginWithKakaoAccount(this@SignInActivity, callback = mCallback)
                            }
                        } else if (token != null) {
                            Toast.makeText(this@SignInActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()
                            token.accessToken.let { viewModel.login(it) }
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this@SignInActivity, callback = mCallback)
                }
            }

        }
    }

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.signInState.collect { state ->
                when (state) {
                    is SignInState.Success -> {
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        finish()
                    }
                    is SignInState.UserNotRegistered -> {
                        startActivity(Intent(this@SignInActivity, TermsActivity::class.java))
                        finish()
                    }
                    is SignInState.Failure -> {
                        Toast.makeText(this@SignInActivity, state.msg, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    companion object {
        private const val LOG_KAKAO_LOGIN = "KAKAO_LOGIN"
    }
}