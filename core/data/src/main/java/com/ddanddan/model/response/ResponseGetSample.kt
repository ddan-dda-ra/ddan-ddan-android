package com.ddanddan.model.response

import com.ddanddan.domain.entity.SampleEntity
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetSample(
    val sample: String,
)

//MapperSample
fun ResponseGetSample.toData(): SampleEntity {
    return SampleEntity(sample = sample)
}