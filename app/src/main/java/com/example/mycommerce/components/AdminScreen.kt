package com.example.mycommerce.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycommerce.components.common.CommonDivider
import com.example.mycommerce.data.models.OrderHistoryItem
import com.example.mycommerce.data.models.User
import com.example.mycommerce.viewModels.MyCommerceViewModel


@Composable
fun AdminScreen(
    modifier: Modifier = Modifier, navController: NavController, viewModel: MyCommerceViewModel
) {
    val allUsersWithOrders by viewModel.allUsersWithOrders.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllUsersWithOrders()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Admin Block", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                TextButton(onClick = {
                    viewModel.signOut()
                    navController.navigate("signin")
                }) {
                    Text(text = "Click here to Logout", fontSize = 14.sp, color = Color.LightGray)
                }
            }
            CommonDivider()

            Text(
                text = "Total Number of Users: ${allUsersWithOrders.size}",
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )

            CommonDivider()

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(allUsersWithOrders) { (user, orderHistory) ->
                    ExpandableCard(user, orderHistory)
                }
            }
        }
    }
}


@Composable
fun ExpandableCard(
    user: User, orderHistory: List<OrderHistoryItem>
) {
    var isExpandable by remember { mutableStateOf(false) }

    val icon = if (isExpandable) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpandable = !isExpandable }
            .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "User ID:\n${user.id}")
            Icon(imageVector = icon, contentDescription = null)
        }
        if (isExpandable) {
            UserDetails(user = user)
            MultipleOrderHistory(itemList = orderHistory)
        }
    }
}

@Composable
fun UserDetails(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        CommonDivider(alpha = 1f)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Username", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = user.username)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Email", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = user.email)
        }
        CommonDivider()
    }
}

@Composable
fun MultipleOrderHistory(
    itemList: List<OrderHistoryItem>
) {

    itemList.forEachIndexed { index, orderItem ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Order No. ${index + 1}", fontWeight = FontWeight.Bold, fontSize = 20.sp
                )
                Text(
                    text = "Order Status: ${orderItem.status}", fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            orderItem.items.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Product Name:\n${item.itemName}")
                    Text(text = "Product Price:\n${item.itemPrice}")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Total Price: ${orderItem.totalPrice}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            CommonDivider()
        }
    }
}
