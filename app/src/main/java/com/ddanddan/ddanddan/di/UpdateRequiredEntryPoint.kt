package com.ddanddan.ddanddan.di

import com.ddanddan.data.update.UpdateRequiredNotifier
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface UpdateRequiredEntryPoint {
    fun updateRequiredNotifier(): UpdateRequiredNotifier
}
