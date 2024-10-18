package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.UserService
import com.ddanddan.model.request.RequestLogin
import com.ddanddan.model.request.RequestUser
import com.ddanddan.model.response.ResponseMainPet
import com.ddanddan.model.response.ResponseUser
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUser(): ResponseUser = userService.getUser()
    suspend fun putUser(name: String, purposeCalorie: Int): ResponseUser = userService.putUser(RequestUser(name, purposeCalorie))

    suspend fun getMainPet(): ResponseMainPet = userService.getMainPet()
    suspend fun login(token: String) = userService.login(RequestLogin(token, "KAKAO"))
}