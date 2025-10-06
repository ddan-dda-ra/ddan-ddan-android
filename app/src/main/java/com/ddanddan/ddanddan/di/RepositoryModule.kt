package com.ddanddan.ddanddan.di

import com.ddanddan.data.repository.AuthRepositoryImpl
import com.ddanddan.data.repository.FriendRepositoryImpl
import com.ddanddan.data.repository.PetRepositoryImpl
import com.ddanddan.data.repository.RankRepositoryImpl
import com.ddanddan.data.repository.UserRepositoryImpl
import com.ddanddan.domain.repository.AuthRepository
import com.ddanddan.domain.repository.FriendRepository
import com.ddanddan.domain.repository.PetRepository
import com.ddanddan.domain.repository.RankRepository
import com.ddanddan.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindPetRepository(petRepositoryImpl: PetRepositoryImpl): PetRepository

    @Singleton
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    fun bindRankRepository(rankRepositoryImpl: RankRepositoryImpl): RankRepository

    @Singleton
    @Binds
    fun bindFriendRepository(friendRepositoryImpl: FriendRepositoryImpl): FriendRepository
}