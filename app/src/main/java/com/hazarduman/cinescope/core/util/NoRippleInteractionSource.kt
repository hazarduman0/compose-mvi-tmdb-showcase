package com.hazarduman.cinescope.core.util

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {
        // No-op, no ripple effect
    }

    override fun tryEmit(interaction: Interaction): Boolean {
        return true // Always succeed in emitting interaction
    }
}