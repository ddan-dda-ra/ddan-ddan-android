package com.ddanddan.data.repository

import com.ddanddan.data.datasource.remote.RemoteRankDataSource
import com.ddanddan.domain.entity.Rank
import com.ddanddan.domain.repository.RankRepository
import javax.inject.Inject

class RankRepositoryImpl @Inject constructor(
    private val rankDataSource: RemoteRankDataSource
) : RankRepository {
    override suspend fun getRanking(criteria: String): Pair<Rank, List<Rank>> {
        val result = rankDataSource.getRanking(criteria)
        return Pair(result.myRanking.toRank(), result.ranking.map { it.toRank() })
    }
}