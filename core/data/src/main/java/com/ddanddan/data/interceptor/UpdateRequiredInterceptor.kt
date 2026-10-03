package com.ddanddan.data.interceptor

import com.ddanddan.data.update.UpdateRequiredNotifier
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class UpdateRequiredInterceptor @Inject constructor(
    private val notifier: UpdateRequiredNotifier
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == CODE_UPGRADE_REQUIRED) {
            notifier.notifyUpdateRequired()
        }
        return response
    }

    companion object {
        private const val CODE_UPGRADE_REQUIRED = 426
    }
}
