package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.runtime.Immutable

@Immutable
data class SettingState(
    val settingItems: List<Pair<Int, Int?>> = listOf(
        Pair(com.ddanddan.base.R.string.setting_title_petbox, com.ddanddan.base.R.string.setting_description_text0),
        Pair(com.ddanddan.base.R.string.setting_title_edit_nickname, null),
        Pair(com.ddanddan.base.R.string.setting_title_edit_calories, null)
    ),
    val settingItemsBottom: List<Int> = listOf(
        com.ddanddan.base.R.string.setting_title_cs,
        com.ddanddan.base.R.string.setting_title_terms,
        com.ddanddan.base.R.string.setting_title_delete_account,
        com.ddanddan.base.R.string.setting_title_logout
    ),
    val signOutList: List<String> = listOf(
        "쓰지 않는 앱이에요",
        "오류가 생겨서 쓸 수 없어요",
        "개인정보가 불안해요",
        "앱 사용법을 모르겠어요",
        "기타"
    ),
    val nickName: String = "",
    val calorie: Int = 100,
    val selectedReasons: List<String> = emptyList(),
    val isCheckBoxChecked: Boolean = false,
    val needRefreshHomeScreen: Boolean = false,
    val isShowLogoutDialog: Boolean = false,
    val isPushAllowed: Boolean = false,
    val isValidNickname: Boolean = true,
    val isNickNameLengthOver: Boolean = false
)

sealed class SettingSideEffect {
    data class NavigatePopUp(val needRefreshHomeScreen: Boolean = false): SettingSideEffect()
    object EditNickname: SettingSideEffect()
    object EditTargetCalories : SettingSideEffect()
    object AgreeToTerms : SettingSideEffect()
    object DeleteAccount : SettingSideEffect()

    object SuccessChange : SettingSideEffect()
    data class NetworkError(val msg: String) : SettingSideEffect()

    object NavigateSignOutSecond : SettingSideEffect()
    object NavigateOnBoarding : SettingSideEffect()
    object NavigateLogin : SettingSideEffect()
    object NavigatePetCollection : SettingSideEffect()
}
