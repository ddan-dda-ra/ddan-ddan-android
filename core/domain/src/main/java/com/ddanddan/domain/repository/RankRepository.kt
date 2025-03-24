package com.ddanddan.domain.repository

import com.ddanddan.domain.entity.Rank

interface RankRepository {
    suspend fun getRanking(criteria: String): Pair<Rank, List<Rank>> // MyRank, 1~100
}