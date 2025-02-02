package com.ddanddan.ddanddan.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddanddan.ddanddan.util.PreferencesKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    val tokenFlow: StateFlow<String?> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.ACCESS_TOKEN_KEY] } //todo - 추후에 이 부분이 토큰 갱신 때 문제 일으키진 않을지 확인 필요
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}

