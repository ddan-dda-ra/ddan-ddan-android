package com.ddanddan.ddanddan.presentation.rank

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.GetRankingUseCase
import com.ddanddan.domain.usecase.GetUserDetailUseCase
import com.ddanddan.domain.usecase.PatchDailyCaloriesUseCase
import com.ddanddan.domain.usecase.PostCheersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val getRankingUseCase: GetRankingUseCase,
    private val patchDailyCaloriesUseCase: PatchDailyCaloriesUseCase,
    private val postCheersUseCase: PostCheersUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel(), ContainerHost<RankState, RankSideEffect> {

    override val container =
        container<RankState, RankSideEffect>(RankState())

    fun setCriteriaTab(criteria: RankCriteria) = intent {
        dismissToolTip()
        reduce {
            state.copy(
                criteria = criteria
            )
        }
        getRanking()
    }

    fun onBackButtonClicked() = intent {
        postSideEffect(RankSideEffect.NavigatePopUp)
    }

    private fun getRanking() = intent {
        getRankingUseCase(state.criteria.toString())
            .onSuccess { (myRank, others) ->
                val myIdx = others.indexOfFirst { it.userId == myRank.userId }
                reduce {
                    state.copy(
                        myRank = myRank,
                        goldRank = others.getOrNull(0),
                        silverRank = others.getOrNull(1),
                        bronzeRank = others.getOrNull(2),
                        otherRanking = others.drop(3),
                        myIdx = myIdx
                    )
                }
            }
            .onFailure {
                if (!state.isPatchAttempted) {
                    postSideEffect(RankSideEffect.UserDataEmpty)
                } else {
                    postSideEffect(RankSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
                }
            }
    }

    fun patchDailyCalories() = intent {
        patchDailyCaloriesUseCase(0)
            .onSuccess {
                reduce { state.copy(isPatchAttempted = true) }
                getRanking()
            }
            .onFailure {
                postSideEffect(RankSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }

    fun showToolTip() = intent {
        reduce {
            state.copy(
                showToolTip = true
            )
        }
    }

    fun dismissToolTip() = intent {
        reduce {
            state.copy(
                showToolTip = false
            )
        }
    }

    fun goToMyRanking() = intent {
        postSideEffect(RankSideEffect.GoToMyRanking(state.myRank?.rank ?: 0))
    }

    fun showSnackBarEvent(msg: String, icon: Int) = intent {
        postSideEffect(RankSideEffect.SnackBarMsg(msg, icon))
    }

    fun getUserDetail(uId: String?) = intent {
        if (uId == null) return@intent
        getUserDetailUseCase(uId)
            .onSuccess {
                reduce {
                    state.copy(
                        chosenUserDetail = it,
                        isShowProfileDialog = true
                    )
                }
            }
            .onFailure {
                postSideEffect(RankSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }

    fun dismissDetailDialog() = intent {
        reduce {
            state.copy(
                isShowProfileDialog = false,
                showFireworks = false
            )
        }
    }

    fun postCheers(uId: String) = intent {
        postCheersUseCase(uId)
            .onSuccess {
                // 불꽃 애니메이션
                reduce {
                    state.copy(showFireworks = true)
                }
            }
            .onFailure {
                postSideEffect(RankSideEffect.FailCheers)
            }
    }
}