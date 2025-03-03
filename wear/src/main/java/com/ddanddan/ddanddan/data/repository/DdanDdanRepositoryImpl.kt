package com.ddanddan.ddanddan.data.repository

import com.ddanddan.ddanddan.data.datasource.DdanDdanRemoteDataSource
import com.ddanddan.ddanddan.data.model.request.RequestDailyCalorie
import com.ddanddan.ddanddan.data.model.response.toMainPet
import com.ddanddan.ddanddan.data.model.response.toUser
import com.ddanddan.ddanddan.data.model.response.toUserDailyInfo
import com.ddanddan.ddanddan.domain.entity.MainPet
import com.ddanddan.ddanddan.domain.entity.User
import com.ddanddan.ddanddan.domain.entity.UserDailyInfo
import com.ddanddan.ddanddan.domain.repository.DdanDdanRepository
import javax.inject.Inject

class DdanDdanRepositoryImpl @Inject constructor(
    private val ddanDdanRemoteDataSource: DdanDdanRemoteDataSource
) : DdanDdanRepository {
    override suspend fun getUser(): User {
        return ddanDdanRemoteDataSource.getUser().toUser()
    }

    override suspend fun getMainPet(): MainPet {
        return ddanDdanRemoteDataSource.getMainPet().toMainPet()
    }

    override suspend fun patchDailyCalorie(requestBody: RequestDailyCalorie): UserDailyInfo {
        return ddanDdanRemoteDataSource.patchDailyCalorie(requestBody = requestBody).toUserDailyInfo()
    }
}
