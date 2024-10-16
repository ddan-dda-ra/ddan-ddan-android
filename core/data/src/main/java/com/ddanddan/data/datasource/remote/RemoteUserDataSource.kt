package com.ddanddan.data.datasource.remote

import com.ddanddan.data.service.UserService
import com.ddanddan.model.request.RequestLogin
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun login(token: String) = userService.login(RequestLogin(token, "KAKAO"))
}