package com.ddanddan.data.interceptor

import com.ddanddan.domain.ddanddanDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class PetCatalogInterceptor @Inject constructor(
    private val dataStore: ddanddanDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 요청에 카탈로그 버전 헤더 추가
        val request = chain.request().newBuilder()
            .addHeader("X-Pet-Catalog-Version", dataStore.petCatalogRevision ?: "")
            .build()

        val response = chain.proceed(request)

        // 응답에서 다운로드 필요 여부 확인
        val needsDownload = response.header("X-Pet-Catalog-Download-Required")?.toBoolean() ?: false
        if (needsDownload) {
            dataStore.isPetCatalogUpdateRequired = true
        }

        return response
    }
}