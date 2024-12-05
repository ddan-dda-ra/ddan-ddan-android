package com.ddanddan.domain

import kotlinx.coroutines.flow.Flow

interface ddanddanDataStore {
    var userToken: String
    var refreshToken: String
    var isLogin: Boolean
    var isFirstAfterInstall: Boolean

    var userInfo: String
    var deviceToken: String
    var askedNotification: Boolean
    var onNotification: Boolean

    // 칼로리 값의 변화를 옵저빙할 수 있는 Flow
    val caloriesFlow: Flow<Float>
    var calories: Float

    fun clearLocalPref()
}
