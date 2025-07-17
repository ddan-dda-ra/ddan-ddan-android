package com.ddanddan.ddanddan.presentation.home

import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.presentation.widget.WidgetManager
import com.ddanddan.domain.repository.UserRepository
import com.ddanddan.domain.usecase.GetMainPetUseCase
import com.ddanddan.domain.usecase.GetNotificationAskedUseCase
import com.ddanddan.domain.usecase.GetUserInfoUseCase
import com.ddanddan.domain.usecase.PostFoodPetUseCase
import com.ddanddan.domain.usecase.PostMainPetUseCase
import com.ddanddan.domain.usecase.PostPlayPetUseCase
import com.ddanddan.domain.usecase.PostRandomPetUseCase
import com.ddanddan.ui.enums.TooltipType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getMainPetUseCase: GetMainPetUseCase,
    private val postPlayPetUseCase: PostPlayPetUseCase,
    private val postFoodPetUseCase: PostFoodPetUseCase,
    private val postRandomPetUseCase: PostRandomPetUseCase,
    private val postMainPetUseCase: PostMainPetUseCase,
    private val getNotificationAskedUseCase: GetNotificationAskedUseCase,
    private val userRepository: UserRepository,
    private val widgetManager: WidgetManager
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {
    override val container =
        container<HomeState, HomeSideEffect>(HomeState())

    init {
        getNotificationAsked()
        getHomeInfo()
        observeCalories()
    }

    fun getHomeInfo() {
        getUserInfo()
        getMainPet()
    }

    private fun getNotificationAsked() = intent {
        val isAsked = getNotificationAskedUseCase()
        if (!isAsked) postSideEffect(HomeSideEffect.AskNotification)
    }

    private fun getUserInfo() = intent {
        getUserInfoUseCase()
            .onSuccess {
                reduce {
                    state.copy(user = it)
                }
            }.onFailure {
                if (it is HttpException) {
                    postSideEffect(HomeSideEffect.NetworkError(it.code()))
                } else {
                    postSideEffect(HomeSideEffect.NetworkError(null))
                }
            }
    }

    private fun getMainPet() = intent {
        getMainPetUseCase()
            .onSuccess {
                reduce {
                    state.copy(pet = it)
                }
                widgetManager.updateWidgetPet(it.type.toString(), it.level)
            }.onFailure {
                if (it is HttpException) {
                    postSideEffect(HomeSideEffect.NetworkError(it.code()))
                } else {
                    postSideEffect(HomeSideEffect.NetworkError(null))
                }
            }
    }

    private fun postRandomPet() = intent {
        postRandomPetUseCase()
            .onSuccess {
                reduce {
                    state.copy(pet = it)
                }
                postMainPet(it.id)
                postSideEffect(HomeSideEffect.NavigateNewPet(it.type))
            }.onFailure {
                postSideEffect(HomeSideEffect.SnackBarMsg("새로운 펫을 불러오는데 오류가 발생했습니다."))
            }
    }

    fun postPlayPet() = intent {
        state.pet?.let { pet ->
            if ((state.user?.toyQuantity ?: 0) > 0) {
                postPlayPetUseCase(pet.id)
                    .onSuccess {
                        if (it.pet.level == MAX_LEVEL && it.pet.expPercent.toInt() == MAX_PERCENTS) {
                            postRandomPet()
                        } else if (it.pet.level > (state.pet?.level ?: 0)) {
                            postSideEffect(HomeSideEffect.NavigateLevelUp(it.pet.level, it.pet.type))
                        }
                        reduce {
                            showTooltipState(true, TooltipType.PLAY)
                            state.copy(user = it.user, pet = it.pet, isPlayAndEatLottie = true)
                        }
                        delay(1600)
                        reduce {
                            state.copy(isPlayAndEatLottie = false)
                        }
                    }.onFailure {
                        if (it is HttpException) {
                            postSideEffect(HomeSideEffect.NetworkError(it.code()))
                        } else {
                            postSideEffect(HomeSideEffect.NetworkError(null))
                        }
                    }
            } else {
                postSideEffect(HomeSideEffect.SnackBarMsg("놀아주기 개수가 부족합니다."))
            }
        } ?: run {
            postSideEffect(HomeSideEffect.SnackBarMsg("펫 아이디에 오류가 발생했습니다."))
        }
    }

    fun postFoodPet() = intent {
        state.pet?.let { pet ->
            if ((state.user?.foodQuantity ?: 0) > 0) {
                postFoodPetUseCase(pet.id)
                    .onSuccess {
                        if (it.pet.level == MAX_LEVEL && it.pet.expPercent.toInt() == MAX_PERCENTS) {
                            postRandomPet()
                        } else if (it.pet.level > (state.pet?.level ?: 0)) {
                            postSideEffect(HomeSideEffect.NavigateLevelUp(it.pet.level, it.pet.type))
                        }
                        reduce {
                            showTooltipState(true, TooltipType.EAT)
                            state.copy(user = it.user, pet = it.pet, isPlayAndEatLottie = true)
                        }
                        delay(1600)
                        reduce {
                            state.copy(isPlayAndEatLottie = false)
                        }
                    }.onFailure {
                        if (it is HttpException) {
                            postSideEffect(HomeSideEffect.NetworkError(it.code()))
                        } else {
                            postSideEffect(HomeSideEffect.NetworkError(null))
                        }
                    }
            } else {
                postSideEffect(HomeSideEffect.SnackBarMsg("먹이주기 개수가 부족합니다."))
            }
        } ?: run {
            postSideEffect(HomeSideEffect.SnackBarMsg("펫 아이디에 오류가 발생했습니다."))
        }
    }

    private fun postMainPet(mainPetId: String) = intent {
        reduce {
            state.copy(isLoading = true)
        }
        postMainPetUseCase(mainPetId)
            .onSuccess {
                reduce {
                    state.copy(pet = it)
                }
            }.onFailure {
                if (it is HttpException) {
                    postSideEffect(HomeSideEffect.NetworkError(it.code()))
                } else {
                    postSideEffect(HomeSideEffect.NetworkError(null))
                }
            }
        reduce {
            state.copy(
                isLoading = false
            )
        }
    }

    fun onRankingClick() = intent {
//        postSideEffect(HomeSideEffect.NavigatePetCollection(state.pet?.id ?: ""))
        postSideEffect(HomeSideEffect.NavigateRanking)
    }

    fun onSettingClick() = intent {
        postSideEffect(HomeSideEffect.NavigateSetting)
    }

    fun showTooltipState(isShowTooltip: Boolean, tooltipType: TooltipType) = intent {
        reduce {
            state.copy(isShowTooltipState = isShowTooltip, tooltipType = tooltipType)
        }
    }

    fun setTooltipState(isShowTooltip: Boolean) = intent {
        reduce {
            state.copy(isShowTooltipState = isShowTooltip)
        }
    }

    fun setCurrentTooltipMsg(msg: String) = intent {
        reduce {
            state.copy(currentTooltipMsg = msg)
        }
    }

    /**
     * 칼로리 변화 관찰
     */
    private fun observeCalories() = intent {
        userRepository.getTodayCalories()
            .collect { updatedCalories ->
                reduce {
                    state.copy(currentCalories = updatedCalories)
                }
                widgetManager.updateWidgetCalories(updatedCalories.toInt())
            }
    }

    companion object {
        private const val MAX_LEVEL = 5
        private const val MAX_PERCENTS = 100
    }
}