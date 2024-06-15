package com.example.mycommerce.components

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mycommerce.DestinationGraph
import com.example.mycommerce.components.common.CommonOutlineTextfield
import com.example.mycommerce.data.models.Address
import com.example.mycommerce.viewModels.MyCommerceViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier, navController: NavHostController, viewModel: MyCommerceViewModel
) {
    val addressList by viewModel.addressList.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = "My Addresses") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate(DestinationGraph.NewLocation.route)
            }, modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Address")
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .imePadding()
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (addressList.isNotEmpty()) {
                    addressList.forEach { address ->
                        AddressItem(address = address, onItemClick = { selectedAddress ->
                            navController.navigate(
                                DestinationGraph.NewLocation.createRoute(selectedAddress.id.toString())
                            )
                        })
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                } else {
                    Text(
                        text = "No addresses found",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun AddressItem(
    address: Address, onItemClick: (Address) -> Unit // Callback to handle item click
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        onClick = { onItemClick(address) } // Pass the address when clicked
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Full Name: ${address.fullName}", style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Phone Number: ${address.phoneNumber}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Address: ${address.address}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddNewLocationLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MyCommerceViewModel,
    fetchLocation: (onLocationFetched: (Address) -> Unit) -> Unit,
    openSettings: () -> Unit
) {
    var permissionResultText by remember { mutableStateOf("Requesting location permission...") }

    val permissionState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    )

    val userId by viewModel.userId.collectAsState()

    var address by remember {
        mutableStateOf(Address(userId = userId))
    }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(permissionState) {
        permissionState.launchMultiplePermissionRequest()
    }

    LaunchedEffect(permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            fetchLocation { fetchedAddress ->
                address = fetchedAddress
                permissionResultText = "Permission Granted"
            }
        } else {
            permissionResultText = "Permission Denied :("
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = "Add New Address") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
            }
        })
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .imePadding()
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                CommonOutlineTextfield(
                    value = address.fullName,
                    onValueChange = { address = address.copy(fullName = it) },
                    label = "Full Name",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus(force = true)
                    }),
                    placeholder = "Enter Full Name"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonOutlineTextfield(
                    value = address.phoneNumber,
                    onValueChange = { address = address.copy(phoneNumber = it) },
                    label = "Phone Number",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus(force = true)
                    }),
                    placeholder = "Enter Phone Number"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonOutlineTextfield(
                    value = address.alternateNumber,
                    onValueChange = { address = address.copy(alternateNumber = it) },
                    label = "Alternate Phone Number",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus(force = true)
                    }),
                    placeholder = "Enter Alternate Phone Number"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CommonOutlineTextfield(
                        value = address.pinCode,
                        onValueChange = { address = address.copy(pinCode = it) },
                        label = "Pin Code",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus(force = true)
                        }),
                        placeholder = "Enter Pin Code"
                    )
                    Button(
                        onClick = {
                            if (permissionState.allPermissionsGranted) {
                                fetchLocation { fetchedAddress ->
                                    address = fetchedAddress
                                }
                            } else {
                                permissionResultText = "Permission Denied :("
                                openSettings()
                            }
                        }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = "Use My Location"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Use My Location", fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    CommonOutlineTextfield(
                        value = address.state,
                        onValueChange = { address = address.copy(state = it) },
                        label = "State",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus(force = true)
                        }),
                        placeholder = "Enter State"
                    )
                    CommonOutlineTextfield(
                        value = address.city,
                        onValueChange = { address = address.copy(city = it) },
                        label = "City",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus(force = true)
                        }),
                        placeholder = "Enter City"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                CommonOutlineTextfield(
                    value = address.address,
                    onValueChange = { address = address.copy(address = it) },
                    label = "Address",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus(force = true)
                    }),
                    placeholder = "Enter Address"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonOutlineTextfield(
                    value = address.areaName,
                    onValueChange = { address = address.copy(areaName = it) },
                    label = "Area Name",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus(force = true)
                    }),
                    placeholder = "Enter Area Name"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CommonOutlineTextfield(
                    value = address.landmark,
                    onValueChange = { address = address.copy(landmark = it) },
                    label = "Landmark",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus(force = true)
                    }),
                    placeholder = "Enter Landmark"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Type of Address")
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputChip(selected = address.typeOfAddress == 0,
                        onClick = { address = address.copy(typeOfAddress = 0) },
                        label = { Text("Home") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Home, contentDescription = null)
                        })
                    InputChip(selected = address.typeOfAddress == 1,
                        onClick = { address = address.copy(typeOfAddress = 1) },
                        label = { Text("Office") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Work, contentDescription = null)
                        })
                    InputChip(selected = address.typeOfAddress == 2,
                        onClick = { address = address.copy(typeOfAddress = 2) },
                        label = { Text("Other") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.OtherHouses, contentDescription = null)
                        })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Handle save logic using viewModel
                        viewModel.addAddress(address)
                        navController.popBackStack()
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = "Save Address")
                }
            }
        }
    }
}