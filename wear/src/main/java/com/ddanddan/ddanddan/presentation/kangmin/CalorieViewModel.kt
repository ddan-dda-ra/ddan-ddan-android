package com.ddanddan.ddanddan.presentation.kangmin

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddanddan.ddanddan.domain.repository.PassiveDataRepository
import com.ddanddan.ddanddan.util.PreferencesKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CalorieViewModel @Inject constructor(
    private val passiveDataRepository: PassiveDataRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    val caloriesValue = passiveDataRepository.latestCalories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)


    val petType = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.PET_TYPE] ?: ""
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    val petLevel = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.PET_LEVEL] ?: 1
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)

    val targetCalories = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.TARGET_CALORIES] ?: 0
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    // 펫 타입과 레벨을 함께 가져오는 Flow
    val petInfo = combine(
        petType,
        petLevel,
        targetCalories
    ) { type, level, calories ->
        Triple(type, level, calories)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Triple("", 1, 0)
    )

}