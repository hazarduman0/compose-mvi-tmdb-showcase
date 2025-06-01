package com.hazarduman.cinescope.ui.base

sealed class NavigationCommand {
    data class To(val route: String, val args: Map<String, String> = emptyMap()) : NavigationCommand()
    data object Back : NavigationCommand()
    data class BackTo(val route: String) : NavigationCommand()
    data object BackToRoot : NavigationCommand()
    data object PopUpToInclusive : NavigationCommand()
    data class Replace(val route: String, val args: Map<String, String> = emptyMap()) : NavigationCommand()
    data class NavigateForResult(val route: String, val requestKey: String) : NavigationCommand()
    data object Exit : NavigationCommand()
}

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
    data object ShowBottomSheet : UiEvent()
    data object ShowDialog : UiEvent()
    data class ShowToast(val message: String) : UiEvent()
    data class ShowAlertDialog(val title: String, val message: String) : UiEvent()
    data object HideBottomSheet : UiEvent()
    data object HideDialog : UiEvent()
    data class NavigateToSettings(val reason: String? = null) : UiEvent()
    data object ShowLoading : UiEvent()
    data object HideLoading : UiEvent()
    data class CopyToClipboard(val text: String) : UiEvent()
    data class Share(val text: String) : UiEvent()
    data class OpenUrl(val url: String) : UiEvent()
    data class PermissionRequest(val permission: String) : UiEvent()
}