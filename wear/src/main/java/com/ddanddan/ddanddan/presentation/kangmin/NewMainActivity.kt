package com.ddanddan.ddanddan.presentation.kangmin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ddanddan.ui.compose.theme.DDanDDanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewMainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startPassiveDataService()

        setContent {
            DDanDDanTheme {
                MainScreen()
            }
        }
    }

    private fun startPassiveDataService() {
        val serviceIntent = Intent(this, PassiveDataService::class.java)
        startService(serviceIntent)
    }
}