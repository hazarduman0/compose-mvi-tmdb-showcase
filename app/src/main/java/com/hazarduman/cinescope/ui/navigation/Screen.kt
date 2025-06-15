package com.hazarduman.cinescope.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Screen: NavKey {
    @Serializable
    data object ExampleScreen1 : Screen()
    @Serializable
    data class ExampleScreen2(val data: String) : Screen()
}