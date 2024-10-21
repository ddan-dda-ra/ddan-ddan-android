package com.ddanddan.ddanddan.presentation.signup.egg

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.FragmentSetEggBinding
import com.ddanddan.ddanddan.presentation.signup.SignUpActivity
import com.ddanddan.ddanddan.presentation.signup.SignUpProgress
import com.ddanddan.ddanddan.presentation.signup.SignUpViewModel
import com.ddanddan.domain.enums.PetTypeEnum
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
                setSelection(PetTypeEnum.CAT)
            }
            ivGreen.setOnClickListener {
                setSelection(PetTypeEnum.HAMSTER)
            }
            ivPurple.setOnClickListener {
                setSelection(PetTypeEnum.DOG)
            }
            ivBlue.setOnClickListener {
                setSelection(PetTypeEnum.PENGUIN)
            }
        }
    }

    private fun setSelection(petTypeEnum: PetTypeEnum) {
        (activity as SignUpActivity).activateNextButton()
        viewModel.setPetType(petTypeEnum)
        with(binding) {
            ivPink.isSelected = false
            ivGreen.isSelected = false
            ivPurple.isSelected = false
            ivBlue.isSelected = false

            when (petTypeEnum) {
                PetTypeEnum.CAT -> ivPink.isSelected = true
                PetTypeEnum.HAMSTER -> ivGreen.isSelected = true
                PetTypeEnum.DOG -> ivPurple.isSelected = true
                PetTypeEnum.PENGUIN -> ivBlue.isSelected = true
            }
        }
    }

}