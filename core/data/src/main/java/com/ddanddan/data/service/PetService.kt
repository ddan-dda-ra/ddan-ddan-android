package com.ddanddan.data.service

import com.ddanddan.model.request.RequestTypePet
import com.ddanddan.model.response.ResponsePet
import com.ddanddan.model.response.ResponsePetCatalog
import com.ddanddan.model.response.ResponsePets
import com.ddanddan.model.response.ResponseUserPet
import retrofit2.Response
import retrofit2.http.Body
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

    @POST("/v1/pets/me")
    suspend fun postTypePet(
        @Body petType: RequestTypePet
    ): ResponsePet

    @POST("/v1/pets/me/gacha")
    suspend fun postRandomPet(): ResponsePet

    // X-Pet-Catalog-Version 헤더는 PetCatalogInterceptor가 모든 요청에 일괄 설정한다
    @GET("/v1/pets/catalog")
    suspend fun getPetCatalog(): Response<ResponsePetCatalog>
}