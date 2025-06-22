package com.hazarduman.cinescope.core.util

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hazarduman.cinescope.core.constants.BOTTOM_SHEET_ANIMATION_DURATION
import com.hazarduman.cinescope.core.enums.BottomSheetStatus
import com.hazarduman.cinescope.ui.model.BottomSheetConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Stable
class BottomSheetController {
    var onShow: (() -> Unit)? = null
    var onHide: (() -> Unit)? = null
    var onHalfExpand: (() -> Unit)? = null

    var bottomSheetConfig: BottomSheetConfig? = null

    var bottomSheetStatus by mutableStateOf(BottomSheetStatus.HIDDEN)

    fun show(bottomSheetConfig: BottomSheetConfig) {
        Log.d("BottomSheetCheckLog", "BottomSheetController: show called with config: $bottomSheetConfig")
        this.bottomSheetConfig = bottomSheetConfig
        onShow?.invoke()
    }

    fun hide(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            onHide?.invoke()
            delay(BOTTOM_SHEET_ANIMATION_DURATION.toLong())
            bottomSheetConfig = null
        }
    }

    fun halfExpand(bottomSheetConfig: BottomSheetConfig) {
        this.bottomSheetConfig = bottomSheetConfig
        onHalfExpand?.invoke()
    }
}