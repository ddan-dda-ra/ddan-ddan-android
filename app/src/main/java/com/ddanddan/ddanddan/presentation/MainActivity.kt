package com.ddanddan.ddanddan.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.theme.DDanDDanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DDanDDanTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = DDanDDanColorPalette.current.color_background) {
                    MainScreen()
                }
            }
        }
    }
}