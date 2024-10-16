package com.ddanddan.ddanddan.presentation.signup.goal

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.FragmentSetGoalBinding
import com.ddanddan.ddanddan.presentation.signup.SignUpActivity
import com.ddanddan.ddanddan.presentation.signup.SignUpProgress
import com.ddanddan.ddanddan.presentation.signup.SignUpViewModel
import com.ddanddan.ui.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetGoalFragment
    : BindingFragment<FragmentSetGoalBinding>(R.layout.fragment_set_goal) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        viewModel.moveSignUpProgress(SignUpProgress.Goal)
        (activity as SignUpActivity).activateNextButton()
        setUpInitialListener()
    }

    private fun setUpInitialListener() {
        with(binding) {
            tvGoal.text = viewModel.goalCalories.value.toString()
            ivMinus.setOnClickListener {
                val result = viewModel.minusGoalCalories()
                tvGoal.text = viewModel.goalCalories.value.toString()
                if (!result) Toast.makeText(requireActivity(), "최소 100칼로리 이상 설정해 주세요", Toast.LENGTH_SHORT).show()
            }
            ivPlus.setOnClickListener {
                val result = viewModel.plusGoalCalories()
                tvGoal.text = viewModel.goalCalories.value.toString()
                if (!result) Toast.makeText(requireActivity(), "최대 1000칼로리까지 설정할 수 있어요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}