package com.ddanddan.ddanddan.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivitySplashBinding
import com.ddanddan.ddanddan.util.NetworkManager
import com.ddanddan.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity
    : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNetwork()
    }

    private fun checkNetwork() {
        if (NetworkManager.checkNetworkState(this)) {
            initSplash(IS_FIRST_AFTER_INSTALL)
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

    private fun initSplash(state: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            when (state) {
                IS_FIRST_AFTER_INSTALL -> startOnBoarding()
//                IS_AUTO_LOGIN -> observer()
                else -> startSignIn()
            }
        }, 3000)
    }

    private fun startOnBoarding() {
        startActivity(Intent(this, OnBoardingActivity::class.java))
        finish()
    }

    private fun startSignIn() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    companion object {
        const val IS_FIRST_AFTER_INSTALL = 0
        const val IS_AUTO_LOGIN = 1
        const val HAVE_TO_SIGN_IN = 2
    }
}