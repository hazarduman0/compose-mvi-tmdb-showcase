package com.hazarduman.cinescope.ui.screen.example2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hazarduman.cinescope.ui.base.BaseView
import com.hazarduman.cinescope.ui.components.Spacer
import com.hazarduman.cinescope.ui.model.TopBarType
import com.hazarduman.cinescope.ui.model.BottomBarType

@Composable
fun ExampleScreen2(
    viewModel: ExampleScreen2ViewModel,
    navController: NavController
) {
    BaseView(
        viewModel = viewModel,
        navController = navController,
        topBarType = { _, onEvent ->
            TopBarType.BackTitleTopBar(
                title = "Example Screen 2",
                onBack = { onEvent(ExampleScreen2UiEvent.OnBackToExampleScreen1) }
            )
        },
        bottomBarType = { _, _ -> BottomBarType.NoBottomBar },
        compactLayout = { uiState, onEvent ->
            ExampleScreen2CompactLayout(uiState, onEvent)
        },
        expandedLayout = { uiState, onEvent ->
            ExampleScreen2ExpandedLayout(uiState, onEvent)
        }
    )
}

@Composable
private fun ExampleScreen2CompactLayout(
    uiState: ExampleScreen2UiState,
    onEvent: (ExampleScreen2UiEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(uiState.exampleText, style = MaterialTheme.typography.bodyLarge)
        Spacer(10.dp)
        Button(
            onClick = {
                onEvent(ExampleScreen2UiEvent.OnBackToExampleScreen1)
            }
        ) {
            Text("Back to Example Screen 1")
        }
    }
}

@Composable
private fun ExampleScreen2ExpandedLayout(
    uiState: ExampleScreen2UiState,
    onEvent: (ExampleScreen2UiEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(uiState.exampleText, style = MaterialTheme.typography.headlineMedium)
        Spacer(10.dp)
        Button(
            onClick = {
                onEvent(ExampleScreen2UiEvent.OnBackToExampleScreen1)
            }
        ) {
            Text("Back to Example Screen 1")
        }
    }
}