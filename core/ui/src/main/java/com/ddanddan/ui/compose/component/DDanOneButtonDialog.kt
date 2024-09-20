package com.ddanddan.ui.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ddanddan.base.R
import com.ddanddan.ui.compose.DDanDDanColorPalette
import com.ddanddan.ui.compose.DDanDDanTypo
import com.ddanddan.ui.compose.theme.DDanDDanTheme


@Composable
fun DDanOneButtonDialog(
    title: String,
    content: String,
    buttonText: String = "획득하기",
    imageSource: Int? = null,
    onClickCancel: () -> Unit = {},
    onClickConfirm: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onClickCancel() }
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            backgroundColor = DDanDDanColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 40.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imageSource != null) {
                    Image(
                        painter = painterResource(id = imageSource),
                        contentDescription = "이미지",
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                Text(
                    text = title,
                    style = DDanDDanTypo.current.HeadLine6,
                    color = DDanDDanColorPalette.current.color_text_headline_primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = content,
                    style = DDanDDanTypo.current.SubTitle1,
                    color = DDanDDanColorPalette.current.color_text_body_teritary
                )

                Spacer(modifier = Modifier.height(28.dp))


                Button(
                    onClick = { onClickConfirm() },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 17.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DDanDDanColorPalette.current.color_button_active,
                        contentColor = DDanDDanColorPalette.current.color_text_button_primary_default
                    ),
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text(text = buttonText, style = DDanDDanTypo.current.HeadLine6)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DDanOneButtonDialogPreview() {
    DDanDDanTheme(darkTheme = true) {
        DDanOneButtonDialog(title = "제목", content = "먹이를 두개 받으세요")
    }
}