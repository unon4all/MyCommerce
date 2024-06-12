package com.example.mycommerce.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mycommerce.data.BottomNavigationItemCompose
import com.example.mycommerce.viewModels.MyCommerceViewModel


@Composable
fun UserScreens(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MyCommerceViewModel,
) {

    val items = listOf(
        BottomNavigationItemCompose(
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
        ),
        BottomNavigationItemCompose(
            selectedIcon = Icons.Filled.AddShoppingCart,
            unselectedIcon = Icons.Outlined.AddShoppingCart,
        ),
        BottomNavigationItemCompose(
            selectedIcon = Icons.Filled.History,
            unselectedIcon = Icons.Outlined.History,
        ),
    )

    var selectedIndexDemo by rememberSaveable {
        mutableIntStateOf(
            navController.currentBackStackEntry?.arguments?.getString("selectedIndex")
                ?.toIntOrNull() ?: 0
        )
    }

    Scaffold(bottomBar = {
        NavigationBar(modifier = modifier) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(selected = selectedIndexDemo == index,
                    onClick = { selectedIndexDemo = index },
                    icon = {
                        Icon(
                            imageVector = if (selectedIndexDemo == index) item.selectedIcon else item.unselectedIcon,
                            contentDescription = null
                        )
                    })
            }
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (selectedIndexDemo) {
                0 -> ShoppingScreen(navController = navController, viewModel = viewModel)
                1 -> AddToCartScreen(navController = navController, viewModel = viewModel)
                else -> HistoryScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}