package com.ddanddan.ddanddan.util.provider

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class KakaoProvider @Inject constructor(
    @ActivityContext private val context: Context
) {
    private fun isKakaoTalkLoginAvailable(): Boolean = UserApiClient.instance.isKakaoTalkLoginAvailable(context)

    private fun loginWithKakaoTalk(callback: (OAuthToken?, Throwable?) -> Unit) {
        UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
    }

    private fun loginWithKakaoAccount(callback: (OAuthToken?, Throwable?) -> Unit) {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }

    fun loginWithKakao(callback: (OAuthToken?, Throwable?) -> Unit) {
        if (isKakaoTalkLoginAvailable()) {
            loginWithKakaoTalk { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) return@loginWithKakaoTalk
                    else loginWithKakaoAccount(callback)
                } else if (token != null) {
                    callback(token, null)
                }
            }
        } else {
            loginWithKakaoAccount(callback)
        }
    }
}