package com.ddanddan.ddanddan.di

import com.ddanddan.data.repository.HomeRepositoryImpl
import com.ddanddan.data.repository.UserRepositoryImpl
import com.ddanddan.domain.repository.HomeRepository
import com.ddanddan.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.http.Body
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Singleton
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}