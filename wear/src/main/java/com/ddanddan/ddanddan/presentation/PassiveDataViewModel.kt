package com.ddanddan.ddanddan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ddanddan.ddanddan.data.model.request.RequestDailyCalorie
import com.ddanddan.ddanddan.domain.entity.MainPet
import com.ddanddan.ddanddan.domain.entity.User
import com.ddanddan.ddanddan.domain.repository.DdanDdanRepository
import com.ddanddan.ddanddan.domain.repository.HealthServicesRepository
import com.ddanddan.ddanddan.domain.repository.PassiveDataRepository
import com.ddanddan.ddanddan.util.WorkerUtils.SYNC_CALORIES_WORKER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PassiveDataViewModel @Inject constructor(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository,
    private val ddanDdanRepository: DdanDdanRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    private val _mainPetState = MutableStateFlow<MainPet?>(null)
    val mainPetState: StateFlow<MainPet?> = _mainPetState.asStateFlow()

    val caloriesValue = passiveDataRepository.latestCalories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)

    init {
        // 워치 디바이스가 칼로리 수집을 지원하는지 확인
        viewModelScope.launch {
            checkCalorieSupportAndRegister()
        }

        // 목표 칼로리, 메인펫 화면 갱신
        viewModelScope.launch {
            loadUserAndPetData()
        }

        // 칼로리가 수집될 때마다 100 단위로 나누어 떨어지는지 확인 후 필요 시 서버로 전송
        viewModelScope.launch {
            passiveDataRepository.calorieCollectedSignal.collect {
                uploadCaloriesToServer()
            }
        }

        // 칼로리 동기화 성공 시 화면 최신화
        observeSyncCaloriesWork()
    }

    private suspend fun loadUserAndPetData() {
        flow {
            val user = ddanDdanRepository.getUser()
            val mainPet = ddanDdanRepository.getMainPet()
            emit(user to mainPet)
        }
            .onStart { _uiState.value = UiState.Loading }
            .catch { e ->
                Timber.e("Failed to load user or pet data: ${e.message}")
                _uiState.value = UiState.Error(e.message.toString())
            }
            .collect { (user, mainPet) ->
                _userState.value = user
                _mainPetState.value = mainPet
                _uiState.value = UiState.Supported
            }
    }

    private fun observeSyncCaloriesWork() {
        workManager.getWorkInfosForUniqueWorkLiveData(SYNC_CALORIES_WORKER)
            .observeForever { workInfos ->
                val workInfo = workInfos.firstOrNull()
                if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                    viewModelScope.launch {
                        loadUserAndPetData()
                    }
                }
            }
    }


    private suspend fun checkCalorieSupportAndRegister() {
        val isCalorieSupported = healthServicesRepository.hasCaloriesCapability()
        if (isCalorieSupported) {
            healthServicesRepository.registerForCaloriesData()
        } else {
            _uiState.value = UiState.NotSupported
        }
    }

    private fun uploadCaloriesToServer() {
        viewModelScope.launch {
            passiveDataRepository.getCaloriesToSend()
                ?.let { caloriesToSend ->
                    flow {
                        val requestBody = RequestDailyCalorie(caloriesToSend.toInt())
                        val response = ddanDdanRepository.patchDailyCalorie(requestBody)
                        emit(response)
                    }
                        .catch { e ->
                            Timber.e(e, "Error uploading calories")
                            _uiState.value = UiState.Error(e.message.toString())
                        }
                        .collect {
                            _uiState.value = UiState.Supported
                        }
                }
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    object NotSupported : UiState()
    object Supported : UiState()
    data class Error(val message: String) : UiState()
}