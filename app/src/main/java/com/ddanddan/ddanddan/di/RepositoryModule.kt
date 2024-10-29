package com.ddanddan.ddanddan.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ddanddan.data.repository.HealthServicesRepositoryImpl
import com.ddanddan.data.repository.PassiveDataRepositoryImpl
import com.ddanddan.data.repository.PetRepositoryImpl
import com.ddanddan.data.repository.UserRepositoryImpl
import com.ddanddan.domain.repository.HealthServicesRepository
import com.ddanddan.domain.repository.PassiveDataRepository
import com.ddanddan.domain.repository.PetRepository
import com.ddanddan.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideHealthServicesRepository(
        @ApplicationContext context: Context
    ): HealthServicesRepository {
        return HealthServicesRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun providePassiveDataRepository(
        dataStore: DataStore<Preferences>
    ): PassiveDataRepository {
        return PassiveDataRepositoryImpl(dataStore)
    }
}
