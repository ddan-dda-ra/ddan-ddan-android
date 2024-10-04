package com.ddanddan.ddanddan.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivitySignupBinding
import com.ddanddan.ddanddan.presentation.signup.egg.SelectEggFragment
import com.ddanddan.ddanddan.presentation.signup.goal.SetGoalFragment
import com.ddanddan.ddanddan.presentation.signup.name.GetNameFragment
import com.ddanddan.ddanddan.util.SigninUtils.EXTRA_KEY_OAUTHID
import com.ddanddan.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity
    : BindingActivity<ActivitySignupBinding>(R.layout.activity_signup) {

    private val viewModel by viewModels<SignUpViewModel>()
    private var oauthId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIdProvider()
        setStartingFragment()
        setUpInitialListener()

    }

    private fun getIdProvider() {
        oauthId = intent.getStringExtra(EXTRA_KEY_OAUTHID).toString()
    }

    private fun setStartingFragment() {
        navigateTo<GetNameFragment>()
    }

    fun activateNextButton() {
        binding.btnNext.isEnabled = true
    }

    fun disableNextButton() {
        binding.btnNext.isEnabled = false
    }

    private fun setUpInitialListener() {
        binding.btnNext.setOnClickListener {
            when (viewModel.signUpProgress.value) {
                SignUpProgress.Name -> {
                    navigateTo<SetGoalFragment>()
                }
                SignUpProgress.Goal -> {
                    navigateTo<SelectEggFragment>()
                }
                SignUpProgress.Egg -> {
                    goToSignUpFinishActivity()
                    finish()
                }
            }
        }
    }

    private fun goToSignUpFinishActivity() {
        val intent = Intent(this, SignUpFinishActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    inline fun <reified T : Fragment> navigateTo() {
        disableNextButton()
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_signup, T::class.java.canonicalName)
            addToBackStack(null)
        }
    }
}