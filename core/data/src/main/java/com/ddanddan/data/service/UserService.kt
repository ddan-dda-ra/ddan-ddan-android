package com.ddanddan.data.service

import com.ddanddan.model.request.RequestLogin
import com.ddanddan.model.response.ResponseLogin
import com.ddanddan.model.response.ResponseMainPet
import com.ddanddan.model.response.ResponseUser
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @GET("/v1/user/me")
    suspend fun getUser(): ResponseUser

    @GET("/v1/user/me/main-pet")
    suspend fun getMainPet(): ResponseMainPet

    @POST("/v1/auth/login")
    suspend fun login(
        @Body token: RequestLogin
    ): ResponseLogin
}