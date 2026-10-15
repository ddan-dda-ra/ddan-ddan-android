package com.ddanddan.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AppVersionInterceptor(
    private val versionName: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header(HEADER_APP_VERSION, versionName)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_APP_VERSION = "X-Android-Version"
    }
}
