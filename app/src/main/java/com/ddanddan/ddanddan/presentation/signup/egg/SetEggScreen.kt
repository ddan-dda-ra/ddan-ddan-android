package com.ddanddan.ddanddan.presentation.signup.egg

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.signup.SignUpSideEffect
import com.ddanddan.ddanddan.presentation.signup.SignUpState
import com.ddanddan.ddanddan.presentation.signup.SignUpViewModel
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SetEggRoute(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    onNavigateFinish: () -> Unit,
    onNavigateGoal: () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val signUpState by signUpViewModel.collectAsState()

    signUpViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.SuccessSignUp -> {
                onNavigateFinish()
            }
            is SignUpSideEffect.NavigateTargetCalories -> {
                onNavigateGoal()
            }
            is SignUpSideEffect.NetworkError -> {
                snackBarHostState.showSnackbar(sideEffect.msg)
            }
            else -> {}
        }
    }

    SetEggScreen(
        signUpState = signUpState,
        onTypeChange = signUpViewModel::setPetType,
        onNextBtnClick = signUpViewModel::putUserInfo
    )
}

@Composable
@Preview
fun SetEggScreen(
    signUpState: SignUpState = SignUpState(),
    onTypeChange: (PetTypeEnum) -> Unit = {},
    onNextBtnClick: () -> Unit = {}
) {
    val eggMap = mapOf(
        PetTypeEnum.CAT to R.drawable.ic_egg_pink,
        PetTypeEnum.HAMSTER to R.drawable.ic_egg_green,
        PetTypeEnum.DOG to R.drawable.ic_egg_purple,
        PetTypeEnum.PENGUIN to R.drawable.ic_egg_blue
    )

    @Composable
    fun EggItem(petTypeEnum: PetTypeEnum) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(
                    color = DDanDDanColorPalette.current.elevation_color_elevation_level01,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    3.dp,
                    if (signUpState.petType == petTypeEnum) DDanDDanColorPalette.current.color_outline_level01_active
                    else DDanDDanColorPalette.current.elevation_color_elevation_level01,
                    RoundedCornerShape(8.dp)
                )
                .clickable { onTypeChange(petTypeEnum) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = eggMap[petTypeEnum] ?: R.drawable.ic_egg_pink),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 34.29.dp, top = 40.dp, end = 34.29.dp, bottom = 24.29.dp)
            )
        }
    }

    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            DDanMarginVerticalSpacer(size = 32)
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(R.string.signup_egg_title),
                style = DDanDDanTypo.current.HeadLine3,
                fontFamily = NeoDgm,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            DDanMarginVerticalSpacer(size = 75)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .width(270.dp)
                        .wrapContentHeight()  // 세로는 콘텐츠 크기에 맞게 자동 조정
                ) {
                    items(eggMap.keys.toList()) { petType ->
                        EggItem(petType)
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            SetEggBtn(
                text = stringResource(R.string.signup_egg_button_text),
                petTypeEnum = signUpState.petType,
                onClick = onNextBtnClick
            )
            DDanMarginVerticalSpacer(size = 20)
        }
    }
}

@Composable
fun SetEggBtn(
    text: String,
    petTypeEnum: PetTypeEnum?,
    onClick: () -> Unit = {}
) {
    val isValid = petTypeEnum != null

    val buttonColors =
        if (isValid) DDanDDanColorPalette.current.color_button_active else DDanDDanColorPalette.current.color_button_disabled
    val textColors =
        if (isValid) DDanDDanColorPalette.current.color_text_button_primary_default else DDanDDanColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = isValid,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors,
            disabledContainerColor = buttonColors
        ),
        shape = RoundedCornerShape(0.dp),
    ) {
        androidx.compose.material.Text(
            text = text,
            style = DDanDDanTypo.current.HeadLine6,
            color = textColors
        )
    }
}