package com.applemarketingtools.musicapp.album_screen.presentation.models

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.applemarketingtools.musicapp.R


@Immutable
data class AlbumInfoPresentationModel(
    val artistId: String,
    val artistName: String,
    val artistUrl: String,
    val artworkUrl100: String,
    val contentAdvisoryRating: String,
    val id: String,
    val kind: String,
    val name: String,
    val releaseDate: String,
    val url: String,
    val copyright: String,
    val previewImageResId: Int = R.drawable.ic_launcher_background

) {
    companion object {
        fun initial() = AlbumInfoPresentationModel(
            artistId = "",
            artistName = "",
            artistUrl = "",
            artworkUrl100 = "",
            id = "",
            kind = "",
            name = "",
            releaseDate = "",
            url = "",
            contentAdvisoryRating = "",
            copyright = ""
        )

        fun preview() = AlbumInfoPresentationModel(
            artistId = "199992",
            artistName = "Jenifer Lopes",
            artistUrl = "http/",
            artworkUrl100 = "",
            id = "1",
            kind = "11",
            name = "Command",
            releaseDate = "2022-05-09",
            url = "",
            contentAdvisoryRating = "",
            copyright = ""
        )
    }
}


