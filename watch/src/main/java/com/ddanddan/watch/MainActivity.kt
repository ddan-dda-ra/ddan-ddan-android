package com.ddanddan.watch

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ddanddan.watch.presentation.PassiveDataApp
import com.ddanddan.watch.service.PassiveDataService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serviceIntent = Intent(this, PassiveDataService::class.java)
        this.startService(serviceIntent)

        setContent {
            PassiveDataApp()
        }
    }
}
