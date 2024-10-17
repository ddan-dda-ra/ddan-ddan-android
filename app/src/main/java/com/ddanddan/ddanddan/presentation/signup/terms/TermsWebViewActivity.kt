package com.ddanddan.ddanddan.presentation.signup.terms

import android.os.Bundle
import android.webkit.CookieManager
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.databinding.ActivityTermsWebviewBinding
import com.ddanddan.ddanddan.util.SigninUtils.EXTRA_KEY_URL
import com.ddanddan.ui.base.BindingActivity

class TermsWebViewActivity
    : BindingActivity<ActivityTermsWebviewBinding>(R.layout.activity_terms_webview) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val termsUrl = intent.getStringExtra(EXTRA_KEY_URL)
        initCookieManager()
        if (termsUrl != null) {
            initWebView(termsUrl)
        }
        else finish()
    }

    private fun initCookieManager() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
    }

    private fun initWebView(url: String) {
        with(binding) {
            termsWebview.settings.javaScriptEnabled = true
            termsWebview.loadUrl(url)
        }
    }

    companion object {
    }
}