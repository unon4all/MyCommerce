package com.example.mycommerce.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class BottomNavigationItemCompose(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
