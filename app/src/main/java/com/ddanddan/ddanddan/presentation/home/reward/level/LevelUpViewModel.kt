package com.ddanddan.ddanddan.presentation.home.reward.level

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.GetLocalCatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LevelUpViewModel @Inject constructor(
    private val getLocalCatalogUseCase: GetLocalCatalogUseCase
) : ViewModel(), ContainerHost<LevelUpState, Unit> {
    override val container = container<LevelUpState, Unit>(LevelUpState())

    init {
        loadCatalog()
    }

    private fun loadCatalog() = intent {
        runCatching {
            getLocalCatalogUseCase()
        }.onSuccess { catalog ->
            reduce { state.copy(catalog = catalog) }
        }
    }
}
