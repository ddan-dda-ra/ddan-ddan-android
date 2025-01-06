package com.ddanddan.domain.repository

interface AuthRepository {
    fun enableAutoLogin()
    fun disableAutoLogin()
    fun getAutoLogin(): Boolean
    fun isFirstAfterInstall(): Boolean
    fun setFirstAfterInstall(isFirst: Boolean)
}