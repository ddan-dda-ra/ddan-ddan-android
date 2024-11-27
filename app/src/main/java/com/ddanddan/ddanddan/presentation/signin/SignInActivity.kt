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
import com.ddanddan.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignInActivity
    : BindingActivity<ActivitySigninBinding>(R.layout.activity_signin){

    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observer()
        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            btnKakao.setOnClickListener {
                viewModel.loginWithKakao(this@SignInActivity)
            }
        }
    }

    private fun observer() {
        viewModel.signInState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is SignInState.Success -> {
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
                    else -> { }
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
    }
}