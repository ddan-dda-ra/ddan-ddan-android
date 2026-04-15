package com.ddanddan.ddanddan.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ddanddan.ddanddan.presentation.widget.WidgetManager
import com.ddanddan.ddanddan.util.AnalyticsEvent
import com.ddanddan.ddanddan.util.AnalyticsManager
import com.ddanddan.domain.ddanddanDataStore
import com.ddanddan.domain.repository.UserRepository
import com.ddanddan.domain.usecase.GetMainPetUseCase
import com.ddanddan.domain.usecase.GetNotificationAskedUseCase
import com.ddanddan.domain.usecase.GetUserInfoUseCase
import com.ddanddan.domain.usecase.PostFoodPetUseCase
import com.ddanddan.domain.usecase.PostMainPetUseCase
import com.ddanddan.domain.usecase.PostPlayPetUseCase
import com.ddanddan.domain.usecase.PostRandomPetUseCase
import com.ddanddan.domain.usecase.SetNotificationAskedUseCase
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
    private val savedStateHandle: SavedStateHandle,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getMainPetUseCase: GetMainPetUseCase,
    private val postPlayPetUseCase: PostPlayPetUseCase,
    private val postFoodPetUseCase: PostFoodPetUseCase,
    private val postRandomPetUseCase: PostRandomPetUseCase,
    private val postMainPetUseCase: PostMainPetUseCase,
    private val getNotificationAskedUseCase: GetNotificationAskedUseCase,
    private val setNotificationAskedUseCase: SetNotificationAskedUseCase,
    private val userRepository: UserRepository,
    private val ddanddanDataStore: ddanddanDataStore,
    private val widgetManager: WidgetManager,
    private val analyticsManager: AnalyticsManager
) : ContainerHost<HomeState, HomeSideEffect>, ViewModel() {
    override val container =
        container<HomeState, HomeSideEffect>(HomeState())

    private val isNewPet = savedStateHandle["isNewPet"] ?: false

    init {
        getNotificationAsked()
        getHomeInfo()
        observeCalories()

        animationEggCounterBadge()
    }

    fun logEvent(event: AnalyticsEvent) {
        analyticsManager.logEvent(event)
    }

    fun getHomeInfo() {
        getUserInfo()
        getMainPet()
    }

    private fun animationEggCounterBadge() = intent {
        if (isNewPet && !ddanddanDataStore.firstEggCounterBadge) {
            reduce { state.copy(firstEggCountBadge = true) }
            ddanddanDataStore.firstEggCounterBadge = true
        }
    }

    private fun getNotificationAsked() = intent {
        val isAsked = getNotificationAskedUseCase()
        if (!isAsked) reduce { state.copy(isShowGuideline = true) }
    }

    fun dismissGuideline() = intent {
        setNotificationAskedUseCase()
        reduce { state.copy(isShowGuideline = false )}
        postSideEffect(HomeSideEffect.AskNotification)
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
        reduce { state.copy(isLoading = false) }
    }
//
//    private fun postRandomPet() = intent {
//        postRandomPetUseCase()
//            .onSuccess {
//                reduce {
//                    state.copy(pet = it)
//                }
//                postMainPet(it.id)
//                postSideEffect(HomeSideEffect.NavigateNewPet(it.type))
//            }.onFailure {
//                postSideEffect(HomeSideEffect.SnackBarMsg("새로운 펫을 불러오는데 오류가 발생했습니다."))
//            }
//    }

    fun postPlayPet() = intent {
        state.pet?.let { pet ->
            if ((state.user?.toyQuantity ?: 0) > 0) {
                postPlayPetUseCase(pet.id)
                    .onSuccess {
                        if (it.pet.level > (state.pet?.level ?: 0)) {
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
//        reduce { state.copy(firstEggCountBadge = true) }
        state.pet?.let { pet ->
            if ((state.user?.foodQuantity ?: 0) > 0) {
                postFoodPetUseCase(pet.id)
                    .onSuccess {
                        if (it.pet.level > (state.pet?.level ?: 0)) {
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
                    state.copy(pet = it, newPet = null, isShowingEggAnimation = false)
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

    fun eggCountBadgeClick() = intent {
        val tickets = state.user?.tickets ?: 0
        if (tickets == 0) {
            reduce { state.copy(isShowEggZeroTooltip = !state.isShowEggZeroTooltip) }
        } else {
            // 알 뽑기 애니메이션 시작
            startEggAnimation()
        }
    }

    private fun startEggAnimation() = intent {
        reduce { state.copy(isShowingEggAnimation = true) }
    }

    fun onEggAnimationComplete() = intent {
        reduce { state.copy(isShowingEggAnimation = false, newPet = null) }
    }

    fun postRandomPet() = intent {
        if (state.newPet == null) {
            postRandomPetUseCase()
                .onSuccess { newPet ->
                    reduce {
                        state.copy(newPet = newPet)
                    }
                    getUserInfo()
                }.onFailure {
                    postSideEffect(HomeSideEffect.SnackBarMsg("새로운 펫을 불러오는데 오류가 발생했습니다."))
                }
        } else {
            state.newPet?.let {
                postMainPet(it.id)
            }
        }
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

    fun dismissCoachMark() = intent {
        reduce { state.copy(firstEggCountBadge = false) }
        delay(500)
        reduce { state.copy(isShowingEggAnimation = true) }
    }

    fun showPermissionDialog() = intent {
        reduce { state.copy(isShowPermissionDialog = true) }
    }

    fun dismissPermissionDialog() = intent {
        reduce { state.copy(isShowPermissionDialog = false) }
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