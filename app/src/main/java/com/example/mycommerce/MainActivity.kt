package com.example.mycommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycommerce.components.LoginScreen
import com.example.mycommerce.components.SignupScreen
import com.example.mycommerce.components.common.NotificationMessage
import com.example.mycommerce.ui.theme.MyCommerceTheme
import com.example.mycommerce.viewModels.MyCommerceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCommerceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyCommerceApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyCommerceApp(
    modifier: Modifier = Modifier,
) {

    val viewModel = hiltViewModel<MyCommerceViewModel>()
    val navController = rememberNavController()

    NotificationMessage(viewModel = viewModel)

    NavHost(
        navController = navController, startDestination = DestinationGraph.Signup.route
    ) {

        composable(DestinationGraph.Signup.route) {
            SignupScreen(navController = navController, modifier = modifier, viewModel = viewModel)
        }

        composable(DestinationGraph.Login.route) {
            LoginScreen(navController = navController, modifier = modifier, viewModel = viewModel)
        }

    }
}


sealed class DestinationGraph(val route: String) {
    data object Signup : DestinationGraph("signup")
    data object Login : DestinationGraph("signin")
}