package com.ddanddan.data.update

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class UpdateRequiredNotifier @Inject constructor() {
    private val _updateRequired = MutableStateFlow(false)
    val updateRequired: StateFlow<Boolean> = _updateRequired.asStateFlow()

    fun notifyUpdateRequired() {
        _updateRequired.value = true
    }
}
