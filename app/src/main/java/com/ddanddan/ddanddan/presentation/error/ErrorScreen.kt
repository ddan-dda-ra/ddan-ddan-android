package com.ddanddan.ddanddan.presentation.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ddanddan.base.R
import com.ddanddan.ddanddan.presentation.home.reward.level.BottomButton
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.theme.DDanDDanTheme

@Composable
fun ErrorScreen(
    isNotPage: Boolean = true,
    errorCode: Int? = null,
    onMoveHomeClicked: () -> Unit = {}
) {
    Scaffold(
        containerColor = DDanDDanColorPalette.current.color_background,
        bottomBar = {
            BottomButton(btnText = "홈으로 이동") {
                onMoveHomeClicked()
            }
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            val (image, title, content) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Text(
                text = if (isNotPage) "페이지를 찾을 수 없습니다." else "오류가 발생했습니다.",
                style = DDanDDanTypo.current.HeadLine7,
                color = DDanDDanColorPalette.current.color_text_headline_teritary,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(image.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            if (!isNotPage) {
                Text(
                    text = "일시적인 현상이니 잠시 후 다시 시도해 주세요\n" +
                            "(오류코드 : $errorCode)",
                    textAlign = TextAlign.Center,
                    style = DDanDDanTypo.current.Body1,
                    color = DDanDDanColorPalette.current.color_text_body_quinary,
                    modifier = Modifier.constrainAs(content) {
                        top.linkTo(title.bottom, margin = 4.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ErrorScreenPreview() {
    DDanDDanTheme(darkTheme = true) {
        ErrorScreen()
    }
}