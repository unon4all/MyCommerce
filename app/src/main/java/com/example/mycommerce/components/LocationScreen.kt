package com.example.mycommerce.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandCircleDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mycommerce.DestinationGraph
import com.example.mycommerce.MainActivity
import com.example.mycommerce.data.models.UserAddressDetails
import com.example.mycommerce.viewModels.MyCommerceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier, navController: NavHostController, viewModel: MyCommerceViewModel
) {
    val userAddresses by viewModel.userAddresses.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = "My Addresses") },
            navigationIcon = {
                IconButton(onClick = {
                    viewModel.getMarkDefaultAddress(viewModel.userId.value)
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                    )
                }
            },
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(DestinationGraph.NewLocation.route)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            AddressListContent(
                address = userAddresses, viewModel = viewModel, navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewLocationLayout(
    modifier: Modifier = Modifier, navController: NavHostController, viewModel: MyCommerceViewModel
) {
    val context = LocalContext.current as MainActivity

    val userId by viewModel.userId.collectAsState()

    var address by remember {
        mutableStateOf(UserAddressDetails(userId = userId))
    }

    // Fetch selected address details if editing
    val selectedAddress by viewModel.selectedAddress.collectAsState()

    // Check if we are editing an existing address
    address = if (selectedAddress != null) {
        // Populate fields with selected address data
        selectedAddress!!
    } else {
        // Clear fields when adding a new address
        UserAddressDetails(userId = userId)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = if (selectedAddress != null) "Edit Address" else "Add New Address") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                    viewModel.clearSelectedAddress()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                    )
                }
            },
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = address.fullName,
                    onValueChange = { address = address.copy(fullName = it) },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address.phoneNumber,
                    onValueChange = { address = address.copy(phoneNumber = it) },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address.alternateNumber,
                    onValueChange = { address = address.copy(alternateNumber = it) },
                    label = { Text("Alternate Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(value = address.address.pinCode,
                        onValueChange = {
                            address = address.copy(address = address.address.copy(pinCode = it))
                        },
                        label = { Text("Pin Code") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
                        )
                    )
                    Button(
                        onClick = {
                            context.checkLocationPermission {
                                context.fetchLocation { location ->
                                    address = location
                                }
                            }
                        }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.MyLocation, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Use My Location", fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    OutlinedTextField(value = address.address.state,
                        onValueChange = {
                            address = address.copy(address = address.address.copy(state = it))
                        },
                        label = { Text("State") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        )
                    )
                    OutlinedTextField(value = address.address.city,
                        onValueChange = {
                            address = address.copy(address = address.address.copy(city = it))
                        },
                        label = { Text("City") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = address.address.addressLine,
                    onValueChange = {
                        address = address.copy(address = address.address.copy(addressLine = it))
                    },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = address.address.areaName,
                    onValueChange = {
                        address = address.copy(address = address.address.copy(areaName = it))
                    },
                    label = { Text("Area Name") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = address.address.landmark,
                    onValueChange = {
                        address = address.copy(address = address.address.copy(landmark = it))
                    },
                    label = { Text("Landmark") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Type of Address")
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputChip(selected = address.address.typeOfAddress == 0, onClick = {
                        address = address.copy(address = address.address.copy(typeOfAddress = 0))
                    }, label = { Text("Home") }, leadingIcon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    })
                    InputChip(selected = address.address.typeOfAddress == 1, onClick = {
                        address = address.copy(address = address.address.copy(typeOfAddress = 1))
                    }, label = { Text("Office") }, leadingIcon = {
                        Icon(imageVector = Icons.Default.Work, contentDescription = null)
                    })
                    InputChip(selected = address.address.typeOfAddress == 2, onClick = {
                        address = address.copy(address = address.address.copy(typeOfAddress = 2))
                    }, label = { Text("Other") }, leadingIcon = {
                        Icon(imageVector = Icons.Default.OtherHouses, contentDescription = null)
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (selectedAddress != null) {
                            viewModel.updateAddress(address)
                            navController.popBackStack()
                            viewModel.clearSelectedAddress()
                        } else {
                            viewModel.saveAddress(address)
                            navController.popBackStack()
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = if (selectedAddress != null) "Update Address" else "Save Address")
                }
            }
        }
    }
}


@Composable
fun AddressListContent(
    address: List<UserAddressDetails>, viewModel: MyCommerceViewModel, navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "${address.size} Saved Addresses", fontSize = 16.sp, color = Color.Gray)
        }
        address.forEach { addressDetails ->
            AddressDetailsCard(
                address = addressDetails, viewModel = viewModel, navController = navController
            )
        }
    }
}


@Composable
fun AddressDetailsCard(
    address: UserAddressDetails, viewModel: MyCommerceViewModel, navController: NavController
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }

    val cardBackgroundColor = if (address.isDefault) Color.Green else Color.LightGray

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { isDropDownExpanded = !isDropDownExpanded }) {
                    Icon(imageVector = Icons.Default.ExpandCircleDown, contentDescription = null)
                    DropdownMenu(expanded = isDropDownExpanded,
                        onDismissRequest = { isDropDownExpanded = false }) {
                        DropdownMenuItem(onClick = {
                            viewModel.getUserAddressDetails(address.id)
                            navController.navigate(DestinationGraph.NewLocation.route)
                            isDropDownExpanded = false
                        }, text = { Text(text = "Edit") })
                        DropdownMenuItem(onClick = {
                            viewModel.deleteAddress(address)
                            isDropDownExpanded = false
                        }, text = { Text(text = "Delete") })
                        DropdownMenuItem(onClick = {
                            viewModel.markAsDefault(addressId = address.id, userId = address.userId)
                            isDropDownExpanded = false
                        }, text = { Text(text = "Mark as Default") })
                    }
                }
            }
            if (address.isDefault) {
                Text(
                    text = "Default address",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(8.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = address.fullName, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .padding(4.dp)
                ) {
                    Text(text = getTypeOfAddressText(address.address.typeOfAddress))
                }
            }
            Text(
                text = "${address.address.addressLine}, ${address.address.areaName}, ${address.address.city}, ${address.address.state}, ${address.address.pinCode}, ${address.address.landmark}",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp, end = 48.dp, start = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = address.phoneNumber,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = address.alternateNumber,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
            )
        }
    }
}


// Function to map type of address to string
fun getTypeOfAddressText(typeOfAddress: Int): String {
    return when (typeOfAddress) {
        0 -> "Home"
        1 -> "Office"
        2 -> "Other"
        else -> ""
    }
}