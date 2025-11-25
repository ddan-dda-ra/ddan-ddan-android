package com.ddanddan.ddanddan.presentation.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.friends.fireworks.FireworkEffect
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.ddanddan.util.toLottie
import com.ddanddan.ddanddan.util.toRectBackgroundImage
import com.ddanddan.domain.entity.UserDetail
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.NeoDgm
import com.ddanddan.ui.compose.component.DDanMarginHorizontalSpacer
import com.ddanddan.ui.compose.component.DDanMarginVerticalSpacer
import com.ddanddan.ui.ext.noRippleClickable

/**
 * 초대링크로 다이얼로그 띄워야 하는 상황일 떄 (내 링크면 띄우지 말 것)
 * ProfileDialog(
 *      userDetail = ...,
 *      onClickCancel = ...,
 *      isFromInvitation = true
 *      onAddFriend = ...,
 * )
 * 이렇게만 사용
 */

@Composable
fun ProfileDialog(
    friendsState: FriendsState? = null,
    userDetail: UserDetail,
    onClickCheers: (String) -> Unit = {},
    onClickCancel: () -> Unit = {},
    isMyself: Boolean = false,
    isFromInvitation: Boolean = false,
    onAddFriend: (String) -> Unit = {},
    showFireworks: Boolean = false
    ) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            userDetail.mainPet.type.toLottie(
                userDetail.mainPet.level,
                isPlayAndEatLottie = false
            )
        )
    )

    Dialog(
        onDismissRequest = { onClickCancel() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card (
                modifier = Modifier
                    .width(296.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = DDanDDanColorPalette.current.elevation_color_elevation_level01
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = userDetail.mainPet.type.toRectBackgroundImage()),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        ConstraintLayout(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val (label, close, pet, fireworks) = createRefs()

                            Image(
                                modifier = Modifier.constrainAs(close) {
                                    top.linkTo(parent.top, margin = 16.dp)
                                    end.linkTo(parent.end, margin = 16.dp)
                                }
                                    .noRippleClickable { onClickCancel() },
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null
                            )

                            if (userDetail.isFriend || isMyself) {
                                Box(
                                    modifier = Modifier.constrainAs(label) {
                                        top.linkTo(parent.top, margin = (16.5).dp)
                                        start.linkTo(parent.start, margin = 16.dp)
                                    }
                                        .wrapContentSize()
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(DDanDDanColorPalette.current.elevation_color_elevation_level04)
                                ) {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                        text = if (isMyself) "나" else "친구",
                                        color = DDanDDanColorPalette.current.color_text_headline_primary,
                                        style = DDanDDanTypo.current.HeadLine7,
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.constrainAs(pet) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                }
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                LottieAnimation(
                                    composition = composition,
                                    modifier = Modifier
                                        .size(100.dp),
                                    iterations = LottieConstants.IterateForever
                                )

                                Spacer(modifier = Modifier.weight(0.262f))
                            }

                            if (showFireworks) {
                                FireworkEffect(
                                    modifier = Modifier.constrainAs(fireworks) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    }
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 20.dp, vertical = 28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = userDetail.name,
                                style = DDanDDanTypo.current.NeoDgm22,
                                color = DDanDDanColorPalette.current.color_text_headline_primary
                            )
                            DDanMarginHorizontalSpacer(8)
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(DDanDDanColorPalette.current.elevation_color_elevation_level02)
                            ) {
                                Text(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    text = "LV.${userDetail.mainPet.level}",
                                    style = DDanDDanTypo.current.NeoDgm16,
                                    color = DDanDDanColorPalette.current.Gray950
                                )
                            }
                        }
                        DDanMarginVerticalSpacer(13)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(34.dp))
                                .background(DDanDDanColorPalette.current.elevation_color_elevation_level03)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.ic_flame),
                                    contentDescription = null
                                )
                                DDanMarginHorizontalSpacer(2)
                                Text(
                                    text = "받은 응원",
                                    style = DDanDDanTypo.current.Body1,
                                    color = DDanDDanColorPalette.current.color_text_headline_teritary
                                )
                                DDanMarginHorizontalSpacer(8)
                                Text(
                                    modifier = Modifier.padding(top = 4.dp),
                                    text = userDetail.monthlyReceivedCheerCount.toString(),
                                    style = DDanDDanTypo.current.NeoDgm24,
                                    color = DDanDDanColorPalette.current.color_text_headline_primary
                                )
                            }
                        }
                        DDanMarginVerticalSpacer(20)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = DDanDDanColorPalette.current.color_divider_level03
                        )
                        DDanMarginVerticalSpacer(20)
                        Text(
                            text = "오늘 소모 칼로리",
                            style = DDanDDanTypo.current.Body1,
                            color = DDanDDanColorPalette.current.color_text_headline_teritary
                        )
                        DDanMarginVerticalSpacer(3)
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = userDetail.todayCalorie.toString(),
                                fontFamily = NeoDgm,
                                fontSize = 20.sp,
                                fontWeight = FontWeight(400),
                                color = DDanDDanColorPalette.current.color_text_headline_primary
                            )
                            DDanMarginHorizontalSpacer(2)
                            Text(
                                text = "kcal",
                                fontFamily = NeoDgm,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(400),
                                color = DDanDDanColorPalette.current.color_text_headline_primary
                            )
                        }
                    }
                }
            }
            if (!isMyself && userDetail.isFriend && friendsState?.isCheeredToday == false) {
                DDanMarginVerticalSpacer(20)
                Button(
                    onClick = {
                        if (!isFromInvitation) onClickCheers(userDetail.id)
                        else onAddFriend(userDetail.id)
                    },
                    modifier = Modifier
                        .wrapContentSize(),
                    contentPadding = PaddingValues(vertical = 14.dp, horizontal = 40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DDanDDanColorPalette.current.color_button_default02,
                        contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
                    ),
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = if (!isFromInvitation) "응원해요" else "친구하기",
                        style = DDanDDanTypo.current.HeadLine6,
                        color = DDanDDanColorPalette.current.color_text_button_primary_default
                    )
                }
            }
        }
    }
}