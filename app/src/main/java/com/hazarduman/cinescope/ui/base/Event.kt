package com.hazarduman.cinescope.ui.base

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