package com.hazarduman.cinescope.ui.model

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable

sealed class FloatingActionButtonType {
    object NoFab : FloatingActionButtonType()
    data class SimpleFab(
        val onClick: () -> Unit,
        val content: @Composable () -> Unit,
        val fabPosition: FabPosition = FabPosition.End
    ) : FloatingActionButtonType()
    data class ExtendedFab(
        val onClick: () -> Unit,
        val text: String,
        val icon: (@Composable () -> Unit)? = null,
        val fabPosition: FabPosition = FabPosition.End
    ) : FloatingActionButtonType()
    data class CustomFab(
        val content: @Composable () -> Unit,
        val fabPosition: FabPosition = FabPosition.End
    ) : FloatingActionButtonType()
}