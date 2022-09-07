package com.applemarketingtools.musicapp.album_screen.presentation.models


import androidx.compose.ui.tooling.preview.PreviewParameterProvider


class PreviewAlbumInfoPresentationModel : PreviewParameterProvider<AlbumInfoPresentationModel> {

    override val values: Sequence<AlbumInfoPresentationModel> = sequenceOf(
        AlbumInfoPresentationModel(
            name = "",
            artistId = "",
           artistUrl = "",
           artistName = "",
           artworkUrl100 = "",
           copyright = "",
           contentAdvisoryRating = "",
           id = "",
           kind = "",
           releaseDate = "",
           url = ""
        )
    )
}


