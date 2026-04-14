package com.ddanddan.ddanddan.util

interface AnalyticsEvent {
    val title: String
    val parameter: Map<String, Any>
}