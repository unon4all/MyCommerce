package com.example.mycommerce

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
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
import com.example.mycommerce.data.models.UserAddressDetails
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
import android.Manifest
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val viewModel: MyCommerceViewModel by viewModels()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchLocation { location ->
                viewModel.saveAddress(location)
            }
        } else {
            openAppSettings()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Check Google Play Services availability
        if (!isGooglePlayServicesAvailable()) {
            Toast.makeText(this, "Google Play Services not available", Toast.LENGTH_LONG).show()
            return
        }

        enableEdgeToEdge()
        setContent {
            MyCommerceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyCommerceApp(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchLocation(onLocationFetched: (UserAddressDetails) -> Unit) {
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
                Log.e("geolocation", "Error fetching location: ${e.message}")
            }
        }
    }

    fun checkLocationPermission(onPermissionGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGranted()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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
    ): UserAddressDetails {
        val geocoder = Geocoder(context, Locale.getDefault())
        var resultAddress = UserAddressDetails(userId = viewModel.userId.value)

        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                resultAddress = UserAddressDetails(
                    userId = viewModel.userId.value,
                    address = Address(
                        pinCode = address.postalCode ?: "",
                        city = address.locality ?: "",
                        state = address.adminArea ?: "",
                        addressLine = "${address.premises},${address.thoroughfare}",
                        areaName = address.subLocality ?: "",
                    ),
                )
            }
        } catch (e: IOException) {
            Log.e("geolocation", "Error getting readable location: ${e.message}")
        }

        return resultAddress
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)
        return resultCode == ConnectionResult.SUCCESS
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
    data object NewLocation : DestinationGraph("newLocation")
}

