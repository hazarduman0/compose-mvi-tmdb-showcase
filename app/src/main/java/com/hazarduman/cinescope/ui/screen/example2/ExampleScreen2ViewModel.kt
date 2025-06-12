package com.hazarduman.cinescope.ui.screen.example2

import com.hazarduman.cinescope.ui.base.BaseViewModel
import com.hazarduman.cinescope.ui.navigation.NavigationType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExampleScreen2ViewModel @Inject constructor() :
    BaseViewModel<ExampleScreen2UiEvent, ExampleScreen2UiState>(ExampleScreen2UiState()) {

    override fun onEvent(event: ExampleScreen2UiEvent) {
        when (event) {
            is ExampleScreen2UiEvent.OnBackToExampleScreen1 -> {
                onBackToExampleScreen1()
            }
        }
    }

    private fun onBackToExampleScreen1() {
        navigate(NavigationType.Back)
    }
}