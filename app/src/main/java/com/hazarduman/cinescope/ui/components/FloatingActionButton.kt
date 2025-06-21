package com.hazarduman.cinescope.ui.components

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.hazarduman.cinescope.ui.model.FloatingActionButtonType

@Composable
fun AppFloatingActionButton(fabType: FloatingActionButtonType) {
    when (fabType) {
        is FloatingActionButtonType.SimpleFab -> FloatingActionButton(onClick = fabType.onClick) {
            fabType.content()
        }
        is FloatingActionButtonType.ExtendedFab -> ExtendedFloatingActionButton(
            onClick = fabType.onClick,
            icon = { fabType.icon?.invoke() },
            text = { Text(fabType.text) },
        )
        is FloatingActionButtonType.CustomFab -> fabType.content()
        FloatingActionButtonType.NoFab -> {
            // No floating action button
        }
    }
}