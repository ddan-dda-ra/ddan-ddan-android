package com.ddanddan.ddanddan.di

import com.ddanddan.data.repository.PetRepositoryImpl
import com.ddanddan.domain.repository.PetRepository
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
}