package com.ddanddan.data.repository

import com.ddanddan.domain.ddanddanDataStore
import com.ddanddan.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val ddanddanDataStore: ddanddanDataStore
): AuthRepository {

    override fun enableAutoLogin() {
        ddanddanDataStore.isLogin = true
    }

    override fun disableAutoLogin() {
        ddanddanDataStore.isLogin = false
    }

    override fun getAutoLogin(): Boolean = ddanddanDataStore.isLogin && ddanddanDataStore.userToken.isNotBlank()

    override fun isFirstAfterInstall(): Boolean {
        val isFirst = ddanddanDataStore.isFirstAfterInstall
        if (isFirst) setFirstAfterInstall(false)
        return isFirst
    }

    override fun setFirstAfterInstall(isFirst: Boolean) {
        ddanddanDataStore.isFirstAfterInstall = isFirst
    }

    override fun isNotificationAsked(): Boolean = ddanddanDataStore.askedNotification

    override fun setNotificationAsked(isAsked: Boolean) {
        ddanddanDataStore.askedNotification = isAsked
    }
}