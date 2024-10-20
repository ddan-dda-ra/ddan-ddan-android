package com.ddanddan.data.service

import com.ddanddan.model.request.RequestLogin
import com.ddanddan.model.request.RequestMainPet
import com.ddanddan.model.request.RequestUser
import com.ddanddan.model.response.ResponseLogin
import com.ddanddan.model.response.ResponseMainPet
import com.ddanddan.model.response.ResponseUser
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {
    @GET("/v1/user/me")
    suspend fun getUser(): ResponseUser

    @PUT("/v1/user/me")
    suspend fun putUser(
        @Body requestUser: RequestUser
    ): ResponseUser

    @GET("/v1/user/me/main-pet")
    suspend fun getMainPet(): ResponseMainPet

    @POST("/v1/user/me/main-pet")
    suspend fun postMainPet(
        @Body requestBody: RequestMainPet
    ): ResponseMainPet

    @POST("/v1/auth/login")
    suspend fun login(
        @Body token: RequestLogin
    ): ResponseLogin
}