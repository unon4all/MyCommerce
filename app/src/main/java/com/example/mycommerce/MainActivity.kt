package com.example.mycommerce

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycommerce.components.AddNewLocationLayout
import com.example.mycommerce.components.AdminScreen
import com.example.mycommerce.components.LocationScreen
import com.example.mycommerce.components.UserScreens
import com.example.mycommerce.components.LoginScreen
import com.example.mycommerce.components.SignupScreen
import com.example.mycommerce.components.UserProfile
import com.example.mycommerce.components.common.NotificationMessage
import com.example.mycommerce.data.models.Address
import com.example.mycommerce.ui.theme.MyCommerceTheme
import com.example.mycommerce.viewModels.MyCommerceViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@SuppressLint("MissingPermission")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // initialize the MyCommerceViewModel
    private val viewModel: MyCommerceViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        enableEdgeToEdge()
        setContent {
            MyCommerceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyCommerceApp(modifier = Modifier.padding(innerPadding),
                        fetchLocation = { fetchLocation(it) },
                        openSettings = { openAppSettings() })
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation(onLocationFetched: (Address) -> Unit) {
        lifecycleScope.launch {
            try {
                val location = getLastLocation()
                if (location != null) {
                    onLocationFetched(
                        getReadableLocation(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            context = this@MainActivity
                        )
                    )
                } else {
                    val currentLocation = getCurrentLocation()
                    onLocationFetched(
                        getReadableLocation(
                            latitude = currentLocation.latitude,
                            longitude = currentLocation.longitude,
                            context = this@MainActivity
                        )
                    )
                }
            } catch (e: Exception) {
                Log.d("geolocation", e.message.toString())
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    private suspend fun getLastLocation() = suspendCancellableCoroutine<Location?> { continuation ->
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            continuation.resume(location)
        }.addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
    }

    private suspend fun getCurrentLocation() =
        suspendCancellableCoroutine<Location> { continuation ->
            val cancellationTokenSource = CancellationTokenSource()
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                continuation.resume(location)
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
            continuation.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }
        }

    private fun getReadableLocation(
        latitude: Double, longitude: Double, context: Context
    ): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        var resultAddress = Address(userId = viewModel.userId.value)

        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                resultAddress = Address(
                    userId = viewModel.userId.value,
                    pinCode = address.postalCode ?: "",
                    city = address.locality ?: "",
                    state = address.adminArea ?: "",
                    address = "${address.premises},${address.thoroughfare}" ?: "",
                    areaName = address.subLocality ?: "",
                    country = address.countryName ?: ""
                )
            }
        } catch (e: IOException) {
            Log.d("geolocation", e.message.toString())
        }

        return resultAddress
    }

}

@Composable
fun MyCommerceApp(
    modifier: Modifier = Modifier,
    fetchLocation: (onLocationFetched: (Address) -> Unit) -> Unit,
    openSettings: () -> Unit
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

        composable(DestinationGraph.Home.route) {
            UserScreens(navController = navController, modifier = modifier, viewModel = viewModel)
        }

        composable(DestinationGraph.Admin.route) {
            AdminScreen(navController = navController, modifier = modifier, viewModel = viewModel)
        }

        composable(DestinationGraph.Profile.route) {
            UserProfile(navController = navController, modifier = modifier, viewModel = viewModel)
        }

        composable(DestinationGraph.Location.route) {
            LocationScreen(
                navController = navController, modifier = modifier, viewModel = viewModel
            )
        }

        composable(DestinationGraph.NewLocation.route) {
            AddNewLocationLayout(
                navController = navController,
                modifier = modifier,
                viewModel = viewModel,
                fetchLocation = fetchLocation,
                openSettings = openSettings
            )
        }
    }
}


sealed class DestinationGraph(val route: String) {
    data object Signup : DestinationGraph("signup")
    data object Login : DestinationGraph("signin")
    data object Home : DestinationGraph("home?selectedIndex={selectedIndex}") {
        fun createRoute(selectedIndex: Int) = "home?selectedIndex=$selectedIndex"
    }

    data object Admin : DestinationGraph("admin")
    data object Profile : DestinationGraph("profile")
    data object Location : DestinationGraph("location")
    data object NewLocation : DestinationGraph("newLocation") {
        private const val ADDRESS_ID_KEY = "addressId"

        fun createRoute(addressId: String? = null): String {
            return if (addressId != null) {
                "newLocation?$ADDRESS_ID_KEY=$addressId"
            } else {
                "newLocation"
            }
        }
    }
}

