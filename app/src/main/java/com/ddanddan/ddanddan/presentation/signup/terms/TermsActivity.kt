package com.ddanddan.ddanddan.presentation.signup.terms

import android.content.Intent
import android.os.Bundle
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivityTermsBinding
import com.ddanddan.ddanddan.presentation.signup.SignUpActivity
import com.ddanddan.ddanddan.util.SigninUtils.EXTRA_KEY_OAUTHID
import com.ddanddan.ddanddan.util.SigninUtils.EXTRA_KEY_URL
import com.ddanddan.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsActivity
    : BindingActivity<ActivityTermsBinding>(R.layout.activity_terms) {

    private var oauthId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIdProvider()
        initView()
        initTermsDetail()
    }

    private fun getIdProvider() {
        oauthId = intent.getStringExtra(EXTRA_KEY_OAUTHID).toString()
    }

    private fun initView() {
        with(binding) {
            btnTermsAll.setOnClickListener {
                btnTermsAll.isSelected = !btnTermsAll.isSelected
                btnTerms1.isSelected = btnTermsAll.isSelected
                btnTerms2.isSelected = btnTermsAll.isSelected
                btnStart.isEnabled = btnTermsAll.isSelected
            }
            btnTerms1.setOnClickListener {
                btnTerms1.isSelected = !btnTerms1.isSelected
                btnTermsAll.isSelected = checkAllSelected()
                btnStart.isEnabled = btnTermsAll.isSelected
            }
            btnTerms2.setOnClickListener {
                btnTerms2.isSelected = !btnTerms2.isSelected
                btnTermsAll.isSelected = checkAllSelected()
                btnStart.isEnabled = btnTermsAll.isSelected
            }
            btnStart.setOnClickListener {
                if (btnTermsAll.isSelected) {
                    val intent = Intent(this@TermsActivity, SignUpActivity::class.java)
                    intent.putExtra(EXTRA_KEY_OAUTHID, oauthId)
                    startActivity(intent)
                }
            }
        }
    }

    private fun initTermsDetail() {
        with(binding) {
            btnTermsDetail1.setOnClickListener {
                goToTermsWebView(1)
            }
            btnTermsDetail2.setOnClickListener {
                goToTermsWebView(2)
            }
        }
    }

    private fun goToTermsWebView(termsIndex: Int) {
        val intent = Intent(this@TermsActivity, TermsWebViewActivity::class.java)
        when (termsIndex) {
            1 -> intent.putExtra(EXTRA_KEY_URL, TERMS_URL_1)
            2 -> intent.putExtra(EXTRA_KEY_URL, TERMS_URL_2)
        }
        startActivity(intent)
    }

    private fun checkAllSelected(): Boolean {
        with (binding) {
            return btnTerms1.isSelected && btnTerms2.isSelected
        }
    }

    companion object {
        const val TERMS_URL_1 = "file:///android_asset/docs_terms_service.html"
        const val TERMS_URL_2 = "file:///android_asset/docs_terms_private.html"
    }
}