package com.ddanddan.ddanddan.di

import com.ddanddan.ddanddan.data.service.DdanDdanService
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
    fun provideDdanDdanService(retrofit: Retrofit) = retrofit.create(DdanDdanService::class.java)
}