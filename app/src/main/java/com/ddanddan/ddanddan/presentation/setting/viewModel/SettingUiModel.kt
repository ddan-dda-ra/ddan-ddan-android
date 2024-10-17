package com.ddanddan.ddanddan.presentation.setting.viewModel

data class SettingUiModel(
    val title: String,
    val url: String = "",
    val onClick:() -> Unit = {}
)

val SignOutList = listOf(
    "쓰지 않는 앱이에요",
    "오류가 생겨서 쓸 수 없어요",
    "개인정보가 불안해요",
    "앱 사용법을 모르겠어요",
    "기타"
)