package com.ddanddan.ddanddan.presentation.kangmin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddanddan.ddanddan.domain.repository.PassiveDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CalorieViewModel @Inject constructor(
    private val passiveDataRepository: PassiveDataRepository
): ViewModel(){
    val caloriesValue = passiveDataRepository.latestCalories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)
}