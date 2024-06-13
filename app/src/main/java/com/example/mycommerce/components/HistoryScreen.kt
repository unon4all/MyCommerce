package com.example.mycommerce.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycommerce.components.common.CommonDivider
import com.example.mycommerce.data.OrderHistoryItem
import com.example.mycommerce.viewModels.MyCommerceViewModel

@Composable
fun HistoryScreen(
    viewModel: MyCommerceViewModel,
    navController: NavController,
) {

    val orderHistory by viewModel.orderHistory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(text = "Placed History", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        CommonDivider()
        if (orderHistory.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No Order Placed Yet")
            }
        } else {
            OrderHistoryContent(orderHistory = orderHistory, viewModel = viewModel)
        }
    }
}

@Composable
fun OrderHistoryContent(orderHistory: List<OrderHistoryItem>, viewModel: MyCommerceViewModel) {
    LazyColumn {
        itemsIndexed(items = orderHistory) { index, itemList ->
            OrderHistoryItemContent(itemList = itemList, viewModel = viewModel, index = index)
        }
    }
}

@Composable
fun OrderHistoryItemContent(
    itemList: OrderHistoryItem, viewModel: MyCommerceViewModel, index: Int
) {

    val items = itemList.items

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Order No. $index", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Order Status: ${itemList.status}", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            items.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Product Name: \n${item.itemName}",
                    )
                    Text(text = "Product Price: \n${item.itemPrice}")
                }
                CommonDivider()
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Total Price: ${itemList.totalPrice}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}