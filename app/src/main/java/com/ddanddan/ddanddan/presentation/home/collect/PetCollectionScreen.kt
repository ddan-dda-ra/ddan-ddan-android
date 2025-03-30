package com.ddanddan.ddanddan.presentation.home.collect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.base.R
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.domain.entity.Pet
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.component.DDanTransparentSnackBar
import com.ddanddan.ui.compose.component.showSnackbar
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun PetCollectionRoute(
    viewModel: CollectViewModel = hiltViewModel(),
    navigatePopUp: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onNavigateError: (Int?) -> Unit = {}
) {
    val petCollectionState by viewModel.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getPetList()
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PetCollectionSideEffect.NavigatePopUp -> {
                navigatePopUp()
            }

            is PetCollectionSideEffect.SuccessChangePet -> {
                onConfirmClick()
            }

            is PetCollectionSideEffect.NetworkError -> {
                onNavigateError(sideEffect.code)
            }

            is PetCollectionSideEffect.SnackBarMsg -> {
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        message = sideEffect.msg,
                        iconResId = sideEffect.icon,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    PetCollectionScreen(
        petCollectionState = petCollectionState,
        snackBarHostState = snackBarHostState,
        navigatePopUp = viewModel::onBackButtonClicked,
        onConfirmClick = viewModel::postMainPet,
        onSelectId = viewModel::changeSelectId,
        onSnackBarEvent = viewModel::showSnackBarEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetCollectionScreen(
    petCollectionState: PetCollectionState = PetCollectionState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navigatePopUp: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    onSelectId: (String) -> Unit = {},
    onSnackBarEvent: (String, Int) -> Unit = { _, _ -> },
) {
    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "펫 보관함",
                        style = DDanDDanTypo.current.HeadLine6,
                        color = DDanDDanColorPalette.current.color_text_headline_primary,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigatePopUp
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "뒤로 가기",
                            tint = DDanDDanColorPalette.current.color_icon_level01
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DDanDDanColorPalette.current.color_background,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            SetPetBtn(state = petCollectionState, text = stringResource(R.string.petcollection_button_text), onClick = onConfirmClick)
        },
        snackbarHost = {
            DDanTransparentSnackBar(snackBarHostState = snackBarHostState)
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize().padding(horizontal = 20.dp)
        ) {
            if (petCollectionState.pets.isNotEmpty()) {
                items(if (petCollectionState.pets.size <= 9) 9 else petCollectionState.pets.size) { index ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(DDanDDanColorPalette.current.elevation_color_elevation_level02)
                    ) {
                        PetItem(
                            pet = petCollectionState.pets.getOrNull(index),
                            selectedPetId = petCollectionState.selectedPetId,
                            onSelectId = onSelectId,
                            onOtherItemClick = onSnackBarEvent
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PetItem(
    pet: Pet?,
    selectedPetId: String,
    onSelectId: (String) -> Unit = {},
    onOtherItemClick: (String, Int) -> Unit = { _, _ -> }
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            if (pet != null) {
                onSelectId(pet.id)
            } else {
                onOtherItemClick("새로운 펫을 준비중이에요", R.drawable.ic_clock_fill)
            }
        }) {
        Image(
            painter = painterResource(id = pet?.type.toAnimal(pet?.level)),
            contentDescription = "Pet",
            modifier = Modifier
                .fillMaxSize()
                .padding(if (pet != null) 8.dp else 24.dp)
        )
        if (pet?.id == selectedPetId) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        4.dp,
                        DDanDDanColorPalette.current.color_outline_level01_active,
                        RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

@Preview
@Composable
fun SetPetBtn(
    state: PetCollectionState = PetCollectionState(),
    text: String = "",
    onClick: () -> Unit = {}
) {
    val buttonColors =
        if (state.selectedPetId != state.mainPetId) DDanDDanColorPalette.current.color_button_active else DDanDDanColorPalette.current.color_button_disabled
    val textColors =
        if (state.selectedPetId != state.mainPetId) DDanDDanColorPalette.current.color_text_button_primary_default else DDanDDanColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(0.dp),
        enabled = state.selectedPetId != state.mainPetId,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors,
            disabledContainerColor = buttonColors
        ),
    ) {
        androidx.compose.material.Text(
            text = text,
            style = DDanDDanTypo.current.HeadLine6,
            color = textColors
        )
    }
}