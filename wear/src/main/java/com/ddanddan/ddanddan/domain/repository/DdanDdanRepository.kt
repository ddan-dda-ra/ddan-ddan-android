package com.ddanddan.ddanddan.domain.repository

import com.ddanddan.ddanddan.data.model.request.RequestDailyCalorie
import com.ddanddan.ddanddan.domain.entity.MainPet
import com.ddanddan.ddanddan.domain.entity.User
import com.ddanddan.ddanddan.domain.entity.UserDailyInfo

/**
 * 워치 앱에서 다뤄질 api 갯수가 많지 않고 앞으로 더 늘어날 여지도 적어보여서 하나로 관리하고자 함
 * 추후 쪼개질 여지는 있음
 */
interface DdanDdanRepository {
    suspend fun getUser() : User
    suspend fun getMainPet() : MainPet
    suspend fun patchDailyCalorie(requestBody: RequestDailyCalorie) : UserDailyInfo
}
