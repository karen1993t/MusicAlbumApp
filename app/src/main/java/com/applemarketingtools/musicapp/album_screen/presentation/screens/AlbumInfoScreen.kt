package com.applemarketingtools.musicapp.album_screen.presentation.screens

import android.app.Activity
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.applemarketingtools.musicapp.R
import com.applemarketingtools.musicapp.album_screen.presentation.GRID_CELLS_FIX_COUNT
import com.applemarketingtools.musicapp.album_screen.presentation.models.AlbumInfoPresentationModel
import com.applemarketingtools.musicapp.album_screen.presentation.models.AlbumInfoScreenSideEffect
import com.applemarketingtools.musicapp.album_screen.presentation.viewmodel.AlbumScreenViewModel
import com.applemarketingtools.musicapp.core.mvi.UiStatus
import com.applemarketingtools.musicapp.core.navigation.AppRoutes
import com.applemarketingtools.musicapp.core.presentation.screens.EmptyListScreen
import com.applemarketingtools.musicapp.core.presentation.screens.LoadingIndicator
import com.applemarketingtools.musicapp.core.presentation.screens.NetworkErrorScreen
import com.applemarketingtools.musicapp.ui.theme.MusicAppExerciseTheme
import com.applemarketingtools.musicapp.ui.theme.MusicAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.viewModel
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun AlbumInfoScreen(
    modifier: Modifier,
    navController: NavController
) {

    val albumViewModel: AlbumScreenViewModel by viewModel()
    val albumInfoViewState by albumViewModel.container.stateFlow.collectAsState()
    val isRefreshing by albumViewModel.isRefreshing.collectAsState()
    val lazyScrollState = rememberLazyGridState()
    val systemUiController = rememberSystemUiController()
    val isDarkModeValue = isSystemInDarkTheme()
    val isDarkMode by remember { mutableStateOf(isDarkModeValue) }
    val activity = LocalContext.current as Activity
    val statusBackground = MusicAppTheme.colors.primaryBackground.value




    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(activity.window, true)

        systemUiController.setStatusBarColor(
            color = Color(statusBackground).copy(alpha = 0.85f),          // Change status bar color
            darkIcons = !isDarkMode
        )
    }

    LaunchedEffect(key1 = true) {
        albumViewModel.loadInitialData()
    }

    albumViewModel.collectSideEffect { sideEffects ->
        when (sideEffects) {
            is AlbumInfoScreenSideEffect.NavigateToAlbumInfoDetailsScreen -> {
                if (sideEffects.isItemClicked) {
                    navController.navigate(
                        "${AppRoutes.AlbumInfoDetailsScreen.route}/${
                            Uri.encode(
                                sideEffects.data
                            )
                        }"
                    )
                }
            }
        }
    }

    Surface(modifier = modifier.background(MusicAppTheme.colors.primaryBackground)) {

        when (albumInfoViewState.status) {
            is UiStatus.Success -> {
                albumViewModel.setRefresh(false)
                SetupMainToolBar(
                    lazyScrollState,
                ) {
                    SwipeRefresh(
                        modifier = Modifier.layoutId("content"),
                        state = rememberSwipeRefreshState(isRefreshing),
                        onRefresh = { albumViewModel.refresh() },
                        indicator = { state, trigger ->
                            SwipeRefreshIndicator(
                                state = state,
                                refreshTriggerDistance = trigger,
                                scale = true,
                                contentColor = MusicAppTheme.colors.buttonPrimary
                            )
                        }

                    ) {
                        AlbumInfoSuccessScreen(
                            modifier = modifier,
                            lazyScrollState = lazyScrollState,
                            data = albumInfoViewState.data
                        ) {
                            albumViewModel.itemClicked(data = it)
                        }
                    }
                }
            }
            is UiStatus.Empty -> {
                albumViewModel.setRefresh(false)
                EmptyListScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MusicAppTheme.colors.primaryBackground)
                )
            }
            is UiStatus.Failed -> {
                albumViewModel.setRefresh(false)
                NetworkErrorScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MusicAppTheme.colors.primaryBackground),
                ) {
                    albumViewModel.tryAgainClicked()
                }
            }
            is UiStatus.Loading -> {
                LoadingIndicator(modifier = Modifier.fillMaxSize())
            }
        }
    }
}


@Composable
private fun AlbumInfoSuccessScreen(
    modifier: Modifier,
    lazyScrollState: LazyGridState,
    data: List<AlbumInfoPresentationModel>,
    isPreviewMode: Boolean = false,
    itemClicked: (AlbumInfoPresentationModel) -> Unit
) {

    // album info recourses
    val itemWidth = dimensionResource(id = R.dimen.album_info_item_width)
    val itemHeight = dimensionResource(id = R.dimen.album_info_item_height)
    val itemHorizontalDivider = dimensionResource(id = R.dimen.album_info_item_divider_hor)
    val itemVerticalDivider = dimensionResource(id = R.dimen.album_info_item_divider_vertical)
    val itemContentHorizontalPadding = dimensionResource(id = R.dimen.album_info_item_padding_hor)
    val itemContentVerticalPadding =
        dimensionResource(id = R.dimen.album_info_item_padding_vertical)

    LazyVerticalGrid(
        modifier = modifier.background(MusicAppTheme.colors.primaryBackground),
        verticalArrangement = Arrangement.spacedBy(itemVerticalDivider),
        horizontalArrangement = Arrangement.spacedBy(
            itemHorizontalDivider
        ),
        contentPadding = PaddingValues(
            horizontal = itemContentHorizontalPadding,
            vertical = itemContentVerticalPadding
        ),
        columns = GridCells.Fixed(GRID_CELLS_FIX_COUNT),
        state = lazyScrollState

    ) {
        items(items = data, key = { it.id }) { item ->
            ItemAlbumInfoScreen(
                modifier = Modifier
                    .width(itemWidth)
                    .height(itemHeight),
                data = item,
                isPreviewMode = isPreviewMode
            ) { onItemClickData ->
                itemClicked.invoke(onItemClickData)
            }
        }
    }
}

@Preview(
    name = "Album Info Screen Light Screen",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun AlbumInfoScreenLightPreview() {
    MusicAppExerciseTheme(darkTheme = false) {
        AlbumInfoSuccessScreen(
            modifier = Modifier.fillMaxSize(),
            lazyScrollState = rememberLazyGridState(),
            data = listOf(
                AlbumInfoPresentationModel.preview(),
                AlbumInfoPresentationModel.preview().copy(id = "2"),
                AlbumInfoPresentationModel.preview().copy(id = "3"),
                AlbumInfoPresentationModel.preview().copy(id = "4"),
                AlbumInfoPresentationModel.preview().copy(id = "5"),
                AlbumInfoPresentationModel.preview().copy(id = "6"),
                AlbumInfoPresentationModel.preview().copy(id = "7"),
                AlbumInfoPresentationModel.preview().copy(id = "8"),
                AlbumInfoPresentationModel.preview().copy(id = "9"),
                AlbumInfoPresentationModel.preview().copy(id = "10"),
                AlbumInfoPresentationModel.preview().copy(id = "11"),
                AlbumInfoPresentationModel.preview().copy(id = "12"),
                AlbumInfoPresentationModel.preview().copy(id = "13")
            ),
            isPreviewMode = true
        ) {
            // item Clicked
        }
    }
}

@Preview(
    name = "Album Info Screen Dark Screen",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AlbumInfoScreenDarkPreview() {
    MusicAppExerciseTheme(darkTheme = true) {
        AlbumInfoSuccessScreen(
            modifier = Modifier.fillMaxSize(),
            lazyScrollState = rememberLazyGridState(),
            data = listOf(
                AlbumInfoPresentationModel.preview(),
                AlbumInfoPresentationModel.preview().copy(id = "2"),
                AlbumInfoPresentationModel.preview().copy(id = "3"),
                AlbumInfoPresentationModel.preview().copy(id = "4"),
                AlbumInfoPresentationModel.preview().copy(id = "5"),
                AlbumInfoPresentationModel.preview().copy(id = "6"),
                AlbumInfoPresentationModel.preview().copy(id = "7"),
                AlbumInfoPresentationModel.preview().copy(id = "8"),
                AlbumInfoPresentationModel.preview().copy(id = "9"),
                AlbumInfoPresentationModel.preview().copy(id = "10"),
                AlbumInfoPresentationModel.preview().copy(id = "11"),
                AlbumInfoPresentationModel.preview().copy(id = "12"),
                AlbumInfoPresentationModel.preview().copy(id = "13")
            ),
            isPreviewMode = true
        ) {
            // item Clicked
        }
    }
}





