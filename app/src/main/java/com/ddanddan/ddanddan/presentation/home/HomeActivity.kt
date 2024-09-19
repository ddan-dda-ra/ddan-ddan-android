package com.ddanddan.ddanddan.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ddanddan.ui.compose.ColorPalette_Dark
import com.ddanddan.ui.compose.theme.DDanDDanTheme

class HomeActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DDanDDanTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = ColorPalette_Dark.color_background) {
                    HomeScreen()
                }
            }
        }
    }
}