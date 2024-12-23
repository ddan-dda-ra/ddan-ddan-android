package com.ddanddan.ddanddan

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ddanddan.ddanddan.BuildConfig.DEBUG
import com.ddanddan.ddanddan.BuildConfig.KAKAO_APP_KEY
import com.ddanddan.ddanddan.presentation.signin.SignInActivity
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application(), LifecycleObserver {

    private lateinit var logoutReceiver: BroadcastReceiver

    override fun onCreate() {
        super.onCreate()
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
            FlipperUtil.init(this)
        }
        KakaoSdk.init(applicationContext, KAKAO_APP_KEY)
        appContext = applicationContext
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        initLogoutReceiver()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun initLogoutReceiver() {
        logoutReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "com.ddanddan.ddanddan.Logout") {
                    context?.let {
                        val signInIntent = Intent(it, SignInActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        it.startActivity(signInIntent)
                        if (it is Activity) {
                            it.finish()
                        }
                    }
                }
            }
        }
        val intentFilter = IntentFilter("com.ddanddan.ddanddan.Logout")
        LocalBroadcastManager.getInstance(this).registerReceiver(logoutReceiver, intentFilter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onAppBackgrounded() {
        isForeground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAppForegrounded() {
        isForeground = true
    }

    companion object {
        lateinit var appContext: Context
        var isForeground = false
    }
}
