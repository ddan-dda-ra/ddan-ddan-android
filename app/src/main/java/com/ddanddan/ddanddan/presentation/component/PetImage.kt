package com.ddanddan.ddanddan.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.ddanddan.ddanddan.util.imageUrlFor
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.domain.entity.PetCatalog

/**
 * 카탈로그에 해당 펫 타입/레벨의 imageUrl이 있으면 그걸 로드하고,
 * 없으면(카탈로그 미로딩·새 타입 등) 번들에 있는 로컬 리소스로 대체한다.
 */
@Composable
fun PetImage(
    petType: String?,
    level: Int?,
    catalog: PetCatalog?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val imageUrl = catalog.imageUrlFor(petType, level)
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    } else {
        Image(
            painter = painterResource(id = petType.toAnimal(level)),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}
