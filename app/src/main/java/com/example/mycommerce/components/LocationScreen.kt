package com.example.mycommerce.components

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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.OtherHouses
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { navController.navigate(DestinationGraph.NewLocation.route) }) {
                Text(
                    text = "Add New Location", modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewLocationLayout(
    modifier: Modifier = Modifier, navController: NavHostController, viewModel: MyCommerceViewModel
) {
    val address by remember { mutableStateOf(Address()) }

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
                OutlinedTextField(value = address.fullName,
                    onValueChange = { address.fullName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = address.phoneNumber,
                    onValueChange = { address.phoneNumber = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = address.alternateNumber,
                    onValueChange = { address.alternateNumber = it },
                    label = { Text("Alternate Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(value = address.pinCode,
                        onValueChange = { address.pinCode = it },
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
                    OutlinedTextField(value = address.state,
                        onValueChange = { address.state = it },
                        label = { Text("State") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(value = address.city,
                        onValueChange = { address.city = it },
                        label = { Text("City") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = address.address,
                    onValueChange = { address.address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = address.areaName,
                    onValueChange = { address.areaName = it },
                    label = { Text("Area Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = address.landmark,
                    onValueChange = { address.landmark = it },
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
                    InputChip(selected = address.typeOfAddress == 0,
                        onClick = { address.typeOfAddress = 0 },
                        label = { Text("Home") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Home, contentDescription = null
                            )
                        })
                    InputChip(selected = address.typeOfAddress == 1,
                        onClick = { address.typeOfAddress = 1 },
                        label = { Text("Office") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Work, contentDescription = null
                            )
                        })
                    InputChip(selected = address.typeOfAddress == 2,
                        onClick = { address.typeOfAddress = 2 },
                        label = { Text("Other") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.OtherHouses, contentDescription = null
                            )
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