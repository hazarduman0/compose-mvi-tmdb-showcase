package com.hazarduman.cinescope.ui.model

sealed class SnackBarType(
    open val message: String,
    open val actionLabel: String? = null
) {
    data class Info(
        override val message: String,
        override val actionLabel: String? = null
    ) : SnackBarType(message, actionLabel)

    data class Error(
        override val message: String,
        override val actionLabel: String? = null
    ) : SnackBarType(message, actionLabel)

    data class Success(
        override val message: String,
        override val actionLabel: String? = null
    ) : SnackBarType(message, actionLabel)
}