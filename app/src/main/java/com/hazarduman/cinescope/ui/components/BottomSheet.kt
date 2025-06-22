package com.hazarduman.cinescope.ui.components

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.hazarduman.cinescope.core.constants.BOTTOM_SHEET_ANIMATION_DURATION
import com.hazarduman.cinescope.core.enums.BottomSheetStatus
import com.hazarduman.cinescope.core.util.BottomSheetController
import com.hazarduman.cinescope.core.util.NoRippleInteractionSource
import com.hazarduman.cinescope.ui.model.BottomSheetDismissBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomSheet(
    bottomSheetController: BottomSheetController,
    coroutineScope: CoroutineScope
) {

    var bottomSheetStatus by bottomSheetController::bottomSheetStatus

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    var sheetHeightValue = when (bottomSheetStatus) {
        BottomSheetStatus.EXPANDED -> screenHeight
        BottomSheetStatus.HALF_EXPANDED -> screenHeight / 2
        BottomSheetStatus.HIDDEN -> 0.dp
    }

    var sheetBackgroundColorAlphaValue = when (bottomSheetStatus) {
        BottomSheetStatus.HIDDEN -> 0f
        else -> 0.7f
    }

    val sheetHeight by animateDpAsState(
        targetValue = sheetHeightValue,
        animationSpec = tween(durationMillis = BOTTOM_SHEET_ANIMATION_DURATION),
        label = "BottomSheetHeightAnimation"
    )

    val sheetTranslationY by animateFloatAsState(
        targetValue = 0f,//if (bottomSheetStatus == BottomSheetStatus.EXPANDED) 0.dp else 200.dp,
        animationSpec = tween(durationMillis = BOTTOM_SHEET_ANIMATION_DURATION),
        label = "BottomSheetTransitionYAnimation"
    )

    val sheetBackgroundColorAlpha by animateFloatAsState(
        targetValue = sheetBackgroundColorAlphaValue,
        animationSpec = tween(durationMillis = BOTTOM_SHEET_ANIMATION_DURATION),
        label = "BottomSheetBackgroundColorAlphaAnimation"
    )

    LaunchedEffect(bottomSheetController) {
        bottomSheetController.onShow = {
            bottomSheetStatus = BottomSheetStatus.EXPANDED
        }
        bottomSheetController.onHide = {
            coroutineScope.launch {
                bottomSheetStatus = BottomSheetStatus.HIDDEN
                Log.d("BottomSheetCheckLog", "BottomSheetController: hide called 1")
                /*delay(BOTTOM_SHEET_ANIMATION_DURATION.toLong())*/
                Log.d("BottomSheetCheckLog", "BottomSheetController: hide called 2")
            }
        }
        bottomSheetController.onHalfExpand = {
            bottomSheetStatus = BottomSheetStatus.HALF_EXPANDED
        }
    }

    if (bottomSheetController.bottomSheetConfig != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(sheetBackgroundColorAlpha))
                .clickable(
                    enabled = bottomSheetController.bottomSheetConfig?.dismissBehavior != BottomSheetDismissBehavior.NON_CANCELLABLE,
                    interactionSource = NoRippleInteractionSource()
                ) {
                    coroutineScope.launch {
                        bottomSheetController.hide(coroutineScope)
                    }
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight)
                    .graphicsLayer {
                        translationY
                    }
                    .clickable(
                        enabled = false,
                        interactionSource = NoRippleInteractionSource()
                    ) {
                        // Prevent clicks from propagating to the background
                    },
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            ) {
                bottomSheetController.bottomSheetConfig?.content()
            }
        }
    }
}

@Composable
fun rememberBottomSheetController(): BottomSheetController {
    return remember { BottomSheetController() }
}