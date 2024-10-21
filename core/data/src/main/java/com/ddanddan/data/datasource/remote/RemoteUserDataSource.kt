package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.UserService
import com.ddanddan.model.request.RequestLogin
import com.ddanddan.model.request.RequestMainPet
import com.ddanddan.model.response.ResponseMainPet
import com.ddanddan.model.response.ResponseUser
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUser(): ResponseUser = userService.getUser()

    suspend fun getMainPet(): ResponseMainPet = userService.getMainPet()
    suspend fun postMainPet(petId: String) = userService.postMainPet(RequestMainPet(petId))
    suspend fun login(token: String) = userService.login(RequestLogin(token, "KAKAO"))
}