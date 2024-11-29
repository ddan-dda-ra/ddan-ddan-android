package com.ddanddan.ddanddan.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ddanddan.ddanddan.presentation.onboarding.OnboardingActivity
import com.ddanddan.ddanddan.presentation.signin.SignInActivity
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
                    MainScreen(
                        onNavigateOnBoarding = {
                            Intent(this, OnboardingActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(this)
                                finish()
                            }
                        },
                        onNavigateLogin = {
                            Intent(this, SignInActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(this)
                                finish()
                            }
                        }
                    )
                }
            }
        }
    }
}