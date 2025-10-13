package com.ddanddan.ddanddan.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chottulink.lib.ChottuLink
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.theme.DDanDDanTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var inviteCode by remember { mutableStateOf<String?>(null) }
            DDanDDanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DDanDDanColorPalette.current.color_background
                ) {
                    MainScreen(inviteCode = inviteCode)
                }
            }

            ChottuLink.getAppLinkData(intent).addOnSuccessListener { result ->
                result?.link?.let { link ->
                    val code = link.getQueryParameter("code")
                    if (!code.isNullOrEmpty()) {
                        inviteCode = code
                    }
                }
            }
        } }

}