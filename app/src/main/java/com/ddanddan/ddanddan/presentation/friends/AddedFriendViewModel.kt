package com.ddanddan.ddanddan.presentation.friends

import androidx.lifecycle.ViewModel
import com.ddanddan.domain.usecase.SyncPetCatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddedFriendViewModel @Inject constructor(
    private val syncPetCatalogUseCase: SyncPetCatalogUseCase
) : ViewModel(), ContainerHost<AddedFriendState, Unit> {
    override val container = container<AddedFriendState, Unit>(AddedFriendState())

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
