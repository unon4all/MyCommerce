package com.example.mycommerce.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycommerce.components.common.CommonDivider
import com.example.mycommerce.components.common.CommonImage
import com.example.mycommerce.components.common.AppImageSlider
import com.example.mycommerce.data.frDatabase.ECommerceItem
import com.example.mycommerce.data.frDatabase.eCommerceItemsList
import com.example.mycommerce.viewModels.MyCommerceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    viewModel: MyCommerceViewModel,
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "MyCommerce")
            }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
                }
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "")
                }
            }, modifier = Modifier.padding(end = 8.dp))
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp)
        ) {
            Box(modifier = Modifier.aspectRatio(16f / 9f)) {
                AppImageSlider(modifier = Modifier.fillMaxSize())
            }
            CommonDivider()
            ECommerceItemList(itemList = eCommerceItemsList, viewModel = viewModel)
        }
    }
}

@Composable
fun ECommerceItemList(
    modifier: Modifier = Modifier,
    itemList: List<ECommerceItem>,
    viewModel: MyCommerceViewModel,
) {
    Box(modifier = modifier) {
        LazyColumn {
            items(items = itemList) { itemList ->
                ECommerceItemUI(itemList = itemList, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ECommerceItemUI(
    itemList: ECommerceItem, viewModel: MyCommerceViewModel
) {

    val cartItems by viewModel.cartItems.collectAsState()
    val isItemInCart = cartItems.any { it.id == itemList.id }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                ItemImage(
                    url = itemList.imageUrl, modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ItemContentDetails(
                    modifier = Modifier.weight(2f),
                    itemName = itemList.itemName,
                    itemDescription = itemList.itemDescription,
                    itemPrice = itemList.itemPrice.toString(),
                    itemRating = itemList.itemRating.toString(),
                    onClick = {
                        if (isItemInCart) {
                            viewModel.removeFromCart(itemList.id)
                        } else {
                            viewModel.addToCart(itemList)
                        }
                    },
                    icon = if (isItemInCart) Icons.Default.Delete else Icons.Default.AddShoppingCart,
                    iconTint = if (isItemInCart) Color.Red else Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun ItemContentDetails(
    modifier: Modifier = Modifier,
    itemName: String,
    itemDescription: String,
    itemPrice: String,
    itemRating: String,
    onClick: () -> Unit,
    icon: ImageVector,
    iconTint: Color
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(text = itemName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = itemDescription, fontSize = 16.sp, color = Color.Gray)
            Text(
                text = "Rating: $itemRating", fontSize = 15.sp, color = Color.DarkGray
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "₹ $itemPrice", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = icon, contentDescription = "", tint = iconTint
                )
            }
        }
    }
}

@Composable
fun ItemImage(modifier: Modifier = Modifier, url: String? = null) {
    CommonImage(
        url = url,
        contentScale = ContentScale.FillBounds,
        modifier = modifier.clip(RoundedCornerShape(8.dp))
    )
}