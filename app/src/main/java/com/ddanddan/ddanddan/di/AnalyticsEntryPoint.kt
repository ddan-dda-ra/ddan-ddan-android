package com.ddanddan.ddanddan.di

import com.ddanddan.ddanddan.util.AnalyticsManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface AnalyticsEntryPoint {
    fun analyticsManager(): AnalyticsManager
}