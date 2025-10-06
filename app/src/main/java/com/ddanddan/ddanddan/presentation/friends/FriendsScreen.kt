package com.ddanddan.ddanddan.presentation.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.ddanddan.util.toColor
import com.ddanddan.domain.enums.PetTypeEnum
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.Pretendard
import com.ddanddan.ui.compose.component.DDanMarginHorizontalSpacer
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.compose.component.DDanSnackBar
import com.ddanddan.ui.compose.component.DDanTwoButtonDialog
import com.ddanddan.ui.compose.component.showSnackbar
import com.ddanddan.ui.ext.noRippleClickable
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun FriendsRoute(
    friendsViewModel: FriendsViewModel = hiltViewModel()
) {
    val friendsState by friendsViewModel.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        friendsViewModel.getFriendsList()
        friendsViewModel.getInviteCode()
    }

    friendsViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FriendsSideEffect.NetworkError -> {}
            is FriendsSideEffect.RefreshList -> friendsViewModel.getFriendsList()
            is FriendsSideEffect.CopyInviteLink -> {
                clipboardManager.setText(AnnotatedString(friendsState.myInviteLink ?: ""))
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        message = "친구 추가 링크를 복사했어요.",
                        iconResId = R.drawable.icon_radio_check_on,
                        duration = SnackbarDuration.Short,
                        bottomPadding = 88
                    )
                }
            }
        }
    }

    FriendsScreen(
        friendsState = friendsState,
        snackBarHostState = snackBarHostState,
        onCopyInviteLink = friendsViewModel::copyInviteCode,
        onDialogDismiss = friendsViewModel::dismissDialog,
        onDialogConfirm = friendsViewModel::deleteFriend,
        onDeleteClick = friendsViewModel::chooseDeleteFriend
    )
}

@Preview
@Composable
fun FriendsScreen(
    friendsState: FriendsState = FriendsState(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onCopyInviteLink: () -> Unit = { },
    onDialogDismiss: () -> Unit = {},
    onDialogConfirm: () -> Unit = {},
    onDeleteClick: (String) -> Unit = {}
) {
    Scaffold(
        snackbarHost = {
            DDanSnackBar(snackBarHostState = snackBarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(DDanDDanColorPalette.current.color_background)
        ) {
            FriendsTopBar(
                copyFriendsLink = { onCopyInviteLink() }
            )
            DDanMarginVerticalSpacer(9)
            FriendsListView(
                friendsState = friendsState,
                onDelete = onDeleteClick
            )
            MyselfBottomSheet(friendsState = friendsState)
        }
    }
    if (friendsState.isShowDeleteDialog) {
        DDanTwoButtonDialog(
            title = "정말 삭제하시겠어요?",
            content = "친구가 삭제돼요",
            cancelText = "취소",
            confirmText = "삭제하기",
            onClickCancel = onDialogDismiss,
            onClickConfirm = onDialogConfirm
        )
    }
}

@Composable
private fun FriendsListView(
    friendsState: FriendsState = FriendsState(),
    listState: LazyListState = rememberLazyListState(),
    onDelete: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        state = listState
    ) {
        items(friendsState.friends.size) { idx ->
            FriendRow(
                nickname = friendsState.friends[idx].name,
                mainPetType = friendsState.friends[idx].mainPetType,
                petLevel = friendsState.friends[idx].petLevel,
                onDelete = { onDelete(friendsState.friends[idx].id) }
            )
        }
    }
}

@Composable
private fun MyselfBottomSheet(
    friendsState: FriendsState = FriendsState(),
    onClick: () -> Unit = { }
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(DDanDDanColorPalette.current.elevation_color_elevation_level02)
            .noRippleClickable { onClick() }
    ) {
        if (friendsState.myProfile != null) {
            FriendRow(
                nickname = friendsState.myProfile.name,
                mainPetType = friendsState.myProfile.mainPetType,
                petLevel = friendsState.myProfile.petLevel,
                isBottomSheet = true
            )
        }
    }
}

@Preview
@Composable
fun FriendsTopBar(
    copyFriendsLink: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "친구 목록",
            style = DDanDDanTypo.current.NeoDgm24,
            color = DDanDDanColorPalette.current.color_text_headline_secondary,
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(4.dp))
                .background(DDanDDanColorPalette.current.elevation_color_elevation_level03)
                .noRippleClickable { copyFriendsLink() }
        ) {
            Text(
                text = "친구 추가",
                style = DDanDDanTypo.current.SubTitle1,
                fontFamily = Pretendard,
                color = DDanDDanColorPalette.current.color_text_caption_primary_default,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview
@Composable
fun FriendRow(
    nickname: String? = "이름입니뎅",
    mainPetType: PetTypeEnum? = PetTypeEnum.CAT,
    petLevel: Int? = 1,
    onDelete: () -> Unit = {},
    isBottomSheet: Boolean = false
) {
    Row(
        modifier = if (isBottomSheet) Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                else Modifier.padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(mainPetType.toColor()),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = mainPetType.toAnimal(petLevel)),
                contentDescription = "펫 이미지",
                modifier = Modifier.size(42.dp)
            )
        }
        DDanMarginHorizontalSpacer(12)
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = nickname?:"",
            style = DDanDDanTypo.current.Body1,
            fontFamily = Pretendard,
            color = if (isBottomSheet) DDanDDanColorPalette.current.color_text_body_secondary else DDanDDanColorPalette.current.color_text_body_primary
        )
        if (isBottomSheet) {
            DDanMarginHorizontalSpacer(8)
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .clip(CircleShape)
                    .background(DDanDDanColorPalette.current.color_icon_level01),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "나",
                    style = DDanDDanTypo.current.Caption1,
                    fontFamily = Pretendard,
                    color = DDanDDanColorPalette.current.color_text_body_quinary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.wrapContentWidth().padding(horizontal = 6.dp)
                )
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .background(DDanDDanColorPalette.current.color_button_default01)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "삭제",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(6.dp)
                )
            }
        }
    }

}