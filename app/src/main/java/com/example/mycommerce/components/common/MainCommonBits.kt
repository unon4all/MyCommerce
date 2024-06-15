package com.example.mycommerce.components.common

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
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
fun CommonDivider(
    thickness: Dp = 1.dp, color: Color = Color.LightGray, alpha: Float = 0.3f
) {
    HorizontalDivider(
        color = color,
        thickness = thickness,
        modifier = Modifier
            .alpha(alpha)
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

@Composable
fun CheckedSignIn(vm: MyCommerceViewModel, navController: NavController) {
    var alreadySignedIn by remember { mutableStateOf(false) }

    LaunchedEffect(vm.isUserSignedIn.collectAsState().value) {
        if (vm.isUserSignedIn.value && !alreadySignedIn) {
            alreadySignedIn = true
            navController.navigate(DestinationGraph.Home.createRoute(0)) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
            }
        }
    }
}

@Composable
fun CommonImage(
    modifier: Modifier = Modifier,
    url: String?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    val defaultPainter = painterResource(id = R.drawable.ic_person)

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

@Composable
fun CommonTextfield(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    label: String,
    placeholder: String,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
    )
}

@Composable
fun CommonOutlineTextfield(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    label: String,
    placeholder: String,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value, onValueChange = onValueChange, modifier = modifier,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
    )
}