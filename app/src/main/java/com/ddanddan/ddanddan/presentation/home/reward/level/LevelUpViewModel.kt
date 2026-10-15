package com.ddanddan.ddanddan.presentation.home.reward.level

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.SyncPetCatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LevelUpViewModel @Inject constructor(
    private val syncPetCatalogUseCase: SyncPetCatalogUseCase
) : ViewModel(), ContainerHost<LevelUpState, Unit> {
    override val container = container<LevelUpState, Unit>(LevelUpState())

    init {
        syncCatalog()
    }

    private fun syncCatalog() = intent {
        runCatching {
            syncPetCatalogUseCase()
        }.onSuccess { catalog ->
            reduce { state.copy(catalog = catalog) }
        }
    }
}
