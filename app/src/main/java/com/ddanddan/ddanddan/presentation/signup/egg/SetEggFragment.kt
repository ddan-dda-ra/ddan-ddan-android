package com.ddanddan.ddanddan.presentation.signup.egg

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.FragmentSetEggBinding
import com.ddanddan.ddanddan.presentation.signup.SignUpActivity
import com.ddanddan.ddanddan.presentation.signup.SignUpProgress
import com.ddanddan.ddanddan.presentation.signup.SignUpViewModel
import com.ddanddan.ui.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectEggFragment
    : BindingFragment<FragmentSetEggBinding>(R.layout.fragment_set_egg) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        viewModel.moveSignUpProgress(SignUpProgress.Egg)
        (activity as SignUpActivity).disableNextButton()
        setUpInitialListener()
    }

    private fun setUpInitialListener() {
        with(binding) {
            ivPink.setOnClickListener {
                setSelection(0)
            }
            ivGreen.setOnClickListener {
                setSelection(1)
            }
            ivPurple.setOnClickListener {
                setSelection(2)
            }
            ivBlue.setOnClickListener {
                setSelection(3)
            }
        }
    }

    private fun setSelection(color: Int) {
        (activity as SignUpActivity).activateNextButton()
        viewModel.setEggColor(color)
        with(binding) {
            ivPink.isSelected = false
            ivGreen.isSelected = false
            ivPurple.isSelected = false
            ivBlue.isSelected = false

            when (color) {
                0 -> ivPink.isSelected = true
                1 -> ivGreen.isSelected = true
                2 -> ivPurple.isSelected = true
                3 -> ivBlue.isSelected = true
            }
        }
    }

}