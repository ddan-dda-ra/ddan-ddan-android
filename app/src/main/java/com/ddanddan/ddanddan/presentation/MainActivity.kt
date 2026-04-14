package com.ddanddan.ddanddan.presentation

import android.content.Intent
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
import androidx.core.net.toUri
import com.chottulink.lib.ChottuLink
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.theme.DDanDDanTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var inviteCode by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DDanDDanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DDanDDanColorPalette.current.color_background
                ) {
                    MainScreen(
                        goToPlayStore = {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    "https://play.google.com/store/apps/details?id=$packageName".toUri()
                                )
                            )
                        },
                        inviteCode = inviteCode
                    )
                }
            }
            handleInviteLink(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleInviteLink(intent)
    }

    private fun handleInviteLink(intent: Intent) {
        ChottuLink.getAppLinkData(intent).addOnSuccessListener { result ->
            result?.link?.let { link ->
                val code = link.getQueryParameter("code")
                if (!code.isNullOrEmpty()) {
                    inviteCode = code
                }
            }
        }
    }

}