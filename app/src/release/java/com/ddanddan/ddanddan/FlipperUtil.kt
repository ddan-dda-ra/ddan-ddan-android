package com.ddanddan.ddanddan

import android.app.Application
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object FlipperUtil {
    val flipperInterceptor: Interceptor
        get() = Interceptor { chain ->
            chain.proceed(chain.request())
        }

    fun init(app: Application) {}

    fun addFlipperNetworkPlugin(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
    }
}
