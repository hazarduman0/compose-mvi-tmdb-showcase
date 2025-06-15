package com.hazarduman.cinescope.ui.screen.example2

import com.hazarduman.cinescope.ui.base.BaseViewModel
import com.hazarduman.cinescope.ui.navigation.NavigationType
import com.hazarduman.cinescope.ui.navigation.Screen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ExampleScreen2ViewModel.Factory::class)
class ExampleScreen2ViewModel @AssistedInject constructor(
    @Assisted val navKey: Screen.ExampleScreen2
) :
    BaseViewModel<ExampleScreen2UiEvent, ExampleScreen2UiState>(ExampleScreen2UiState()) {

    @AssistedFactory
    interface Factory {
        fun create(navKey: Screen.ExampleScreen2): ExampleScreen2ViewModel
    }

    init {
        setNavKey(navKey)
    }

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

    private fun setNavKey(navKey: Screen.ExampleScreen2) {
        updateState(uiState.value.copy(navKey = navKey.data))
    }
}