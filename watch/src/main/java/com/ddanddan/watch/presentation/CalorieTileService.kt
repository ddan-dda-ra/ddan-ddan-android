package com.ddanddan.watch.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ColorBuilders
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.StateBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.ddanddan.domain.repository.PassiveDataRepository
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import com.google.android.horologist.tiles.images.drawableResToImageResource
import kotlinx.coroutines.flow.first
import kotlin.math.roundToInt

//@ExperimentalHorologistApi
//class CalorieTileService : SuspendingTileService() {
//
//    private lateinit var passiveDataRepository: PassiveDataRepository
//    private val goalCalories = 2000.0  // 목표 칼로리 설정
//    private var currentCalories = 0.0  // 현재 칼로리 데이터
//    private var isShowingImage = false  // 기본적으로 텍스트를 표시
//
//    override fun onCreate() {
//        super.onCreate()
//        passiveDataRepository = PassiveDataRepository(applicationContext)
//    }
//
//    // 리소스 요청 처리 (이미지 리소스 제공)
//    override suspend fun resourcesRequest(
//        requestParams: RequestBuilders.ResourcesRequest
//    ): ResourceBuilders.Resources {
//        // 이미지 리소스 설정
//        val imageResource = drawableResToImageResource(com.google.android.horologist.compose.tools.R.drawable.sample_image)
//
//        return ResourceBuilders.Resources.Builder()
//            .setVersion(RESOURCES_VERSION)
//            .addIdToImageMapping(IMAGE_RESOURCE_ID, imageResource)  // IMAGE_RESOURCE_ID와 매핑
//            .build()
//    }
//
//    override suspend fun tileRequest(
//        requestParams: RequestBuilders.TileRequest
//    ): TileBuilders.Tile {
//        // DataStore에서 최신 칼로리 데이터를 가져옴
//        currentCalories = passiveDataRepository.latestCalories.first()
//        val progressState = (currentCalories / goalCalories).coerceIn(0.0, 1.0).toFloat()
//
//        // 클릭 이벤트 처리: 이미지와 텍스트 전환
//        if (requestParams.currentState.lastClickableId == TOGGLE_CLICK_ID) {
//            isShowingImage = !isShowingImage  // 클릭할 때마다 이미지와 텍스트 전환
//        }
//
//        // 상태에 따라 이미지 또는 텍스트를 표시
//        val centralContent = if (isShowingImage) {
//            // 이미지가 보이는 상태일 때
//            LayoutElementBuilders.Image.Builder()
//                .setResourceId(IMAGE_RESOURCE_ID)  // 이미지 리소스를 설정
//                .build()
//        } else {
//            // 칼로리 숫자와 단위를 나란히 배치 (Row 레이아웃 사용)
//            LayoutElementBuilders.Row.Builder()
//                .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)  // 세로로 중앙 정렬
//                .addContent(
//                    LayoutElementBuilders.Text.Builder()
//                        .setText("${currentCalories.roundToInt()}")  // 칼로리 숫자 부분
//                        .setFontStyle(
//                            LayoutElementBuilders.FontStyle.Builder()
//                                .setSize(DimensionBuilders.sp(40f))  // 숫자 크기 40sp
//                                .setColor(ColorBuilders.argb(Color.White.toArgb()))
//                                .build()
//                        )
//                        .build()
//                )
//                .addContent(
//                    LayoutElementBuilders.Spacer.Builder()
//                        .setWidth(DimensionBuilders.dp(4f))  // 칼로리와 kcal 사이에 4dp 간격
//                        .build()
//                )
//                .addContent(
//                    LayoutElementBuilders.Text.Builder()
//                        .setText("kcal")  // 단위 부분
//                        .setFontStyle(
//                            LayoutElementBuilders.FontStyle.Builder()
//                                .setSize(DimensionBuilders.sp(16f))  // 단위 크기 16sp
//                                .setColor(ColorBuilders.argb(Color.White.toArgb()))
//                                .build()
//                        )
//                        .build()
//                )
//                .build()
//        }
//
//        // 클릭 이벤트를 처리하여 상태를 업데이트
//        val clickable = ModifiersBuilders.Clickable.Builder()
//            .setId(TOGGLE_CLICK_ID)  // 클릭 ID 설정
//            .setOnClick(
//                ActionBuilders.LoadAction.Builder()
//                    .setRequestState(
//                        StateBuilders.State.Builder().build()
//                    )
//                    .build()
//            )
//            .build()
//
//        // 중앙 콘텐츠와 프로그레스바를 고정 레이아웃으로 구성
//        val tileLayout = LayoutElementBuilders.Box.Builder()
//            .setVerticalAlignment(LayoutElementBuilders.VERTICAL_ALIGN_CENTER)
//            .setWidth(DimensionBuilders.expand())
//            .setHeight(DimensionBuilders.expand())
//            .addContent(createCircularProgressBar(progressState))  // 원형 프로그레스바
//            .addContent(
//                LayoutElementBuilders.Box.Builder()
//                    .setWidth(DimensionBuilders.dp(60f))
//                    .setHeight(DimensionBuilders.dp(60f))
//                    .addContent(centralContent)  // 상태에 따라 텍스트 또는 이미지
//                    .setModifiers(
//                        ModifiersBuilders.Modifiers.Builder()
//                            .setClickable(clickable)  // 클릭 가능하도록 설정
//                            .build()
//                    )
//                    .build()
//            )
//            .build()
//
//        // 단일 타임라인 생성
//        val singleTileTimeline = TimelineBuilders.Timeline.Builder()
//            .addTimelineEntry(
//                TimelineBuilders.TimelineEntry.Builder()
//                    .setLayout(
//                        LayoutElementBuilders.Layout.Builder()
//                            .setRoot(tileLayout)
//                            .build()
//                    )
//                    .build()
//            )
//            .build()
//
//        return TileBuilders.Tile.Builder()
//            .setResourcesVersion(RESOURCES_VERSION)
//            .setTileTimeline(singleTileTimeline)
//            .build()
//    }
//
//    // 원형 프로그레스바 생성 함수
//    private fun createCircularProgressBar(progressState: Float): LayoutElementBuilders.LayoutElement {
//        val progress = progressState * 360f  // 360도 기준으로 진행 상태 설정
//
//        return LayoutElementBuilders.Arc.Builder()
//            .setAnchorType(LayoutElementBuilders.ARC_ANCHOR_START)
//            .addContent(
//                LayoutElementBuilders.ArcLine.Builder()
//                    .setColor(ColorBuilders.argb(Color.Gray.toArgb()))
//                    .setThickness(DimensionBuilders.dp(12f))
//                    .setLength(DimensionBuilders.degrees(360.0f))
//                    .build()
//            )
//            .addContent(
//                LayoutElementBuilders.ArcLine.Builder()
//                    .setColor(ColorBuilders.argb(Color.Yellow.toArgb()))
//                    .setThickness(DimensionBuilders.dp(12f))
//                    .setLength(DimensionBuilders.degrees(progress))
//                    .build()
//            )
//            .build()
//    }
//
//    companion object {
//        private const val TOGGLE_CLICK_ID = "toggle"  // 클릭 ID 정의
//        private const val IMAGE_RESOURCE_ID = "image_id"  // 이미지 리소스 ID
//        private const val RESOURCES_VERSION = "1"  // 리소스 버전 증가
//    }
//}
