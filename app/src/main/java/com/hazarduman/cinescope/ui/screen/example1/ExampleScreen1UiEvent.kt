package com.hazarduman.cinescope.ui.screen.example1

sealed class ExampleScreen1UiEvent {
    data object OnNavigateToExampleScreen2 : ExampleScreen1UiEvent()
}