package com.ddanddan.ddanddan.presentation.signup.name

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.FragmentGetNameBinding
import com.ddanddan.ddanddan.presentation.signup.SignUpActivity
import com.ddanddan.ddanddan.presentation.signup.SignUpProgress
import com.ddanddan.ddanddan.presentation.signup.SignUpViewModel
import com.ddanddan.ui.base.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class GetNameFragment
    : BindingFragment<FragmentGetNameBinding>(R.layout.fragment_get_name) {

    private val viewModel by activityViewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        viewModel.moveSignUpProgress(SignUpProgress.Name)
        binding.etName.filters = arrayOf(filterAlphaNumSpace)
        setTextChangedListener()
        checkValidinput()
    }

    private fun setTextChangedListener() {
        binding.etName.doAfterTextChanged {
            viewModel.updateUserName(it.toString())
        }
    }

    private var filterAlphaNumSpace = InputFilter { source, _, _, _, _, _ ->
        val ps = Pattern.compile(REGEX_NAME_PATTERN_WRITING)
        if (!ps.matcher(source).matches()) {
            ""
        } else source
    }

    private fun checkValidinput() {
        lifecycleScope.launch {
            viewModel.userName.collectLatest { userName ->
                if (Pattern.matches(REGEX_NAME_PATTERN_SUBMIT, userName) && userName.trim().length >= 2) {
                    (activity as SignUpActivity).activateNextButton()
                    binding.tvCaption.visibility = View.INVISIBLE
                }
                else {
                    (activity as SignUpActivity).disableNextButton()
                    if (userName.isNotBlank()) binding.tvCaption.visibility = View.VISIBLE
                    else binding.tvCaption.visibility = View.INVISIBLE
                }
            }
        }
    }

    companion object {
        private const val REGEX_NAME_PATTERN_SUBMIT = "^([가-힣]*)\$"
        private const val REGEX_NAME_PATTERN_WRITING = "^[가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+\$"
    }
}