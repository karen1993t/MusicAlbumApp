package com.applemarketingtools.musicapp.album_screen.presentation.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.applemarketingtools.musicapp.R
import com.applemarketingtools.musicapp.album_details_screen.presentation.model.AlbumInfoDetailsPresentationModel
import com.applemarketingtools.musicapp.album_details_screen.presentation.screens.AlbumInfoDetailsScreen
import com.applemarketingtools.musicapp.album_screen.presentation.models.AlbumInfoPresentationModel
import com.applemarketingtools.musicapp.core.extensions.clickableSingle
import com.applemarketingtools.musicapp.ui.theme.MusicAppExerciseTheme
import com.applemarketingtools.musicapp.ui.theme.MusicAppStyle
import com.applemarketingtools.musicapp.ui.theme.MusicAppTheme


@Composable
fun ItemAlbumInfoScreen(
    modifier: Modifier,
    data: AlbumInfoPresentationModel,
    isPreviewMode: Boolean = false,
    onItemClicked: (AlbumInfoPresentationModel) -> Unit
) {

    // recourses
    val cardElevation =
        dimensionResource(id = R.dimen.album_info_item_card_elevation)
    val firstLabelStartPadding =
        dimensionResource(id = R.dimen.album_info_item_first_label_padding_start)
    val firstLabelEndPadding =
        dimensionResource(id = R.dimen.album_info_item_first_label_padding_end)
    val secondLabelStartPadding =
        dimensionResource(id = R.dimen.album_info_item_second_label_padding_start)
    val secondLabelEndPadding =
        dimensionResource(id = R.dimen.album_info_item_second_label_padding_end)
    val secondLabelBottomPadding =
        dimensionResource(id = R.dimen.album_info_item_second_label_padding_bottom)

    Card(
        modifier = modifier
            .shadow(
                clip = true,
                ambientColor = Color.White,
                elevation = cardElevation,
                shape = MusicAppTheme.roundedCornerShape.shapeMedium
            )
            .clickableSingle {
                onItemClicked.invoke(data)
            },
        shape = MusicAppTheme.roundedCornerShape.shapeMedium,
        backgroundColor = MusicAppTheme.colors.primaryBackground
    ) {
        AsyncImage(
            model = if (isPreviewMode) data.previewImageResId else data.artworkUrl100,
            contentScale = ContentScale.Crop,
            contentDescription = "IconAlbum",
            modifier = modifier
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = data.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = firstLabelStartPadding, end = firstLabelEndPadding),
                style = MusicAppTheme.typography.labelMedium,
                color = MusicAppTheme.colors.primaryText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = data.artistName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = secondLabelStartPadding,
                        end = secondLabelEndPadding,
                        bottom = secondLabelBottomPadding
                    ),
                style = MusicAppTheme.typography.labelSmall,
                color = MusicAppTheme.colors.secondaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@Preview(
    name = "ItemAlbumInfo",
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_YES
)
private fun ItemAlbumInfoScreenDarkModePreview() {
    MusicAppExerciseTheme(
        darkTheme = true,
        style = MusicAppStyle.Main
    ) {
        ItemAlbumInfoScreen(
            modifier = Modifier
                .width(173.dp)
                .height(173.dp),
            data = AlbumInfoPresentationModel.preview(),
            isPreviewMode = true
        ) {

        }
    }
}


@Composable
@Preview(
    name = "ItemAlbumInfoLight",
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)

private fun ItemAlbumInfoScreenPreviewLight() {
    MusicAppExerciseTheme(
        darkTheme = false,
        style = MusicAppStyle.Main
    ) {
        ItemAlbumInfoScreen(
            modifier = Modifier
                .width(173.dp)
                .height(173.dp),
            data = AlbumInfoPresentationModel.preview(),
            isPreviewMode = true

        ) {

        }
    }
}

@Composable
@Preview
private fun Preview() {
    AlbumInfoDetailsScreen(
        modifier = Modifier.fillMaxSize(),
        albumInfoData = AlbumInfoPresentationModel.preview(),
        navController = rememberNavController()
    )
}