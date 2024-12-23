package com.ddanddan.ddanddan.presentation

import android.util.Log
import com.ddanddan.ddanddan.presentation.setting.viewModel.SettingViewModel
import com.ddanddan.domain.repository.UserRepository
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

//todo - 추후 적절한 레이어로 분리 예정
@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var settingViewModel: SettingViewModel

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { event ->
            if (event.type == DataEvent.TYPE_CHANGED) {
                val dataItem = event.dataItem

                when (dataItem.uri.path) {
                    "/calories_data" -> {
                        val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
                        val calories = dataMap.getDouble("calories")
                        val timeStamp = dataMap.getLong("timeStamp")
                        Log.d("Received calories data", "$calories at $timeStamp")

                        runBlocking {
                            userRepository.saveCalories(calories) //수신한 칼로리 저장
                        }
                    }

                    "/refresh_token_expired" -> { //todo - 추후 이 서비스 클래스가 어디 레이어로 분리되는지에 따라 뷰모델을 주입받는 방식이 부적절 할 수도 있음.
                        settingViewModel.navigateLogin()
                    }
                }
            }
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        // 추가 메시지 처리가 필요한 경우 이곳에 구현
    }
}
