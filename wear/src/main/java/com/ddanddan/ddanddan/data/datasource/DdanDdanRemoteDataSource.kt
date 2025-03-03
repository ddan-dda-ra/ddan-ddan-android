package com.ddanddan.ddanddan.data.datasource

import com.ddanddan.ddanddan.data.model.request.RequestDailyCalorie
import com.ddanddan.ddanddan.data.service.DdanDdanService
import javax.inject.Inject

class DdanDdanRemoteDataSource @Inject constructor(
    private var ddanddanService: DdanDdanService,
) {
    suspend fun getUser() = ddanddanService.getUser()
    suspend fun getMainPet() = ddanddanService.getMainPet()
    suspend fun patchDailyCalorie(
        requestBody: RequestDailyCalorie
    ) = ddanddanService.patchDailyCalorie(requestBody = requestBody)
}
