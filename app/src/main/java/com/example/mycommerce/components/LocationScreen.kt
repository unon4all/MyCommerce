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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mycommerce.DestinationGraph
import com.example.mycommerce.viewModels.MyCommerceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier, navController: NavHostController, viewModel: MyCommerceViewModel
) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = "My Addresses") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                    )
                }
            },
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate(DestinationGraph.NewLocation.route) }) {
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
            Text("Location Screen")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewLocationLayout(
    modifier: Modifier = Modifier, navController: NavHostController, viewModel: MyCommerceViewModel
) {
    var address by remember {
        mutableStateOf(Address())
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = "My Addresses") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
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
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address.phoneNumber,
                    onValueChange = { address = address.copy(phoneNumber = it) },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address.alternateNumber,
                    onValueChange = { address = address.copy(alternateNumber = it) },
                    label = { Text("Alternate Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = address.pinCode,
                        onValueChange = { address = address.copy(pinCode = it) },
                        label = { Text("Pin Code") },
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(4.dp)
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
                    OutlinedTextField(
                        value = address.state,
                        onValueChange = { address = address.copy(state = it) },
                        label = { Text("State") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = address.city,
                        onValueChange = { address = address.copy(city = it) },
                        label = { Text("City") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address.address,
                    onValueChange = { address = address.copy(address = it) },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address.areaName,
                    onValueChange = { address = address.copy(areaName = it) },
                    label = { Text("Area Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address.landmark,
                    onValueChange = { address = address.copy(landmark = it) },
                    label = { Text("Landmark") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Type of Address")
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputChip(selected = address.typeOfAddress == 0, onClick = {
                        address = address.copy(typeOfAddress = 0)
                    }, label = { Text("Home") }, leadingIcon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    })
                    InputChip(selected = address.typeOfAddress == 1, onClick = {
                        address = address.copy(typeOfAddress = 1)
                    }, label = { Text("Office") }, leadingIcon = {
                        Icon(imageVector = Icons.Default.Work, contentDescription = null)
                    })
                    InputChip(selected = address.typeOfAddress == 2, onClick = {
                        address = address.copy(typeOfAddress = 2)
                    }, label = { Text("Other") }, leadingIcon = {
                        Icon(imageVector = Icons.Default.OtherHouses, contentDescription = null)
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle save logic */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = "Save Address")
                }
            }
        }
    }
}

// Define the Address data class
data class Address(
    var fullName: String = "",
    var phoneNumber: String = "",
    var alternateNumber: String = "",
    var pinCode: String = "",
    var state: String = "",
    var city: String = "",
    var address: String = "",
    var areaName: String = "",
    var landmark: String = "",
    var typeOfAddress: Int = 0 // 0 for Home, 1 for Office, 2 for Other
)

@Preview
@Composable
fun PreviewAddressScreen() {

    val addressList = listOf(
        Address(
            fullName = "John Doe",
            phoneNumber = "1234567890",
            alternateNumber = "9876543210",
            pinCode = "123456",
            state = "State 1",
            city = "City 1",
            address = "Address 1",
            areaName = "Area 1",
            landmark = "Landmark 1",
            typeOfAddress = 0
        ),
    )

    AddressListContent(address = addressList)

}

@Composable
fun AddressListContent(
    address: List<Address>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "${address.size} Saved Addresses", fontSize = 16.sp, color = Color.Gray)
        }
        address.forEach {
            AddressDetailsCard(address = it)
        }
    }
}

@Composable
fun AddressDetailsCard(address: Address) {
    var isDropDownExpanded by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth(), shape = RectangleShape) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { isDropDownExpanded = !isDropDownExpanded }) {
                    Icon(imageVector = Icons.Default.ExpandCircleDown, contentDescription = null)
                    DropdownMenu(expanded = isDropDownExpanded,
                        onDismissRequest = { isDropDownExpanded = false }) {
                        DropdownMenuItem(onClick = { /*TODO*/ }, text = { Text(text = "Edit") })
                        DropdownMenuItem(onClick = { /*TODO*/ }, text = { Text(text = "Save") })
                        DropdownMenuItem(onClick = { /*TODO*/ },
                            text = { Text(text = "Mark as Default") })
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = address.fullName, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .padding(4.dp)
                ) {
                    Text(text = getTypeOfAddressText(address.typeOfAddress))
                }
            }
            Text(
                text = "${address.address}, ${address.areaName}, ${address.city}, ${address.state}, ${address.pinCode}, ${address.landmark}",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp, end = 48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = address.phoneNumber, fontSize = 14.sp)
            Text(text = address.alternateNumber, fontSize = 14.sp)
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