package com.ddanddan.ddanddan.ext

import android.content.Context
import android.widget.Toast
import com.ddanddan.ddanddan.BuildConfig

/**
 * 디버그 모드에서만 Toast
 */
fun Context.showDebugToast(message: String) {
    if (BuildConfig.DEBUG) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

