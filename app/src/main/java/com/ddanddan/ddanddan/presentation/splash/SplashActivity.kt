package com.ddanddan.ddanddan.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddanddan.ddanddan.BuildConfig.KAKAO_APP_KEY
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivitySplashBinding
import com.ddanddan.ddanddan.presentation.MainActivity
import com.ddanddan.ddanddan.presentation.onboarding.OnboardingActivity
import com.ddanddan.ddanddan.presentation.signin.SignInActivity
import com.ddanddan.ddanddan.presentation.signin.SignInState
import com.ddanddan.ddanddan.presentation.signin.SignInViewModel
import com.ddanddan.ddanddan.util.NetworkManager
import com.ddanddan.ddanddan.util.PermissionUtils
import com.ddanddan.ui.base.BindingActivity
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity
    : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel by viewModels<SplashViewModel>()
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KakaoSdk.init(applicationContext, KAKAO_APP_KEY)
        signInObserver()
        autoLoginObserver()
        checkNetwork()
    }

    private fun checkNetwork() {
        if (NetworkManager.checkNetworkState(this)) {
            initSplash(PermissionUtils.isLocationPermissionGranted(applicationContext))
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

    private fun initSplash(isGranted: Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isGranted) {
                if (splashViewModel.isAutoLoginEnabled()) splashViewModel.autoLoginWithKakao()
                else startSignIn()
            }
            else startOnBoarding()
        }, 3000)
    }

    private fun startOnBoarding() {
        startActivity(Intent(this, OnboardingActivity::class.java))
        finish()
    }

    private fun startSignIn() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    private fun autoLoginObserver() {
        splashViewModel.autoLoginState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is AutoLoginState.Success -> {
                        signInViewModel.login(it.token)
                    }
                    is AutoLoginState.Failure -> {
                        Timber.d(it.msg)
                        startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                        finish()
                    }
                    else -> {
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun signInObserver() {
        signInViewModel.signInState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is SignInState.Success -> {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                    is SignInState.Failure -> {
                        startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                        finish()
                    }
                    else -> {
                    }
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
    }
}