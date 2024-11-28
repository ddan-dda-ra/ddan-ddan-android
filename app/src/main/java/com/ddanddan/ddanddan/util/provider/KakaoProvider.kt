package com.ddanddan.ddanddan.util.provider

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class KakaoProvider @Inject constructor(
    @ActivityContext private val context: Context
) {
    fun isKakaoTalkLoginAvailable(): Boolean = UserApiClient.instance.isKakaoTalkLoginAvailable(context)

    fun loginWithKakaoTalk(callback: (OAuthToken?, Throwable?) -> Unit) {
        UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
    }

    fun loginWithKakaoAccount(callback: (OAuthToken?, Throwable?) -> Unit) {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}