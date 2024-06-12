package com.example.mycommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycommerce.ui.theme.MyCommerceTheme

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

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = DestinationGraph.Signup.route
    ) {

        composable(DestinationGraph.Signup.route) {

        }

        composable(DestinationGraph.Signin.route) {

        }

    }
}


sealed class DestinationGraph(val route: String) {
    data object Signup : DestinationGraph("signup")
    data object Signin : DestinationGraph("signin")
}