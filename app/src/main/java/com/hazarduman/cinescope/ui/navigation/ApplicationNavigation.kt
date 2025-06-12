package com.hazarduman.cinescope.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hazarduman.cinescope.ui.screen.example1.ExampleScreen1
import com.hazarduman.cinescope.ui.screen.example1.ExampleScreen1ViewModel
import com.hazarduman.cinescope.ui.screen.example2.ExampleScreen2
import com.hazarduman.cinescope.ui.screen.example2.ExampleScreen2ViewModel

private const val ANIMATION_DURATION = 500

/**
 * Handles the application's navigation graph and transitions.
 *
 * @param navController The [NavHostController] used for navigation.
 */

@Composable
fun ApplicationNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.EXAMPLE_SCREEN_ROUTE_1.name,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        }
    ) {
        composable(Route.EXAMPLE_SCREEN_ROUTE_1.name) {
            val viewModel = hiltViewModel<ExampleScreen1ViewModel>()
            ExampleScreen1(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Route.EXAMPLE_SCREEN_ROUTE_2.name) {
            val viewModel = hiltViewModel<ExampleScreen2ViewModel>()
            ExampleScreen2(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}
