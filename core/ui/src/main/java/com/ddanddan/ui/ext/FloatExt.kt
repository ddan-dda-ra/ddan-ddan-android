package com.ddanddan.ui.ext

import android.content.Context

fun Float.dpToPx(context: Context): Int {
    return context.resources.displayMetrics.density.let { density ->
        (this * density).toInt()
    }
}
