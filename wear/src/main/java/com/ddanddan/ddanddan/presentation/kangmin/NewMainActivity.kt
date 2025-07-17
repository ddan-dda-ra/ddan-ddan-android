package com.ddanddan.ddanddan.presentation.kangmin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.ddanddan.ddanddan.service.DataLayerListenerService
import com.ddanddan.ddanddan.util.WatchToPhoneDataSender
import com.ddanddan.ui.compose.theme.DDanDDanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NewMainActivity: ComponentActivity() {

    @Inject
    lateinit var watchToPhoneDataSender: WatchToPhoneDataSender
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DDanDDanTheme {
                MainScreen(
                    navigateToCalorie = {
                        startPassiveDataService()
                        requestUserInfo()
                    }
                )
            }
        }
    }

    private fun requestUserInfo() {
        lifecycleScope.launch {
            try {
                watchToPhoneDataSender.sendMessage("/request_user_info")
                Timber.d("Requested user info on app start")
            } catch (e: Exception) {
                Timber.e(e, "Failed to request user info on app start")
            }
        }
    }

    private fun startPassiveDataService() {
        startService(Intent(applicationContext, DataLayerListenerService::class.java))
    }
}