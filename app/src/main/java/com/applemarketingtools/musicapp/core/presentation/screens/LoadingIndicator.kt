package com.applemarketingtools.musicapp.core.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.applemarketingtools.musicapp.ui.theme.MusicAppExerciseTheme
import com.applemarketingtools.musicapp.ui.theme.MusicAppTheme

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {

        CircularProgressIndicator(
            modifier = Modifier
                .align(
                    Alignment.Center
                ),
            color = MusicAppTheme.colors.buttonPrimary
        )
    }
}

@Preview(
    name = "Loading Screen Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun LoadingScreenLightPreview() {
    MusicAppExerciseTheme(darkTheme = false) {
        Box(modifier = Modifier.fillMaxSize()) {

            CircularProgressIndicator(
                modifier = Modifier
                    .align(
                        Alignment.Center
                    ),
                color = MusicAppTheme.colors.buttonPrimary,
                progress = 0.75f
            )
        }
    }
}