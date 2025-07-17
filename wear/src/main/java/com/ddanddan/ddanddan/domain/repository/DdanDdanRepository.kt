package com.ddanddan.ddanddan.domain.repository

import com.ddanddan.ddanddan.data.model.request.RequestDailyCalorie
import com.ddanddan.ddanddan.domain.entity.MainPet
import com.ddanddan.ddanddan.domain.entity.User
import com.ddanddan.ddanddan.domain.entity.UserDailyInfo


interface DdanDdanRepository {
    suspend fun getUser() : User
    suspend fun getMainPet() : MainPet
    suspend fun patchDailyCalorie(requestBody: RequestDailyCalorie) : UserDailyInfo
}
