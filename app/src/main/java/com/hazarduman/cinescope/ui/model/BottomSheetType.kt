package com.hazarduman.cinescope.ui.model

import androidx.compose.runtime.Composable

/**
 * Represents the available behaviors and properties for a bottom sheet.
 * Multiple properties can be combined for a single bottom sheet.
 *
 * Mutually exclusive behaviors are grouped as option sets below for type safety and clarity.
 */
enum class BottomSheetDismissBehavior {
    CANCELLABLE,
    NON_CANCELLABLE
}

enum class BottomSheetExpandBehavior {
    FULLY_EXPANDED,
    HALF_EXPANDED
}

enum class BottomSheetDragBehavior {
    DRAGGABLE,
    NON_DRAGGABLE
}

/**
 * Configuration for a bottom sheet, using explicit option sets for mutually exclusive behaviors.
 *
 * @param dismissBehavior Controls whether the sheet is cancellable by user actions.
 * @param expandBehavior Controls the expansion state/height of the sheet.
 * @param dragBehavior Controls whether the sheet can be dragged by the user.
 * @param content Composable content of the bottom sheet.
 */
data class BottomSheetConfig(
    val dismissBehavior: BottomSheetDismissBehavior = BottomSheetDismissBehavior.NON_CANCELLABLE,
    val expandBehavior: BottomSheetExpandBehavior = BottomSheetExpandBehavior.FULLY_EXPANDED,
    val dragBehavior: BottomSheetDragBehavior = BottomSheetDragBehavior.DRAGGABLE,
    val content: @Composable () -> Unit
)