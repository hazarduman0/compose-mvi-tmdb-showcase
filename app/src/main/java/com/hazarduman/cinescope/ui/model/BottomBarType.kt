package com.hazarduman.cinescope.ui.model

import androidx.compose.runtime.Composable

sealed class BottomBarType {
    object NoBottomBar : BottomBarType()
    data class SimpleBottomBar(
        val items: List<BottomBarItem>,
        val selectedIndex: Int = 0,
        val onItemSelected: (Int) -> Unit
    ) : BottomBarType()
}

data class BottomBarItem(
    val label: String,
    val icon: @Composable (() -> Unit)? = null
)