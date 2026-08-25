package com.ddanddan.data.interceptor

import com.ddanddan.domain.ddanddanDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class PetCatalogInterceptor @Inject constructor(
    private val dataStore: ddanddanDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 요청에 카탈로그 버전 헤더 설정 (Retrofit이 이미 같은 이름의 헤더를 추가했다면 대체)
        val request = chain.request().newBuilder()
            .header("X-Pet-Catalog-Version", dataStore.petCatalogRevision ?: NO_CACHE_REVISION)
            .build()

        val response = chain.proceed(request)

        // 응답에서 다운로드 필요 여부 확인
        val needsDownload = response.header("X-Pet-Catalog-Download-Required")?.toBoolean() ?: false
        if (needsDownload) {
            dataStore.isPetCatalogUpdateRequired = true
        }

        return response
    }

    companion object {
        // 캐시된 리비전이 없을 때 보낼 값. 서버가 date-time 포맷 + required로 검증하므로
        // 항상 "가장 오래된" 값으로 간주되도록 epoch를 사용해 무조건 갱신이 필요하다고 응답받는다.
        private const val NO_CACHE_REVISION = "1970-01-01T00:00:00Z"
    }
}