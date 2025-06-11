package com.hazarduman.cinescope.ui.navigation

sealed class NavigationType {
    data class To(val route: String, val args: Map<String, String> = emptyMap()) : NavigationType()
    data object Back : NavigationType()
    data class BackTo(val route: String) : NavigationType()
    data object BackToRoot : NavigationType()
    data object PopUpToInclusive : NavigationType()
    data class Replace(val route: String, val args: Map<String, String> = emptyMap()) : NavigationType()
    data class NavigateForResult(val route: String, val requestKey: String) : NavigationType()
    data object Exit : NavigationType()
}