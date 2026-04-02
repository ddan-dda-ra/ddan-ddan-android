package com.ddanddan.ddanddan.util

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

interface AnalyticsManager {
    fun logEvent(event: AnalyticsEvent)
}

class FirebaseAnalyticsManager @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsManager {

    override fun logEvent(event: AnalyticsEvent) {
        val bundle = Bundle().apply {
            event.parameter.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Double -> putDouble(key, value)
                    is Long -> putLong(key, value)
                }
            }
        }
        firebaseAnalytics.logEvent(event.title, bundle)
    }
}