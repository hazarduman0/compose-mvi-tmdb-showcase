package com.hazarduman.cinescope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hazarduman.cinescope.ui.navigation.ApplicationNavigation
import com.hazarduman.cinescope.ui.theme.CineScopeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeTheme {
                ApplicationNavigation()
            }
        }
    }
}