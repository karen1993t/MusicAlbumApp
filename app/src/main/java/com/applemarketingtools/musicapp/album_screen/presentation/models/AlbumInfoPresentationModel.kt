package com.applemarketingtools.musicapp.album_screen.presentation.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.applemarketingtools.musicapp.R


@Immutable
data class AlbumInfoPresentationModel(
    val artistId: String,
    val artistName: String,
    val artistUrl: String,
    val imageUrl: String,
    val contentAdvisoryRating: String,
    val id: String,
    val kind: String,
    val name: String,
    val releaseDate: String,
    val url: String,
    val copyright: String,
    @DrawableRes val previewImageResId: Int = 0

) {
    companion object {
        fun initial() = AlbumInfoPresentationModel(
            artistId = "",
            artistName = "",
            artistUrl = "",
            imageUrl = "",
            id = "",
            kind = "",
            name = "",
            releaseDate = "",
            url = "",
            contentAdvisoryRating = "",
            copyright = ""
        )

        fun preview() = AlbumInfoPresentationModel(
            artistId = "artist_id",
            artistName = "Bad Bunny",
            artistUrl = "artist_url",
            imageUrl = ".......",
            id = "1",
            kind = "fsfsf",
            name = "Un Verano Sin Ti",
            releaseDate = "Released May 20, 2022",
            url = "url",
            contentAdvisoryRating = ".......",
            copyright = "Copyright 2022 Apple inc.All rights reserved.",
            previewImageResId = R.mipmap.ic_album_info_details_preview
        )
    }
}


