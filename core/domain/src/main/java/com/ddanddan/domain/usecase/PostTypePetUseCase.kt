package com.ddanddan.domain.usecase

import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.domain.repository.PetRepository
import javax.inject.Inject

class PostTypePetUseCase @Inject constructor(
    private val petRepository: PetRepository
){
    suspend operator fun invoke(petTypeEnum: PetTypeEnum) = runCatching {
        petRepository.postTypePet(petTypeEnum)
    }
}