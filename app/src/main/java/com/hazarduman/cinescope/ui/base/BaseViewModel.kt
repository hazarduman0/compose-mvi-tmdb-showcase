package com.hazarduman.cinescope.ui.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazarduman.cinescope.ui.model.SnackBarType
import com.hazarduman.cinescope.ui.navigation.NavigationType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel class providing state, navigation, and UI event management.
 *
 * @param E The type of events handled by the ViewModel.
 * @param S The type of UI state managed by the ViewModel.
 * @property initialState The initial state of the UI.
 */
open class BaseViewModel<E, S>(
    initialState: S
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    /**
     * Exposes the current UI state as an immutable [StateFlow].
     */
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<NavigationType>(Channel.BUFFERED)
    /**
     * Emits navigation commands to be handled by the UI layer.
     */
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    /**
     * Emits UI events (snackBar, dialog, etc.) to be handled by the UI layer.
     */
    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * Handles events from the UI. Should be overridden in subclasses.
     *
     * @param event The event to handle.
     */
    open fun onEvent(event: E) {
        // To be implemented by subclasses
    }

    /**
     * Sends a navigation command to be handled by the UI.
     *
     * @param command The navigation command.
     */
    fun navigate(command: NavigationType) {
        viewModelScope.launch {
            _navigationEvent.send(command)
        }
    }

    /**
     * Sends a UI event to be handled by the UI.
     *
     * @param event The UI event.
     */
    fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    /**
     * Updates the current UI state.
     *
     * @param newState The new state to set.
     */
    protected fun updateState(newState: S) {
        _uiState.value = newState
    }

    /**
     * Holds the last SnackBarType in the ViewModel.
     */
    var lastSnackBarType: SnackBarType? by mutableStateOf(null)
}
