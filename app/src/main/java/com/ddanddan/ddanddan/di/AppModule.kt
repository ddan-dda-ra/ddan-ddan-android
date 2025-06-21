package com.ddanddan.ddanddan.di

import android.content.Context
import com.ddanddan.data.provider.KakaoProvider
import com.ddanddan.ddanddan.presentation.widget.WidgetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {
    @Provides
    fun provideKakaoProvider(@ActivityContext context: Context): KakaoProvider = KakaoProvider(context)

    @Provides
    @Singleton
    fun provideWidgetManager(@ApplicationContext context: Context): WidgetManager {
        return WidgetManager(context)
    }
}