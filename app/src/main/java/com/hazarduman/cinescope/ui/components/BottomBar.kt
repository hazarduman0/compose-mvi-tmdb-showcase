package com.hazarduman.cinescope.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hazarduman.cinescope.core.util.NoRippleInteractionSource
import com.hazarduman.cinescope.ui.model.BottomBarType

@Composable
fun BottomBar(
    bottomBarType: BottomBarType
) {
    when (bottomBarType) {
        is BottomBarType.NoBottomBar -> {
            // No bottom bar to display
        }

        is BottomBarType.SimpleBottomBar -> {
            NavigationBar {
                bottomBarType.items.forEachIndexed { index, item ->
                    val selectedColor = MaterialTheme.colorScheme.primary
                    val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                    NavigationBarItem(
                        selected = index == bottomBarType.selectedIndex,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = selectedColor,
                            selectedTextColor = selectedColor,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor,
                            indicatorColor = Color.Transparent
                        ),
                        onClick = { bottomBarType.onItemSelected(index) },
                        icon = {
                            item.icon?.invoke()
                        },
                        label = {
                            Text(
                                item.label,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        interactionSource = NoRippleInteractionSource()
                    )
                }
            }
        }
    }
}