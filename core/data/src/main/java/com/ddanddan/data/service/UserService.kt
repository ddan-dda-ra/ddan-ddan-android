package com.ddanddan.data.service

import com.ddanddan.model.response.ResponseMainPet
import com.ddanddan.model.response.ResponsePet
import com.ddanddan.model.response.ResponseUser
import retrofit2.http.GET

interface UserService {
    @GET("/v1/user/me")
    suspend fun getUser(): ResponseUser

    @GET("/v1/user/me/main-pet")
    suspend fun getMainPet(): ResponseMainPet
}