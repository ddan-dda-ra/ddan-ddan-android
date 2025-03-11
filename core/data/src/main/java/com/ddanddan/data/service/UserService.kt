package com.ddanddan.data.service

import com.ddanddan.model.request.RequestLogin
import com.ddanddan.model.request.RequestMainPet
import com.ddanddan.model.request.RequestSignOut
import com.ddanddan.model.request.RequestUser
import com.ddanddan.model.response.ResponseLogin
import com.ddanddan.model.response.ResponseMainPet
import com.ddanddan.model.response.ResponseUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {
    @GET("/v1/users/me")
    suspend fun getUser(): ResponseUser

    @PUT("/v1/users/me")
    suspend fun putUser(
        @Body requestUser: RequestUser
    ): ResponseUser

    @HTTP(method = "DELETE", path = "/v1/users/me", hasBody = true)
    suspend fun deleteUser(
        @Body request: RequestSignOut
    ): Response<Unit>

    @GET("/v1/users/me/main-pet")
    suspend fun getMainPet(): ResponseMainPet

    @POST("/v1/users/me/main-pet")
    suspend fun postMainPet(
        @Body requestBody: RequestMainPet
    ): ResponseMainPet

    @POST("/v1/auth/login")
    suspend fun postLogin(
        @Body token: RequestLogin
    ): ResponseLogin
}