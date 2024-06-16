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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mycommerce.DestinationGraph
import com.example.mycommerce.R
import com.example.mycommerce.components.common.CommonDivider
import com.example.mycommerce.viewModels.MyCommerceViewModel

@Composable
fun AddToCartScreen(
    viewModel: MyCommerceViewModel, navController: NavController
) {

    val cartItems by viewModel.cartItems.collectAsState()
    var isCheckoutClicked by remember { mutableStateOf(false) }
    val addressList by viewModel.userAddresses.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(text = "Your Cart", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        CommonDivider()
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Total Items in Cart: ${cartItems.size}", fontSize = 16.sp)
            Text(text = "Total Price: ₹${viewModel.calculateTotalPrice()}", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ECommerceItemList(itemList = cartItems, viewModel = viewModel)
    }

    if (cartItems.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Button(onClick = {
                if (addressList.isEmpty()) {
                    navController.navigate(DestinationGraph.Location.route)
                } else {
                    viewModel.placeOrder()
                    isCheckoutClicked = true
                }
            }) {
                Text(text = "Checkout")
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Your Cart is Empty")
        }
    }

    if (isCheckoutClicked) {
        OrderPlacedScreen(navController = navController, viewModel = viewModel)
    }
}

@Composable
fun OrderPlacedScreen(navController: NavController, viewModel: MyCommerceViewModel) {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.order_placed)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition, iterations = 1
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (progress == 1f) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Animation Ended!",
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                Button(onClick = {
                    navController.navigate(DestinationGraph.Home.createRoute(0))
                }) {
                    Text(
                        text = "Continue Shopping",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        } else {
            LottieAnimation(
                composition = composition, modifier = Modifier.size(300.dp), progress = progress
            )
        }
    }
}