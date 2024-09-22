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
                    HomeScreen(
                        onStorageClick = {
                            // TODO 보관함 클릭 로직
                        },
                        onSettingClick = {
                            // TODO 설정 클릭 로직
                        },
                        onEatClick = {
                            // TODO 먹이주기 클릭 로직
                        },
                        onPlayClick = {
                            // TODO 놀아주기 클릭 로직
                        }
                    )
                }
            }
        }
    }
}