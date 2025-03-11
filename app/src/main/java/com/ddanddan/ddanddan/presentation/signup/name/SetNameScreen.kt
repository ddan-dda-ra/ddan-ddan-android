package com.ddanddan.ddanddan.presentation.signup.name

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.signup.SignUpSideEffect
import com.ddanddan.ddanddan.presentation.signup.SignUpState
import com.ddanddan.ddanddan.presentation.signup.SignUpViewModel
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SetNameRoute(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    onNavigateTargetCalories: () -> Unit,
    onNavigateTerms: () -> Unit
) {
    val signUpState by signUpViewModel.collectAsState()

    signUpViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.NavigateTerms -> {
                onNavigateTerms()
            }
            is SignUpSideEffect.NavigateTargetCalories -> {
                onNavigateTargetCalories()
            }
            else -> {}
        }
    }

    SetNameScreen(
        signUpState = signUpState,
        onValueChange = signUpViewModel::setNickname,
        onNextBtnClick = onNavigateTargetCalories
    )
}

@Composable
@Preview
fun SetNameScreen(
    signUpState: SignUpState = SignUpState(),
    onValueChange: (String) -> Unit = {},
    onNextBtnClick: () -> Unit = {}
) {
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
                text = stringResource(id = R.string.editname_screen_title),
                style = DDanDDanTypo.current.HeadLine3,
                fontFamily = NeoDgm,
                color = DDanDDanColorPalette.current.color_text_headline_primary
            )
            DDanMarginVerticalSpacer(size = 32)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .wrapContentHeight()
                    .background(color = DDanDDanColorPalette.current.color_background),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = R.string.editname_textfield_text),
                    style = DDanDDanTypo.current.Body2,
                    color = DDanDDanColorPalette.current.color_text_body_quaternary
                )
                DDanMarginVerticalSpacer(size = 8)
                SetNameField(
                    signUpState = signUpState,
                    nickName = signUpState.nickname,
                    onValueChange = onValueChange
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            SetCardBtn(
                signUpState = signUpState,
                text = stringResource(R.string.signup_name_button_text),
                onClick = onNextBtnClick
            )
            DDanMarginVerticalSpacer(size = 20)
        }
    }
}

@Composable
fun SetNameField(
    signUpState: SignUpState = SignUpState(),
    nickName: String,
    onValueChange: (String) -> Unit = {},
) {
    val maxChar = 10

    Column {
        OutlinedTextField(
            value = nickName,
            placeholder = { Text(stringResource(R.string.signup_name_placeholder)) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(1.dp, 
                    color = if (signUpState.isValidNickname) DDanDDanColorPalette.current.elevation_color_elevation_level01
                        else DDanDDanColorPalette.current.color_outline_level01_error,
                    shape = RoundedCornerShape(4.dp)
                ),
            textStyle = DDanDDanTypo.current.Body1,
            onValueChange = { newText ->
                if (newText.length <= maxChar) {
                    onValueChange(newText)
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = DDanDDanColorPalette.current.color_text_body_primary,
                focusedBorderColor = DDanDDanColorPalette.current.elevation_color_elevation_level01,
                unfocusedBorderColor = DDanDDanColorPalette.current.elevation_color_elevation_level01,
                unfocusedLabelColor = DDanDDanColorPalette.current.color_text_body_quinary,
                focusedLabelColor = DDanDDanColorPalette.current.color_text_body_quinary,
                backgroundColor = DDanDDanColorPalette.current.elevation_color_elevation_level01,
                cursorColor = DDanDDanColorPalette.current.color_icon_level02_active,
                placeholderColor = DDanDDanColorPalette.current.color_text_body_quinary,
            ),
        )
        if (!signUpState.isValidNickname) {
            DDanMarginVerticalSpacer(8)
            Text(
                text = stringResource(R.string.signup_name_error),
                style = DDanDDanTypo.current.Caption2,
                color = DDanDDanColorPalette.current.Error300
            )
        }
    }
}

@Composable
fun SetCardBtn(
    signUpState: SignUpState = SignUpState(),
    text: String,
    onClick: () -> Unit = {}
) {
    val buttonColors =
        if (signUpState.isValidNickname) DDanDDanColorPalette.current.color_button_active else DDanDDanColorPalette.current.color_button_disabled
    val textColors =
        if (signUpState.isValidNickname) DDanDDanColorPalette.current.color_text_button_primary_default else DDanDDanColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(0.dp),
        enabled = signUpState.isValidNickname,
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors,
            disabledContainerColor = buttonColors
        ),
    ) {
        Text(
            text = text,
            style = DDanDDanTypo.current.HeadLine6,
            color = textColors
        )
    }
}