package com.ddanddan.ddanddan.presentation.rank

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.GetRankingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val getRankingUseCase: GetRankingUseCase
) : ViewModel(), ContainerHost<RankState, RankSideEffect> {

    override val container =
        container<RankState, RankSideEffect>(RankState())

    fun setCriteriaTab(criteria: RankCriteria) = intent {
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
                reduce {
                    state.copy(
                        myRank = myRank,
                        goldRank = others.getOrNull(0),
                        silverRank = others.getOrNull(1),
                        bronzeRank = others.getOrNull(2),
                        otherRanking = others.drop(3)
                    )
                }
            }
            .onFailure {
                postSideEffect(RankSideEffect.NetworkError("정보를 가져오는데 실패했습니다"))
            }
    }
}