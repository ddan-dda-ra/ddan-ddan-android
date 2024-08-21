package com.ddanddan.domain

interface ddanddanDataStore {
    var userToken: String
    var refreshToken: String
    var isLogin: Boolean
    var isFirstAfterInstall: Boolean

    var userInfo: String
    var deviceToken: String
    var askedNotification: Boolean
    var onNotification: Boolean

    fun clearLocalPref()
}
