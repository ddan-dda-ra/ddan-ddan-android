package com.ddanddan.ddanddan.presentation.signin

import android.content.Intent
import android.os.Bundle
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivitySigninBinding
import com.ddanddan.ddanddan.presentation.signup.SignUpActivity
import com.ddanddan.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity
    : BindingActivity<ActivitySigninBinding>(R.layout.activity_signin){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            btnKakao.setOnClickListener {
                startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
            }

        }
    }

    companion object {
    }
}