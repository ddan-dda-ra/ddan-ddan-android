package com.ddanddan.data.service

import com.ddanddan.model.request.RequestLogin
import com.ddanddan.model.response.ResponseLogin
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/v1/auth/login")
    suspend fun login(
        @Body token: RequestLogin
    ): ResponseLogin
}