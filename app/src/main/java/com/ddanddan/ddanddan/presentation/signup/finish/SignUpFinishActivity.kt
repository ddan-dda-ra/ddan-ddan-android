package com.ddanddan.ddanddan.presentation.signup.finish

import android.os.Bundle
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivitySignupFinishBinding
import com.ddanddan.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFinishActivity
    : BindingActivity<ActivitySignupFinishBinding>(R.layout.activity_signup_finish) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpInitialListener()
    }

    private fun setUpInitialListener() {
        with(binding) {
            btnStart.setOnClickListener {

            }
        }
    }
}