package com.ddanddan.ddanddan.presentation.home.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(): ViewModel() {
    private val _snackBarEvent = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvent = _snackBarEvent.asSharedFlow()

    fun showSnackBarEvent(msg: String) {
        viewModelScope.launch {
            _snackBarEvent.emit(SnackBarEvent.ShowSnackBarMsg(msg))
        }
    }

    fun defaultSnackBarEvent() {
        viewModelScope.launch {
            _snackBarEvent.emit(SnackBarEvent.Default)
        }
    }
}

sealed interface SnackBarEvent {
    object Default: SnackBarEvent
    data class ShowSnackBarMsg(val msg: String): SnackBarEvent

}