package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.UserService
import com.ddanddan.model.request.RequestLogin
import com.ddanddan.model.request.RequestMainPet
import com.ddanddan.model.request.RequestSignOut
import com.ddanddan.model.request.RequestUser
import com.ddanddan.model.response.ResponseMainPet
import com.ddanddan.model.response.ResponseUser
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUser(): ResponseUser = userService.getUser()
    suspend fun putUser(name: String, purposeCalorie: Int): ResponseUser = userService.putUser(
        RequestUser(name, purposeCalorie)
    )
    suspend fun deleteUser(cause: String): Boolean {
       return userService.deleteUser(RequestSignOut(cause)).isSuccessful
    }
    suspend fun getMainPet(): ResponseMainPet = userService.getMainPet()
    suspend fun postMainPet(petId: String) = userService.postMainPet(RequestMainPet(petId))
    suspend fun postLogin(token: String) = userService.postLogin(RequestLogin(token, "KAKAO"))
}