package com.hazarduman.cinescope.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.navEntryDecorator
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.hazarduman.cinescope.ui.screen.example1.ExampleScreen1
import com.hazarduman.cinescope.ui.screen.example1.ExampleScreen1ViewModel
import com.hazarduman.cinescope.ui.screen.example2.ExampleScreen2
import com.hazarduman.cinescope.ui.screen.example2.ExampleScreen2ViewModel

private const val ANIMATION_DURATION = 500

/**
 * Handles the application's navigation graph and transitions.
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ApplicationNavigation() {

    val localNavSharedTransitionScope: ProvidableCompositionLocal<SharedTransitionScope> =
        compositionLocalOf {
            throw IllegalStateException(
                "Unexpected access to LocalNavSharedTransitionScope. You must provide a " +
                        "SharedTransitionScope from a call to SharedTransitionLayout() or " +
                        "SharedTransitionScope()"
            )
        }

    /**
     * A [navEntryDecorator] that wraps each entry in a shared element that is controlled by the
     * [Scene].
     */
    val sharedEntryInSceneNavEntryDecorator = navEntryDecorator<NavKey> { entry ->
        with(localNavSharedTransitionScope.current) {
            Box(
                Modifier.sharedElement(
                    rememberSharedContentState(entry.contentKey),
                    animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                ),
            ) {
                entry.Content()
            }
        }
    }

    val backStack = rememberNavBackStack(Screen.ExampleScreen1)
    val twoPaneStrategy = remember { TwoPaneSceneStrategy<Any>() }

    SharedTransitionLayout {
        CompositionLocalProvider(localNavSharedTransitionScope provides this) {
            NavDisplay(
                backStack = backStack,
                onBack = { keysToRemove -> repeat(keysToRemove) { backStack.removeLastOrNull() } },
                entryDecorators = listOf(
                    sharedEntryInSceneNavEntryDecorator,
                    rememberSceneSetupNavEntryDecorator(),
                    rememberSavedStateNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                sceneStrategy = twoPaneStrategy,
                transitionSpec = {
                    // Slide new content up, keeping the old content in place underneath
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(ANIMATION_DURATION)
                    ) togetherWith ExitTransition.KeepUntilTransitionsFinished
                },
                popTransitionSpec = {
                    // Slide old content down, revealing the new content in place underneath
                    EnterTransition.None togetherWith
                            slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(ANIMATION_DURATION)
                            )
                },
                entryProvider = entryProvider {
                    entry<Screen.ExampleScreen1>(
                        metadata = TwoPaneScene.twoPane()
                    ) {
                        val viewModel = hiltViewModel<ExampleScreen1ViewModel>()
                        ExampleScreen1(
                            viewModel = viewModel,
                            backStack = backStack
                        )
                    }
                    entry<Screen.ExampleScreen2>(
                        metadata = TwoPaneScene.twoPane()
                    ) { key ->
                        val viewModel =
                            hiltViewModel<ExampleScreen2ViewModel, ExampleScreen2ViewModel.Factory>(
                                creationCallback = { factory ->
                                    factory.create(key)
                                }
                            )

                        ExampleScreen2(
                            viewModel = viewModel,
                            backStack = backStack
                        )
                    }
                }
            )
        }
    }
}
