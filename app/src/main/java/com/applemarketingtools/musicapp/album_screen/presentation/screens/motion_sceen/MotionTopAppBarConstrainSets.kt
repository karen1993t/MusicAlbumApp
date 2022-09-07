package com.applemarketingtools.musicapp.album_screen.presentation.screens.motion_sceen

import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Transition

fun startConstrainSet() = ConstraintSet {
    val topAppBarBox = createRefFor(TopAppBarMotionEnum.TOP_APP_BAR.id)
    val title = createRefFor(TopAppBarMotionEnum.TITLE_APP_BAR.id)
    val albumInfoScreenBox = createRefFor(TopAppBarMotionEnum.ALBUM_INFO_SCREEN.id)

    constrain(topAppBarBox) {

        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(title) {
        height = Dimension.wrapContent

        start.linkTo(parent.start)
        top.linkTo(parent.top)
        end.linkTo(parent.end)

    }
    constrain(albumInfoScreenBox) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(topAppBarBox.bottom)
        bottom.linkTo(parent.bottom)
    }


}


fun endConstrainSet() = ConstraintSet {
    val topAppBarBox = createRefFor(TopAppBarMotionEnum.TOP_APP_BAR)
    val title = createRefFor(TopAppBarMotionEnum.TITLE_APP_BAR)
    val content = createRefFor(TopAppBarMotionEnum.ALBUM_INFO_SCREEN)

    constrain(topAppBarBox) {

        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    constrain(title) {
        height = Dimension.wrapContent
        start.linkTo(parent.start)
        top.linkTo(parent.top)
        end.linkTo(parent.end)


    }
    constrain(content) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
    }

}



