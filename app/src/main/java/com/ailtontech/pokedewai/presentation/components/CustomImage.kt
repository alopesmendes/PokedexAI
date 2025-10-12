package com.ailtontech.pokedewai.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ailtontech.pokedewai.R
import com.ailtontech.pokedewai.presentation.utils.shimmerEffect

/**
 * A composable that displays an image from a URL with shimmer loading, placeholder, and error support.
 *
 * @param imageUrl The URL of the image to load.
 * @param modifier The modifier to be applied to the image.
 * @param contentDescription The content description for the image.
 * @param contentScale The scaling to apply to the image.
 * @param placeholder An optional drawable resource to display while the image is loading.
 * @param error An optional drawable resource to display if the image fails to load.
 * @param shimmer Whether the shimmer effect is visible.
 */
@Composable
fun CustomImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    @DrawableRes placeholder: Int? = R.drawable.placeholder,
    @DrawableRes error: Int? = R.drawable.error,
    shimmer: Boolean = true,
) {
    if (LocalInspectionMode.current) {
        if (placeholder != null) {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
        return
    }

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        loading = {
            Box(modifier = Modifier.shimmerEffect(visible = shimmer))
        },
        error = {
            if (error != null) {
                Image(
                    painter = painterResource(id = error),
                    contentDescription = contentDescription,
                    contentScale = contentScale
                )
            }
        }
    )
}

@Preview
@Composable
private fun CustomImagePreview() {
    CustomImage(
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
        contentDescription = "Bulbasaur"
    )
}
