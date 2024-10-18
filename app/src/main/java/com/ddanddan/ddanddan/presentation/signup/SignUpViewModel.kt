package com.ddanddan.ddanddan.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddanddan.domain.enum.PetTypeEnum
import com.ddanddan.domain.repository.UserRepository
import com.ddanddan.domain.usecase.GetPetListUseCase
import com.ddanddan.domain.usecase.PostTypePetUseCase
import com.ddanddan.domain.usecase.PutUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val putUserInfoUseCase: PutUserInfoUseCase,
    private val postTypePetUseCase: PostTypePetUseCase
) : ViewModel(), ContainerHost<SignUpState, SignUpSideEffect> {

    override val container =
        container<SignUpState, SignUpSideEffect>(SignUpState())

    private val _signUpProgress = MutableStateFlow<SignUpProgress>(SignUpProgress.Name)
    val signUpProgress: StateFlow<SignUpProgress> = _signUpProgress.asStateFlow()

    fun moveSignUpProgress(progress: SignUpProgress) {
        _signUpProgress.value = progress
    }

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    fun updateUserName(userName: String) {
        _userName.value = userName
    }

    private val _goalCalories = MutableStateFlow<Int>(300)
    val goalCalories: StateFlow<Int> = _goalCalories.asStateFlow()

    fun plusGoalCalories(): Boolean {
        if (_goalCalories.value >= 1000) return false
        _goalCalories.value += 100
        return true
    }

    fun minusGoalCalories(): Boolean {
        if (_goalCalories.value <= 100) return false
        _goalCalories.value -= 100
        return true
    }

    private val _petType = MutableStateFlow<PetTypeEnum?>(null)
    val petType: StateFlow<PetTypeEnum?> = _petType.asStateFlow()

    fun setPetType(petTypeEnum: PetTypeEnum) {
        _petType.value = petTypeEnum
    }

    fun putUserInfo() = intent {
        putUserInfoUseCase(userName.value, goalCalories.value)
            .onSuccess {
                reduce {
                    state.copy(isLoading = true)
                }
                postTypePet()
            }.onFailure {
                postSideEffect(SignUpSideEffect.ToastNetworkError)
            }
    }

    private fun postTypePet() = intent {
        petType.value?.let { postTypePetUseCase(it)
            .onSuccess {
                reduce {
                    state.copy(newPet = it)
                }
                postMainPet()
            }
            .onFailure {
                postSideEffect(SignUpSideEffect.ToastNetworkError)
            }
        }
    }

    private fun postMainPet() = intent {

    }
}

enum class SignUpProgress {
    Name, Goal, Egg
}