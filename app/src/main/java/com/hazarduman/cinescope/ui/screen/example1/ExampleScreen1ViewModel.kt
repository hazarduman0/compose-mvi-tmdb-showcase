package com.hazarduman.cinescope.ui.screen.example1

import com.hazarduman.cinescope.ui.base.BaseViewModel
import com.hazarduman.cinescope.ui.base.UiEvent
import com.hazarduman.cinescope.ui.navigation.NavigationType
import com.hazarduman.cinescope.ui.navigation.Route
import com.hazarduman.cinescope.ui.model.SnackBarType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExampleScreen1ViewModel @Inject constructor() :
    BaseViewModel<ExampleScreen1UiEvent, ExampleScreen1UiState>(ExampleScreen1UiState()) {

    override fun onEvent(event: ExampleScreen1UiEvent) {
        when (event) {
            is ExampleScreen1UiEvent.OnNavigateToExampleScreen2 -> {
                navigateToExampleScreen2()
            }

            is ExampleScreen1UiEvent.OnBottomBarItemSelected -> {
                updateState(uiState.value.copy(selectedIndex = event.index))
            }

            is ExampleScreen1UiEvent.OnShowSnackBar -> onShowSnackBar(event.snackBarType)
        }
    }

    private fun navigateToExampleScreen2() {
        navigate(
            NavigationType.To(
                route = Route.EXAMPLE_SCREEN_ROUTE_2.name,
                args = mapOf()
            )
        )
    }

    private fun onShowSnackBar(snackBarType: SnackBarType) {
        sendUiEvent(UiEvent.ShowSnackBar(snackBarType))
    }
}