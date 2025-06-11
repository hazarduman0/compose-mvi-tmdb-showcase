package com.hazarduman.cinescope.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.hazarduman.cinescope.ui.navigation.NavigationType
import kotlinx.coroutines.flow.collectLatest

/**
 * A generic base Composable for screens, handling UI state, navigation, and UI events.
 *
 * @param E The type of events handled by the ViewModel.
 * @param S The type of UI state exposed by the ViewModel.
 * @param VM The ViewModel type, extending [BaseViewModel].
 * @param viewModel The ViewModel instance.
 * @param navController The NavController for navigation actions.
 * @param content The Composable content, receiving the current UI state and an event dispatcher.
 */
@Composable
fun <E, S, VM : BaseViewModel<E, S>> BaseView(
    viewModel: VM,
    navController: NavController,
    content: @Composable (uiState: S, onEvent: (E) -> Unit) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HandleNavigationEvents(viewModel, navController)
    HandleUiEvents(viewModel)

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            content(uiState) { event -> viewModel.onEvent(event) }
        }
    }
}

/**
 * Collects and handles navigation events from the ViewModel.
 */
@Composable
private fun <E, S> HandleNavigationEvents(
    viewModel: BaseViewModel<E, S>,
    navController: NavController
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collectLatest { command ->
            when (command) {
                is NavigationType.To -> {
                    navController.navigate(
                        command.route,
                        navOptions = navOptions { launchSingleTop = true }
                    )
                }
                is NavigationType.Back -> {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        navController.popBackStack()
                    }
                }
                is NavigationType.BackTo -> {
                    // TODO: Implement BackTo navigation
                }
                NavigationType.BackToRoot -> {
                    // TODO: Implement BackToRoot navigation
                }
                NavigationType.Exit -> {
                    // TODO: Implement Exit navigation
                }
                is NavigationType.NavigateForResult -> {
                    // TODO: Implement NavigateForResult
                }
                NavigationType.PopUpToInclusive -> {
                    // TODO: Implement PopUpToInclusive
                }
                is NavigationType.Replace -> {
                    // TODO: Implement Replace navigation
                }
            }
        }
    }
}

/**
 * Collects and handles UI events from the ViewModel.
 */
@Composable
private fun <E, S> HandleUiEvents(
    viewModel: BaseViewModel<E, S>
) {
    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    // TODO: Implement ShowSnackBar
                }
                is UiEvent.ShowBottomSheet -> {
                    // TODO: Implement ShowBottomSheet
                }
                is UiEvent.ShowDialog -> {
                    // TODO: Implement ShowDialog
                }
                is UiEvent.CopyToClipboard -> {
                    // TODO: Implement CopyToClipboard
                }
                UiEvent.HideBottomSheet -> {
                    // TODO: Implement HideBottomSheet
                }
                UiEvent.HideDialog -> {
                    // TODO: Implement HideDialog
                }
                UiEvent.HideLoading -> {
                    // TODO: Implement HideLoading
                }
                is UiEvent.NavigateToSettings -> {
                    // TODO: Implement NavigateToSettings
                }
                is UiEvent.OpenUrl -> {
                    // TODO: Implement OpenUrl
                }
                is UiEvent.PermissionRequest -> {
                    // TODO: Implement PermissionRequest
                }
                is UiEvent.Share -> {
                    // TODO: Implement Share
                }
                is UiEvent.ShowAlertDialog -> {
                    // TODO: Implement ShowAlertDialog
                }
                UiEvent.ShowLoading -> {
                    // TODO: Implement ShowLoading
                }
                is UiEvent.ShowToast -> {
                    // TODO: Implement ShowToast
                }
            }
        }
    }
}
