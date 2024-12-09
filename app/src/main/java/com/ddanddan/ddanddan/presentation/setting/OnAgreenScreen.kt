package com.ddanddan.ddanddan.presentation.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.navigation.DDanDDanRoute
import com.ddanddan.ui.compose.component.DdanScaffold

@Composable
fun onAgreeScreen(
    navController: NavHostController,
    onTopBarBackClick: () -> Unit = {}
) {
    DdanScaffold(
        topbarText = stringResource(id = R.string.setting_notice_title),
        onClick = {
            onTopBarBackClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                SettingTitle(
                    title = "서비스 이용약관",
                    onClick = {
                        navController.navigate(DDanDDanRoute.WEBVIEW.route + "?url=https://www.notion.so/4105267fc3b849fba10b8a3155809255")
                    }
                )
                SettingTitle(
                    title = "개인정보 처리방침",
                    onClick = {
                        navController.navigate(DDanDDanRoute.WEBVIEW.route + "?url=https://www.notion.so/1d544c615c44412fa51d2ecb9f98116a")
                    }
                )
            }
        }

    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
fun OnAgreePreview() {
    val navController = rememberNavController()
    onAgreeScreen(navController = navController)
}