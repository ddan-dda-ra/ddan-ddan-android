package com.ddanddan.ddanddan.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivitySigninBinding
import com.ddanddan.ddanddan.presentation.MainActivity
import com.ddanddan.ddanddan.presentation.signup.terms.TermsActivity
import com.ddanddan.ddanddan.util.WatchUtils
import com.ddanddan.ddanddan.util.provider.KakaoProvider
import com.ddanddan.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity
    : BindingActivity<ActivitySigninBinding>(R.layout.activity_signin) {

    @Inject
    lateinit var kakaoProvider: KakaoProvider
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observer()
        setClickListener()
    }

    private fun disableOnboarding() {
        viewModel.setFirstAfterInstall(false)
    }

    private fun setClickListener() {
        with(binding) {
            btnKakao.setOnClickListener {
                disableOnboarding()
                kakaoProvider.loginWithKakao { token, error ->
                    if (error == null) token?.accessToken?.let { viewModel.login(it) }
                }
            }
        }
    }

    private fun observer() {
        viewModel.signInState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is SignInState.Success -> {
                        sendTokenToWatch(
                            accessToken = it.accessToken,
                            refreshToken = it.refreshToken
                        )
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        finish()
                    }

                    is SignInState.UserNotRegistered -> {
                        startActivity(Intent(this@SignInActivity, TermsActivity::class.java))
                        finish()
                    }

                    is SignInState.Failure -> {
                        Toast.makeText(this@SignInActivity, it.msg, Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    private fun sendTokenToWatch(accessToken: String, refreshToken: String) {
        WatchUtils.checkWatchConnection(
            context = this,
            onConnected = { nodes ->
                WatchUtils.sendTokenToWatch(
                    context = this,
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
                Timber.d("Access token sent to connected watches: ${nodes.map { it.displayName }}")
            },
            onNotConnected = {
                Timber.w("No connected watches. Access token was not sent.")
            }
        )
    }
}