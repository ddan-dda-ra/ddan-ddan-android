package com.ddanddan.ddanddan.presentation.kangmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ddanddan.ui.compose.theme.DDanDDanTheme

class NewMainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DDanDDanTheme {
                MainScreen()
            }
        }
    }
}