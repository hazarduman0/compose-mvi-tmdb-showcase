package com.hazarduman.cinescope.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.hazarduman.cinescope.ui.model.SnackBarType

@Composable
fun SnackBar(snackBarType: SnackBarType, data: SnackbarData) {
    val backgroundColor = when (snackBarType) {
        is SnackBarType.Info -> MaterialTheme.colorScheme.surfaceVariant
        is SnackBarType.Error -> MaterialTheme.colorScheme.error
        is SnackBarType.Success -> MaterialTheme.colorScheme.primary
    }
    val contentColor = when (snackBarType) {
        is SnackBarType.Info -> MaterialTheme.colorScheme.onSurfaceVariant
        is SnackBarType.Error -> MaterialTheme.colorScheme.onError
        is SnackBarType.Success -> MaterialTheme.colorScheme.onPrimary
    }

    val isActionRequired = snackBarType.actionLabel != null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(backgroundColor, MaterialTheme.shapes.extraSmall)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                snackBarType.message,
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = if (isActionRequired) Modifier.weight(0.7f) else Modifier.weight(1f)
            )
            snackBarType.actionLabel?.let { actionLabel ->
                Spacer(Modifier.weight(0.05f))
                TextButton(
                    modifier = Modifier.weight(0.25f),
                    onClick = { data.performAction() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = contentColor
                    )
                ) {
                    Text(
                        text = actionLabel,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
            }
        }
    }
}
