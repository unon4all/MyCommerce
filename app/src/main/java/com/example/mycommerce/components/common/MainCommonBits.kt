package com.example.mycommerce.components.common

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mycommerce.DestinationGraph
import com.example.mycommerce.R
import com.example.mycommerce.viewModels.MyCommerceViewModel


@Composable
fun NotificationMessage(viewModel: MyCommerceViewModel) {

    val notifyState by viewModel.popupNotification.collectAsState()
    val context = LocalContext.current

    notifyState?.getContentOrNull()?.let { notifyMessage ->
        Toast.makeText(context, notifyMessage, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CommonDivider() {
    HorizontalDivider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun CommonNetworkImage(
    modifier: Modifier = Modifier,
    url: String?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    val defaultPainter = painterResource(id = R.drawable.baseline_person_24)

    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = defaultPainter,
        error = defaultPainter,
        fallback = defaultPainter,
        alignment = Alignment.Center
    )
}


fun navigateTo(navController: NavController, destination: DestinationGraph) {
    navController.navigate(destination.route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
    }
}