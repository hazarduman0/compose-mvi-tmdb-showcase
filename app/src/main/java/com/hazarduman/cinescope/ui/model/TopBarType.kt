package com.hazarduman.cinescope.ui.model

import androidx.compose.runtime.Composable

sealed class TopBarType {
    object NoTopBar : TopBarType()
    data class BackTopBar(
        val onBack: () -> Unit,
        val titleCentered: Boolean = true,
        val actions: (@Composable () -> Unit)? = null
    ) : TopBarType()
    data class CloseTopBar(
        val onClose: () -> Unit,
        val titleCentered: Boolean = true,
        val actions: (@Composable () -> Unit)? = null
    ) : TopBarType()
    data class TitleTopBar(
        val title: String,
        val titleCentered: Boolean = true,
        val actions: (@Composable () -> Unit)? = null
    ) : TopBarType()
    data class BackTitleTopBar(
        val title: String,
        val onBack: () -> Unit,
        val titleCentered: Boolean = true,
        val actions: (@Composable () -> Unit)? = null
    ) : TopBarType()
    data class CustomTopBar(
        val content: @Composable () -> Unit
    ) : TopBarType()
}