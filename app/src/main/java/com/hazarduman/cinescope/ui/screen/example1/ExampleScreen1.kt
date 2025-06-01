package com.hazarduman.cinescope.ui.screen.example1

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

@Composable
fun ExampleScreen1(
    viewModel: ExampleScreen1ViewModel,
    navController: NavController
) {
    BaseView(viewModel = viewModel, navController = navController) { uiState, onEvent ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(uiState.exampleText, style = MaterialTheme.typography.bodyLarge)
            Spacer(10.dp)
            Button(
                onClick = {
                    onEvent(ExampleScreen1UiEvent.OnNavigateToExampleScreen2)
                }
            ) {
                Text("Navigate to Example Screen 2")
            }
        }
    }
}