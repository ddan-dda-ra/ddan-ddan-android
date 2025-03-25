package com.ddanddan.domain.usecase

import com.ddanddan.domain.entity.Rank
import com.ddanddan.domain.repository.RankRepository
import javax.inject.Inject

class GetRankingUseCase @Inject constructor(
    private val rankRepository: RankRepository
) {
    suspend operator fun invoke(criteria: String): Result<Pair<Rank, List<Rank>>> {
        return runCatching {
            rankRepository.getRanking(criteria)
        }
    }
}