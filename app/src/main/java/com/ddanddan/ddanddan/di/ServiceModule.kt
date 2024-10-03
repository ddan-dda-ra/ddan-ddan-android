package com.ddanddan.ddanddan.di

import com.ddanddan.data.service.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)
}