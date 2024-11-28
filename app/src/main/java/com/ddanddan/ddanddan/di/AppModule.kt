package com.ddanddan.ddanddan.di

import android.content.Context
import com.ddanddan.ddanddan.util.provider.KakaoProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object AppModule {
    @Provides
    fun provideKakaoProvider(@ActivityContext context: Context): KakaoProvider = KakaoProvider(context)
}