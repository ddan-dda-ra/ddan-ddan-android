package com.ddanddan.ddanddan.data.repository

import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ddanddan.ddanddan.BuildConfig
import com.ddanddan.ddanddan.data.model.response.ResponseAuthToken
import com.ddanddan.ddanddan.util.PreferencesKeys
import com.ddanddan.ddanddan.util.WatchToPhoneUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val json: Json,
    private val dataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            dataStore.data
                .map { preferences -> preferences[PreferencesKeys.ACCESS_TOKEN_KEY] }
                .firstOrNull()
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", accessToken ?: "")
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            // 토큰 갱신 시도
            val newAccessToken = refreshToken(chain) ?: return response

            // 갱신된 토큰으로 다시 요청
            val newRequest = request.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", newAccessToken)
                .build()

            return chain.proceed(newRequest)
        }

        return response
    }

    /**
     * 토큰을 갱신하고 DataStore에 저장
     */
    private fun refreshToken(chain: Interceptor.Chain): String? {
        return try {
            val refreshToken = runBlocking {
                dataStore.data
                    .map { preferences -> preferences[PreferencesKeys.REFRESH_TOKEN_KEY] }
                    .firstOrNull()
            }

            if (refreshToken.isNullOrEmpty()) {
                Timber.e("Refresh token이 비어 있음.")
                return null
            }

            // 폰의 AuthInterceptor와 같은 방식: 리프레시 토큰("Bearer " 제외)을 JSON 바디로 보낸다
            val refreshTokenJson = """
                {
                    "refreshToken": "${refreshToken.removePrefix("Bearer ")}"
                }
            """.trimIndent()

            val refreshTokenRequest = chain.request().newBuilder()
                .url("${BuildConfig.BASE_URL}/v1/auth/reissue")
                .post(refreshTokenJson.toRequestBody(MEDIA_TYPE_JSON))
                .build()

            val refreshTokenResponse = chain.proceed(refreshTokenRequest)

            if (refreshTokenResponse.isSuccessful) {
                val responseToken = json.decodeFromString(
                    refreshTokenResponse.body?.string().orEmpty()
                ) as ResponseAuthToken
                refreshTokenResponse.close()

                val newAccessToken = "Bearer ${responseToken.accessToken}"
                val newRefreshToken = "Bearer ${responseToken.refreshToken}"

                // DataStore에 갱신된 토큰 저장
                runBlocking {
                    dataStore.edit { preferences ->
                        preferences[PreferencesKeys.ACCESS_TOKEN_KEY] = newAccessToken
                        preferences[PreferencesKeys.REFRESH_TOKEN_KEY] = newRefreshToken
                    }
                }

                Timber.d("토큰 갱신 성공")
                // 저장된 값과 같은 "Bearer ..." 형태로 재요청해야 한다
                return newAccessToken
            } else {
                Timber.e("토큰 갱신 실패: HTTP ${refreshTokenResponse.code}")
                handleTokenExpired(context)
                null
            }
        } catch (e: Exception) {
            Timber.e(e, "토큰 갱신 중 오류 발생")
            null
        }
    }

    private fun handleTokenExpired(context: Context) {
        WatchToPhoneUtils.checkAndSendTokenExpired(context)

        val intent = Intent("com.ddanddan.ddanddan.TOKEN_EXPIRED") //todo - 유틸 또는 상수로 분리
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    companion object {
        private val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    }
}
