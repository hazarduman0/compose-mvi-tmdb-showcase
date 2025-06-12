package com.hazarduman.cinescope.ui.screen.example1

import com.hazarduman.cinescope.ui.model.SnackBarType

sealed class ExampleScreen1UiEvent {
    data object OnNavigateToExampleScreen2 : ExampleScreen1UiEvent()
    data class OnBottomBarItemSelected(val index: Int) : ExampleScreen1UiEvent()
    data class OnShowSnackBar(val snackBarType: SnackBarType) : ExampleScreen1UiEvent()
}