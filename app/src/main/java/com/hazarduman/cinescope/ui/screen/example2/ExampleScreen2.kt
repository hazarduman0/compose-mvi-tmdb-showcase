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
import com.hazarduman.cinescope.ui.components.Spacer

@Composable
fun ExampleScreen2(
    navController: NavController
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Example Screen 2", style = MaterialTheme.typography.bodyLarge)
        Spacer(10.dp)
        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Back to Example Screen 1")
        }
    }
}