package com.ddanddan.ddanddan

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.ddanddan.ddanddan.BuildConfig.DEBUG
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
            FlipperUtil.init(this)
        }
        appContext = applicationContext
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onAppBackgrounded() { isForeground = false }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAppForegrounded() { isForeground = true}

    companion object {
        lateinit var appContext: Context
        var isForeground = false
    }
}
