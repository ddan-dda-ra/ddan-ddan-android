package com.ddanddan.ddanddan.service

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import com.ddanddan.ddanddan.util.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>


    override fun onDataChanged(dataEvents: DataEventBuffer) {

        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/token") {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val accessToken = dataMap.getString("accessToken")
                val refreshToken = dataMap.getString("refreshToken")

                Log.d("kangmi", accessToken.toString())
                saveToken(accessToken = accessToken, refreshToken = refreshToken)
            }
        }
    }


    private fun saveToken(accessToken: String?, refreshToken: String?) {
        if (!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty())

            CoroutineScope(Dispatchers.IO).launch {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.ACCESS_TOKEN_KEY] = accessToken
                    preferences[PreferencesKeys.REFRESH_TOKEN_KEY] = refreshToken
                }
                Timber.tag("DataLayerListenerService").d("Access token saved to DataStore")
            }
    }
}
