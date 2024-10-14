package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.UserService
import com.ddanddan.model.response.ResponseMainPet
import com.ddanddan.model.response.ResponsePet
import com.ddanddan.model.response.ResponseUser
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUser(): ResponseUser = userService.getUser()

    suspend fun getMainPet(): ResponseMainPet = userService.getMainPet()
}