package com.ddanddan.ddanddan.presentation.friends

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.GetLocalCatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddedFriendViewModel @Inject constructor(
    private val getLocalCatalogUseCase: GetLocalCatalogUseCase
) : ViewModel(), ContainerHost<AddedFriendState, Unit> {
    override val container = container<AddedFriendState, Unit>(AddedFriendState())

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
