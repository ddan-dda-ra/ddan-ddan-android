package com.ddanddan.ui.compose.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

enum class PetBackgroundShape(
    val viewportWidth: Float,
    val viewportHeight: Float,
    val bgPath: String,
    val shadowPath: String,
    val whiteDots: List<String>
) {
    STAR(
        viewportWidth = 320f,
        viewportHeight = 370f,
        bgPath = "M126,0H149H172H195H218V23H241V46H264V69H287V93V116V139V162V185V208V231V254V278V301H264V324H241V347H218V370H195H172H149H126H102V347H79V324H56V301H33V278V254V231V208V185V162V139V116V93V69H56V46H79V23H102V0H126Z",
        shadowPath = "M110,301a50,12 0,1 0,100 0a50,12 0,1 0,-100 0z",
        whiteDots = listOf(
            "M64,104h4v12h-4z", "M60,112l0,-4l12,-0l0,4z",
            "M189,51h4v12h-4z", "M185,59l0,-4l12,-0l0,4z",
            "M240,144h4v12h-4z", "M236,152l0,-4l12,-0l0,4z"
        )
    ),
    RECT(
        viewportWidth = 296f,
        viewportHeight = 200f,
        bgPath = "M0,0h296v200h-296z",
        shadowPath = "M98,168a50,12 0,1 0,100 0a50,12 0,1 0,-100 0z",
        whiteDots = listOf(
            "M44.76,108h2.76v8.27h-2.76z", "M42,113.51l0,-2.76l8.27,-0l0,2.76z",
            "M114.48,47.14h2.76v8.27h-2.76z", "M111.72,52.65l0,-2.76l8.27,-0l0,2.76z",
            "M237.76,114h2.76v8.27h-2.76z", "M235,119.51l0,-2.76l8.27,-0l0,2.76z"
        )
    )
}

fun Color.toShadowColor(): Color = Color(
    red = red * 0.82f,
    green = green * 0.82f,
    blue = blue * 0.82f,
    alpha = alpha
)

@Composable
fun PetBackground(
    mainColorHex: String,
    shape: PetBackgroundShape,
    modifier: Modifier = Modifier
) {
    val mainColor = remember(mainColorHex) {
        runCatching {
            Color(android.graphics.Color.parseColor(mainColorHex))
        }.getOrDefault(Color(0xFFFD85FF))
    }
    val shadowColor = remember(mainColor) { mainColor.toShadowColor() }

    Canvas(
        modifier = modifier
    ) {
        val scaleX = size.width / shape.viewportWidth
        val scaleY = size.height / shape.viewportHeight
        val matrix = Matrix().apply { scale(scaleX, scaleY) }

        // 배경
        drawPath(
            PathParser().parsePathString(shape.bgPath).toPath().apply { transform(matrix) },
            mainColor
        )
        // 그림자
        drawPath(
            PathParser().parsePathString(shape.shadowPath).toPath().apply { transform(matrix) },
            shadowColor
        )
        // 흰색 점들
        shape.whiteDots.forEach { pathData ->
            drawPath(
                PathParser().parsePathString(pathData).toPath().apply { transform(matrix) },
                Color.White
            )
        }
    }
}