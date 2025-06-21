package com.hazarduman.cinescope.ui.base

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.hazarduman.cinescope.ui.components.AppFloatingActionButton
import com.hazarduman.cinescope.ui.components.BottomBar
import com.hazarduman.cinescope.ui.components.SnackBar
import com.hazarduman.cinescope.ui.components.TopAppBar
import com.hazarduman.cinescope.ui.model.BottomBarType
import com.hazarduman.cinescope.ui.model.FloatingActionButtonType
import com.hazarduman.cinescope.ui.model.SnackBarType
import com.hazarduman.cinescope.ui.model.TopBarType
import com.hazarduman.cinescope.ui.navigation.NavigationType
import com.hazarduman.cinescope.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Generic base Composable for screens. Handles UI state, navigation, and UI events.
 *
 * @param E The type of events handled by the ViewModel.
 * @param S The type of UI state exposed by the ViewModel.
 * @param VM The ViewModel type, extending [BaseViewModel].
 * @param viewModel The ViewModel instance.
 * @param backStack The Navigation 3 back stack.
 * @param topBarType Lambda to provide the top bar type based on UI state and event dispatcher.
 * @param bottomBarType Lambda to provide the bottom bar type based on UI state and event dispatcher.
 * @param floatingActionButtonType Lambda to provide the floating action button type based on UI state and event dispatcher.
 * @param compactLayout Composable layout for compact screens.
 * @param expandedLayout Composable layout for expanded screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <E, S, VM : BaseViewModel<E, S>> BaseView(
    viewModel: VM,
    backStack: NavBackStack,
    topBarType: @Composable (uiState: S, onEvent: (E) -> Unit) -> TopBarType = { _, _ -> TopBarType.NoTopBar },
    bottomBarType: @Composable (uiState: S, onEvent: (E) -> Unit) -> BottomBarType = { _, _ -> BottomBarType.NoBottomBar },
    floatingActionButtonType: @Composable (uiState: S, onEvent: (E) -> Unit) -> FloatingActionButtonType = { _, _ -> FloatingActionButtonType.NoFab },
    compactLayout: @Composable (uiState: S, onEvent: (E) -> Unit) -> Unit,
    expandedLayout: @Composable (uiState: S, onEvent: (E) -> Unit) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    HandleNavigationEvents(
        viewModel = viewModel,
        backStack = backStack
    )

    HandleUiEvents(
        viewModel = viewModel,
        snackBarHostState = snackBarHostState,
        coroutineScope = coroutineScope
    )

    val isExpanded = isExpandedScreen()

    Scaffold(
        topBar = {
            TopAppBar(
                topBarType = topBarType(uiState) { event -> viewModel.onEvent(event) }
            )
        },
        bottomBar = {
            BottomBar(bottomBarType(uiState) { event -> viewModel.onEvent(event) })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data: SnackbarData ->
                val snackBarType = viewModel.lastSnackBarType ?: SnackBarType.Info(
                    data.visuals.message,
                    data.visuals.actionLabel
                )
                SnackBar(snackBarType = snackBarType, data = data)
            }
        },
        floatingActionButton = {
            AppFloatingActionButton(
                floatingActionButtonType(uiState) { event -> viewModel.onEvent(event) }
            )
        },
        floatingActionButtonPosition = when (val fabType = floatingActionButtonType(uiState) { event -> viewModel.onEvent(event) }) {
            is FloatingActionButtonType.SimpleFab -> fabType.fabPosition
            is FloatingActionButtonType.ExtendedFab -> fabType.fabPosition
            is FloatingActionButtonType.CustomFab -> fabType.fabPosition
            else -> androidx.compose.material3.FabPosition.End
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            if (isExpanded) {
                expandedLayout(uiState) { event -> viewModel.onEvent(event) }
            } else {
                compactLayout(uiState) { event -> viewModel.onEvent(event) }
            }
        }
    }
}

/**
 * Returns true if the device should use the expanded layout (e.g., tablet, foldable).
 * Uses 600dp as the threshold for expanded screens.
 */
@Composable
private fun isExpandedScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.smallestScreenWidthDp >= 600
}

/**
 * Collects and handles navigation events from the ViewModel, updating the back stack accordingly.
 */
@Composable
private fun <E, S> HandleNavigationEvents(
    viewModel: BaseViewModel<E, S>,
    backStack: NavBackStack
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collectLatest { command ->
            when (command) {
                is NavigationType.To -> {
                    backStack.addIfNotOnTop(command.screen)
                }

                is NavigationType.Back -> {
                    backStack.popIfNotEmpty()
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
    viewModel: BaseViewModel<E, S>,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    LaunchedEffect(viewModel) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    coroutineScope.launch {
                        snackBarHostState.currentSnackbarData?.let {
                            it.dismiss()
                            delay(100)
                        }

                        viewModel.lastSnackBarType = event.snackBarType

                        snackBarHostState.showSnackbar(
                            message = event.snackBarType.message,
                            actionLabel = event.snackBarType.actionLabel
                        )
                    }
                }

                is UiEvent.ShowDialog -> {
                    // TODO: Implement ShowDialog
                }

                is UiEvent.CopyToClipboard -> {
                    // TODO: Implement CopyToClipboard
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

private fun SnapshotStateList<NavKey>.addIfNotOnTop(screen: Screen) {
    if (lastOrNull() != screen) {
        add(screen)
    }
}

private fun SnapshotStateList<NavKey>.popIfNotEmpty() {
    if (size > 1) {
        removeLastOrNull()
    } else {
        Log.w(
            "BaseView",
            "Attempted to remove last item from back stack, but only one item remains."
        )
    }
}
