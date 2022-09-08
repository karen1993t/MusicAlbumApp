package com.applemarketingtools.musicapp.album_details_screen.presentation.screens

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.applemarketingtools.musicapp.R
import com.applemarketingtools.musicapp.album_details_screen.presentation.model.AlbumInfoDetailsPresentationModel
import com.applemarketingtools.musicapp.album_details_screen.presentation.model.AlbumInfoDetailsSideEffect
import com.applemarketingtools.musicapp.album_details_screen.presentation.viewmodel.AlbumInfoDetailsViewModel
import com.applemarketingtools.musicapp.album_screen.presentation.models.AlbumInfoPresentationModel
import com.applemarketingtools.musicapp.core.extensions.MultipleEventsCutter
import com.applemarketingtools.musicapp.core.extensions.get
import com.applemarketingtools.musicapp.core.mvi.UiStatus
import com.applemarketingtools.musicapp.core.presentation.screens.LoadingIndicator
import com.applemarketingtools.musicapp.ui.theme.MusicAppExerciseTheme
import com.applemarketingtools.musicapp.ui.theme.MusicAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.viewModel
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun AlbumInfoDetailsScreen(
    modifier: Modifier,
    albumInfoData: AlbumInfoPresentationModel,
    navController: NavController
) {

    val albumDetailsViewModel: AlbumInfoDetailsViewModel by viewModel()
    val albumViewState by albumDetailsViewModel.container.stateFlow.collectAsState()
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    LaunchedEffect(key1 = true) {
        albumDetailsViewModel.loadInitialAlbumInfoData(albumInfoData = albumInfoData)
    }

    albumDetailsViewModel.collectSideEffect { sideEffects ->
        when (sideEffects) {
            is AlbumInfoDetailsSideEffect.OnVisitButtonClicked -> {
                if (sideEffects.isClicked) {
                    val url = sideEffects.artistUrl
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)

                    startActivity(context, intent, null)
                }
            }
            is AlbumInfoDetailsSideEffect.NavigateToBackStack -> {
                navController.popBackStack()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MusicAppTheme.colors.primaryBackground)
    ) {

        when (albumViewState.status) {

            is UiStatus.Loading -> {
                LoadingIndicator(
                    modifier = modifier
                        .background(MusicAppTheme.colors.primaryBackground)
                )
            }
            is UiStatus.Success -> {
                SideEffect {
                    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
                    systemUiController.setStatusBarColor(Color.Transparent)
                }
                AlbumInfoDetailsScreenSuccess(
                    modifier = modifier,
                    data = albumViewState.data,
                    visitButtonClick = {
                        multipleEventsCutter.processEvent {
                            albumDetailsViewModel.onVisitButtonClicked(artisUrl = albumViewState.data.artistUrl)
                        }
                    },
                    backButtonClicked = { albumDetailsViewModel.onBackButtonClicked() }
                )
            }
            is UiStatus.Failed -> {
                return@Box
            }
            is UiStatus.Empty -> {
                return@Box
            }
        }
    }
}


@Composable
private fun AlbumInfoDetailsScreenSuccess(
    modifier: Modifier,
    data: AlbumInfoDetailsPresentationModel,
    isPreview: Boolean = false,
    visitButtonClick: () -> Unit,
    backButtonClicked: () -> Unit
) {
    // recourses
    val imageHeight = dimensionResource(id = R.dimen.album_info_details_image_height)
    val labelContainerPaddingStart =
        dimensionResource(id = R.dimen.album_info_details_label_container_padding_start)
    val labelContainerPaddingEnd =
        dimensionResource(id = R.dimen.album_info_details_label_container_padding_end)
    val labelContainerPaddingTop =
        dimensionResource(id = R.dimen.album_info_details_label_container_padding_top)
    val chipButtonText = stringResource(id = R.string.button_text_chip_base)
    val chipButtonMarginTop =
        dimensionResource(id = R.dimen.album_info_details_chip_button_margin_top)
    val chipButtonBorderStrokeWidth =
        dimensionResource(id = R.dimen.album_info_details_chip_button_border_stroke_width)
    val chipButtonContentPaddingStart =
        dimensionResource(id = R.dimen.album_info_details_chip_button_content_padding_start)
    val chipButtonContentPaddingEnd =
        dimensionResource(id = R.dimen.album_info_details_chip_button_content_padding_end)
    val chipButtonContentPaddingTop =
        dimensionResource(id = R.dimen.album_info_details_chip_button_content_padding_top)
    val chipButtonContentPaddingBottom =
        dimensionResource(id = R.dimen.album_info_details_chip_button_content_padding_bottom)
    val label1ContainerPaddingStart =
        dimensionResource(id = R.dimen.album_info_details_label_1_padding_start)
    val label1ContainerPaddingEnd =
        dimensionResource(id = R.dimen.album_info_details_label_1_padding_end)
    val label1ContainerPaddingBottom =
        dimensionResource(id = R.dimen.album_info_details_label_1_padding_bottom)
    val visitButtonText = stringResource(id = R.string.button_text_go_to_album)
    val visitButtonMarginTop =
        dimensionResource(id = R.dimen.album_info_details_visit_button_margin_top)
    val visitButtonContentPaddingHor =
        dimensionResource(id = R.dimen.album_info_details_visit_button_content_padding_hor)
    val visitButtonContentPaddingVertical =
        dimensionResource(id = R.dimen.album_info_details_visit_button_content_padding_vertical)
    val backButtonMarginStart =
        dimensionResource(id = R.dimen.album_info_details_back_button_margin_start)
    val backButtonMarginTop =
        dimensionResource(id = R.dimen.album_info_details_back_button_margin_top)

    Column(
        modifier = modifier
            .background(MusicAppTheme.colors.primaryBackground)
    ) {
        if (!isPreview) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                model = data.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "IconAlbum",
            )
        } else {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                painter = painterResource(id = data.previewImageResId),
                contentScale = ContentScale.Crop,
                contentDescription = "IconAlbum",
            )
        }

        Column(
            modifier = modifier
                .background(MusicAppTheme.colors.primaryBackground),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MusicAppTheme.colors.primaryBackground)
                    .padding(
                        start = labelContainerPaddingStart,
                        end = labelContainerPaddingEnd,
                        top = labelContainerPaddingTop
                    )
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    text = data.artistName,
                    color = MusicAppTheme.colors.body2SecondaryText,
                    style = MusicAppTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    textAlign = TextAlign.Start,
                    text = data.name,
                    color = MusicAppTheme.colors.body2PrimaryText,
                    style = MusicAppTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Button(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .background(MusicAppTheme.colors.primaryBackground)
                        .padding(top = chipButtonMarginTop),
                    border = BorderStroke(
                        width = chipButtonBorderStrokeWidth,
                        color = MusicAppTheme.colors.buttonPrimary
                    ),
                    contentPadding = PaddingValues(
                        start = chipButtonContentPaddingStart,
                        end = chipButtonContentPaddingEnd,
                        top = chipButtonContentPaddingTop,
                        bottom = chipButtonContentPaddingBottom
                    ),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MusicAppTheme.colors.primaryBackground
                    ),
                    shape = MusicAppTheme.roundedCornerShape.shapeLarge,

                    ) {

                    Text(
                        textAlign = TextAlign.Center,
                        text = chipButtonText,
                        style = MusicAppTheme.typography.labelSmall,
                        color = MusicAppTheme.colors.buttonPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MusicAppTheme.colors.primaryBackground)
                    .padding(
                        start = label1ContainerPaddingStart,
                        end = label1ContainerPaddingEnd,
                        bottom = label1ContainerPaddingBottom
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = data.releaseDate,
                    color = MusicAppTheme.colors.secondaryText,
                    style = MusicAppTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = data.copyright,
                    color = MusicAppTheme.colors.secondaryText,
                    style = MusicAppTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Button(
                    modifier = Modifier
                        .padding(top = visitButtonMarginTop)
                        .background(MusicAppTheme.colors.primaryBackground),
                    contentPadding = PaddingValues(
                        horizontal = visitButtonContentPaddingHor,
                        vertical = visitButtonContentPaddingVertical
                    ),

                    onClick = {
                        visitButtonClick.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MusicAppTheme.colors.buttonPrimary
                    ),
                    shape = MusicAppTheme.roundedCornerShape.shapeSmall,
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = visitButtonText,
                        style = MusicAppTheme.typography.labelMedium,
                        color = MusicAppTheme.colors.primaryBackground,
                        maxLines = 1,
                    )
                }
            }
        }
    }

    IconButton(
        modifier = Modifier
            .padding(
                start = backButtonMarginStart,
                top = backButtonMarginTop
            ),
        onClick = {
            backButtonClicked.invoke()

        }) {

        Icon(
            painter = painterResource(id = R.drawable.ic_back_button),
            contentDescription = "",
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_round_arrow_back),
            contentDescription = "",
        )
    }
}


@Preview(
    name = "Album Info Details Light Screen",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun AlbumInfoDetailsScreenPreviewLight() {
    MusicAppExerciseTheme(darkTheme = false) {
        AlbumInfoDetailsScreenSuccess(
            modifier = Modifier.fillMaxSize(),
            data = AlbumInfoDetailsPresentationModel.preview(),
            isPreview = true,
            visitButtonClick = {},
            backButtonClicked = {}
        )
    }
}

@Preview(
    name = "Album Info Details Dark Screen",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun AlbumInfoDetailsScreenPreviewDark() {
    MusicAppExerciseTheme(darkTheme = true) {
        AlbumInfoDetailsScreenSuccess(
            modifier = Modifier.fillMaxSize(),
            data = AlbumInfoDetailsPresentationModel.preview(),
            isPreview = true,
            visitButtonClick = {},
            backButtonClicked = {}
        )
    }
}



