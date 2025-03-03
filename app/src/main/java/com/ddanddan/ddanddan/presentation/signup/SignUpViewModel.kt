package com.ddanddan.ddanddan.presentation.signup

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.domain.usecase.PostMainPetUseCase
import com.ddanddan.domain.usecase.PostTypePetUseCase
import com.ddanddan.domain.usecase.PutUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val putUserInfoUseCase: PutUserInfoUseCase,
    private val postTypePetUseCase: PostTypePetUseCase,
    private val postMainPetUseCase: PostMainPetUseCase
) : ViewModel(), ContainerHost<SignUpState, SignUpSideEffect> {

    override val container =
        container<SignUpState, SignUpSideEffect>(SignUpState())

    private fun isKoreanOnly(input: String): Boolean {
        val regex = "^[가-힣]+$"  // 한글 음절 블록만 허용하는 정규 표현식
        return input.matches(regex.toRegex())
    }

    fun setNickname(newNickname: String) = intent {
        reduce {
            state.copy(
                nickname = newNickname,
                isValidNickname = isKoreanOnly(newNickname) && newNickname.length > 2
            )
        }
    }

    fun incrementTarget() = intent {
        if (state.calorie < 1000) {
            reduce {
                state.copy(calorie = state.calorie + 100)
            }
        }
    }

    fun decrementTarget() = intent {
        if (state.calorie > 100) {
            reduce {
                state.copy(calorie = state.calorie - 100)
            }
        }
    }

    fun setPetType(petTypeEnum: PetTypeEnum) = intent {
        reduce {
            state.copy(petType = petTypeEnum)
        }
    }

    fun putUserInfo() = intent {
        putUserInfoUseCase(state.nickname, state.calorie)
            .onSuccess {
                postPetType()
            }
            .onFailure {
                postSideEffect(SignUpSideEffect.NetworkError("회원가입에 실패했습니다"))
            }
    }

    private fun postPetType() = intent {
        state.petType?.let { petType -> postTypePetUseCase(petType)
            .onSuccess { pet ->
                postMainPet(pet.id)
            }
            .onFailure {
                postSideEffect(SignUpSideEffect.NetworkError("회원가입에 실패했습니다"))
            }
        }
    }

    private fun postMainPet(petId: String) = intent {
        postMainPetUseCase(petId)
            .onSuccess {
                postSideEffect(SignUpSideEffect.SuccessSignUp)
            }
            .onFailure {
                postSideEffect(SignUpSideEffect.NetworkError("회원가입에 실패했습니다"))
            }
    }
}