package com.ddanddan.data.service

import com.ddanddan.model.response.ResponsePets
import com.ddanddan.model.response.ResponseUserPet
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PetService {
    @POST("/v1/pets/{petId}/play")
    suspend fun postPlayPet(
        @Path("petId") petId: String
    ): ResponseUserPet

    @POST("/v1/pets/{petId}/food")
    suspend fun postFoodPet(
        @Path("petId") petId: String
    ): ResponseUserPet

    @GET("/v1/pets/me")
    suspend fun getPetList(): ResponsePets
}