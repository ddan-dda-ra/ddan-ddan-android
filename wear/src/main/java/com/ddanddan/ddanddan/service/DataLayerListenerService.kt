package com.ddanddan.ddanddan.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ddanddan.ddanddan.domain.repository.PassiveDataRepository
import com.ddanddan.ddanddan.util.PreferencesKeys
import com.ddanddan.ddanddan.util.WatchToPhoneDataSender
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>
    
    @Inject
    lateinit var watchToPhoneDataSender: WatchToPhoneDataSender
    
    @Inject
    lateinit var passiveDataRepository: PassiveDataRepository
    
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onMessageReceived(messageEvent: MessageEvent) {
        when (messageEvent.path) {
            "/request_latest_calories" -> {
                // 핸드폰으로부터 최신 칼로리 데이터 요청을 받음
                // 저장된 최신 칼로리 데이터를 가져와서 전송
                serviceScope.launch {
                    try {
                        // 저장소에서 최신 칼로리 데이터 가져오기
                        val latestCalories = passiveDataRepository.latestCalories.firstOrNull()
                        val timestamp = System.currentTimeMillis()
                        
                        if (latestCalories != null) {
                            // 핸드폰으로 데이터 전송
                            watchToPhoneDataSender.sendCalories(latestCalories, timestamp)
                        } else {
                            Timber.d("No calories data available to send")
                        }
                    } catch (e: Exception) {
                        Timber.e(e, "Error sending calories data")
                    }
                }
            }
            
            "/user_info" -> {
                // 핸드폰으로부터 유저 정보 수신
                try {
                    val jsonData = String(messageEvent.data)
                    Timber.d("Received user info: $jsonData")
                    
                    // JSON 파싱
                    val jsonObject = org.json.JSONObject(jsonData)
                    val petType = jsonObject.getString("pet_type")
                    val petLevel = jsonObject.getInt("pet_level")
                    val targetCalories = jsonObject.getInt("target_calories")
                    // 폰이 카탈로그에서 못 찾았거나 구버전 폰이면 필드가 없다
                    val petImageUrl = jsonObject.optString("pet_image_url").takeIf { it.isNotBlank() }

                    Timber.tag("kangmi")
                        .d("petType: $petType, petLevel: $petLevel, targetCalories: $targetCalories, petImageUrl: $petImageUrl")
                    // 유저 정보 저장
                    serviceScope.launch {
                        saveUserInfo(petType, petLevel, targetCalories, petImageUrl)
                        Timber.d("User info saved successfully")
                    }
                } catch (e: Exception) {
                    Timber.e(e, "Error parsing user info")
                }
            }
        }
    }

    // onDataChanged is no longer used as we're now using onMessageReceived
    
    /**
     * 폰에 유저 정보 요청
     */
    private fun requestUserInfo() {
        serviceScope.launch {
            try {
                watchToPhoneDataSender.sendMessage("/request_user_info")
                Timber.d("Successfully requested user info from phone")
            } catch (e: Exception) {
                Timber.e(e, "Failed to request user info from phone")
            }
        }
    }
    
    /**
     * 받은 유저 정보 로컬에 저장
     */
    private suspend fun saveUserInfo(
        petType: String,
        petLevel: Int,
        targetCalories: Int,
        petImageUrl: String?
    ) {
        try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.PET_TYPE] = petType
                preferences[PreferencesKeys.PET_LEVEL] = petLevel
                preferences[PreferencesKeys.TARGET_CALORIES] = targetCalories
                // 펫이 바뀌었는데 URL이 없으면 이전 펫의 URL이 남지 않도록 지운다
                if (petImageUrl != null) {
                    preferences[PreferencesKeys.PET_IMAGE_URL] = petImageUrl
                } else {
                    preferences.remove(PreferencesKeys.PET_IMAGE_URL)
                }
            }
            Timber.d("User info saved: petType=$petType, petLevel=$petLevel, targetCalories=$targetCalories, petImageUrl=$petImageUrl")
            
            // 여기에서 필요한 경우 UI 업데이트를 위한 브로드캐스트나 이벤트를 발생시킬 수 있습니다.
        } catch (e: Exception) {
            Timber.e(e, "Failed to save user info")
        }
    }

    private fun saveToken(accessToken: String?, refreshToken: String?) {
        if (!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()) {
            serviceScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences[PreferencesKeys.ACCESS_TOKEN_KEY] = accessToken
                        preferences[PreferencesKeys.REFRESH_TOKEN_KEY] = refreshToken
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }
}
