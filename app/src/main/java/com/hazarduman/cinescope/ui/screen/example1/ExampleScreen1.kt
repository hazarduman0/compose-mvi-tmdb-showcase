package com.hazarduman.cinescope.ui.screen.example1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import com.hazarduman.cinescope.R
import com.hazarduman.cinescope.ui.base.BaseView
import com.hazarduman.cinescope.ui.model.BottomBarItem
import com.hazarduman.cinescope.ui.model.BottomBarType
import com.hazarduman.cinescope.ui.model.BottomSheetConfig
import com.hazarduman.cinescope.ui.model.FloatingActionButtonType
import com.hazarduman.cinescope.ui.model.SnackBarType
import com.hazarduman.cinescope.ui.model.TopBarType

@Composable
fun ExampleScreen1(
    viewModel: ExampleScreen1ViewModel,
    backStack: NavBackStack
) {
    BaseView(
        viewModel = viewModel,
        backStack = backStack,
        topBarType = { _, _ ->
            TopBarType.TitleTopBar("Example Screen 1")
        },
        bottomBarType = { uiState, onEvent ->
            BottomBarType.SimpleBottomBar(
                items = listOf(
                    BottomBarItem(
                        label = "Home",
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_home),
                                contentDescription = "Home",
                            )
                        }
                    ),
                    BottomBarItem(
                        label = "Profile",
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = "Profile"
                            )
                        }
                    ),
                    BottomBarItem(
                        label = "Settings",
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = "Settings"
                            )
                        }
                    )
                ),
                selectedIndex = uiState.selectedIndex,
                onItemSelected = { index ->
                    onEvent(ExampleScreen1UiEvent.OnBottomBarItemSelected(index))
                }
            )
        },
        floatingActionButtonType = { _, onEvent ->
            FloatingActionButtonType.ExtendedFab(
                onClick = {
                    // Handle FAB click
                },
                text = "Add",
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
            )
        },
        compactLayout = { uiState, onEvent ->
            ExampleScreen1CompactLayout(uiState, onEvent)
        },
        expandedLayout = { uiState, onEvent ->
            ExampleScreen1ExpandedLayout(uiState, onEvent)
        }
    )
}

@Composable
private fun ExampleScreen1CompactLayout(
    uiState: ExampleScreen1UiState,
    onEvent: (ExampleScreen1UiEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(uiState.exampleText, style = MaterialTheme.typography.bodyLarge)
        SnackBarButtonRow(onEvent = onEvent)
        Button(
            onClick = {
                onEvent(
                    ExampleScreen1UiEvent.OnShowBottomSheet(
                        bottomSheetConfig = BottomSheetConfig(
                            content = { BottomSheetContent(onEvent) }
                        )
                    ))
            }
        ) {
            Text("Show Bottom Sheet")
        }
        Button(
            onClick = {
                onEvent(ExampleScreen1UiEvent.OnNavigateToExampleScreen2)
            }
        ) {
            Text("Navigate to Example Screen 2")
        }
    }
}

@Composable
private fun ExampleScreen1ExpandedLayout(
    uiState: ExampleScreen1UiState,
    onEvent: (ExampleScreen1UiEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(uiState.exampleText, style = MaterialTheme.typography.bodyLarge)
        SnackBarButtonRow(onEvent = onEvent)
        Button(
            onClick = {
                onEvent(
                    ExampleScreen1UiEvent.OnShowBottomSheet(
                        bottomSheetConfig = BottomSheetConfig(
                            content = { BottomSheetContent(onEvent) }
                        )
                    ))
            }
        ) {
            Text("Show Bottom Sheet")
        }
        Button(
            onClick = {
                onEvent(ExampleScreen1UiEvent.OnNavigateToExampleScreen2)
            }
        ) {
            Text("Navigate to Example Screen 2")
        }
    }
}

@Composable
private fun BottomSheetContent(
    onEvent: (ExampleScreen1UiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Text("This is a bottom sheet", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { onEvent(ExampleScreen1UiEvent.OnCloseBottomSheet) }) {
            Text("Close Bottom Sheet")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SnackBarButtonRow(
    onEvent: (ExampleScreen1UiEvent) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Button(
            onClick = {
                onEvent(
                    ExampleScreen1UiEvent.OnShowSnackBar(
                        SnackBarType.Info("This is an info snack bar", "Dismiss")
                    )
                )
            }
        ) {
            Text("Show Info Snack Bar")
        }
        Button(
            onClick = {
                onEvent(
                    ExampleScreen1UiEvent.OnShowSnackBar(
                        SnackBarType.Error("This is an error snack bar", "Dismiss")
                    )
                )
            }
        ) {
            Text("Show Error Snack Bar")
        }
        Button(
            onClick = {
                onEvent(
                    ExampleScreen1UiEvent.OnShowSnackBar(
                        SnackBarType.Success("This is a success snack bar", "Dismiss")
                    )
                )
            }
        ) {
            Text("Show Success Snack Bar")
        }
    }
}