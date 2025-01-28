package com.ddanddan.ddanddan.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivitySplashBinding
import com.ddanddan.ddanddan.presentation.MainActivity
import com.ddanddan.ddanddan.presentation.onboarding.OnboardingActivity
import com.ddanddan.ddanddan.util.NetworkManager
import com.ddanddan.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity
    : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetwork()
    }

    private fun checkNetwork() {
        if (NetworkManager.checkNetworkState(this)) {
            initSplash(splashViewModel.isFirstAfterInstall())
        } else {
            AlertDialog.Builder(this)
                .setTitle("인터넷 연결")
                .setMessage("인터넷 연결을 확인해주세요.")
                .setCancelable(false)
                .setPositiveButton(
                    "확인",
                ) { _, _ ->
                    finishAffinity()
                }
                .create()
                .show()
        }
    }

    private fun initSplash(isFirst: Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirst) startOnBoarding()
            else if (splashViewModel.isAutoLoginEnabled()) startHome()
            else startSignIn()
        }, 3000)
    }

    private fun startOnBoarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }

    private fun startSignIn() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun startHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
    }
}