package com.ddanddan.ddanddan.di

import android.content.Context
import com.ddanddan.ddanddan.util.provider.KakaoProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideKakaoProvider(@ApplicationContext context: Context): KakaoProvider = KakaoProvider(context)
}