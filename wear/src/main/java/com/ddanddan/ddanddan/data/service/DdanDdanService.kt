package com.ddanddan.ddanddan.data.service

import com.ddanddan.ddanddan.data.model.request.RequestDailyCalorie
import com.ddanddan.ddanddan.data.model.response.ResponseDailyCalorie
import com.ddanddan.ddanddan.data.model.response.ResponseMainPet
import com.ddanddan.ddanddan.data.model.response.ResponseUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import javax.inject.Singleton

interface DdanDdanService {
    /**
     * 내 정보 조회
     */
    @GET("/v1/user/me")
    suspend fun getUser(
    ): ResponseUser

    /**
     * 메인 펫 조회
     */
    @GET("/v1/user/me/main-pet")
    suspend fun getMainPet(
    ): ResponseMainPet

    /**
     * 일일 칼로리 갱신
     */
    @PATCH("/v1/user/me/daily-calorie")
    suspend fun patchDailyCalorie(
        @Body requestBody: RequestDailyCalorie
    ): ResponseDailyCalorie
}