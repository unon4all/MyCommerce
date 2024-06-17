package com.example.mycommerce.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.mycommerce.components.common.CommonImage
import com.example.mycommerce.components.common.CommonTextfield
import com.example.mycommerce.viewModels.MyCommerceViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile(
    navController: NavHostController, modifier: Modifier, viewModel: MyCommerceViewModel
) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = "User Profile") }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
            }
        }, actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Logout")
                IconButton(onClick = {
                    viewModel.signOut()
                    navController.navigate("signup")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null
                    )
                }
            }
        })
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),
        ) {
            Column(Modifier.fillMaxSize()) {
                ProfileScreen(viewModel = viewModel)
                Spacer(modifier = Modifier.height(16.dp))
                UserProfileDetails(viewModel = viewModel, navController = navController)
            }
        }
    }
}

@Composable
fun ProfileScreen(viewModel: MyCommerceViewModel) {

    val imageUrl by viewModel.imageUrl.collectAsState()

    Log.d("IMGURL", "ProfileScreen: $imageUrl")

    val launcher =
        rememberLauncherForActivityResult(contract = androidx.activity.result.contract.ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    viewModel.onImageUrlChange(newValue = it.toString())
                }
            })

    Card(modifier = Modifier.fillMaxWidth(), shape = RectangleShape) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.size(180.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
                        contentScale = ContentScale.FillBounds
                    )

                    IconButton(
                        onClick = { launcher.launch("image/*") },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Change Profile Picture", style = typography.bodyLarge)
            }
        }
    }
}


@Composable
fun UserProfileDetails(viewModel: MyCommerceViewModel, navController: NavController) {

    val focusManager = LocalFocusManager.current

    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val email by viewModel.email.collectAsState()
    val mobile by viewModel.mobileNumber.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .imePadding(),
    ) {
        CommonTextfield(
            value = firstName,
            onValueChange = { viewModel.onFirstNameChange(it) },
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(force = true)
                viewModel.onFirstNameDoneClick(newValue = firstName)
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
            ),
            label = "First Name",
            placeholder = firstName.text,
        )

        CommonTextfield(
            value = lastName,
            onValueChange = { viewModel.onLastNameChange(it) },
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(force = true)
                viewModel.onLastNameDoneClick(newValue = lastName)
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
            ),
            label = "Last Name",
            placeholder = lastName.text,
        )

        CommonTextfield(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(force = true)
                viewModel.onEmailDoneClick(newValue = email)
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            label = "Email",
            placeholder = email.text,
        )

        CommonTextfield(
            value = mobile,
            onValueChange = { viewModel.onMobileNumberChange(it) },
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(force = true)
                viewModel.onPhoneNumberDoneClick(newValue = mobile)
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            label = "Mobile",
            placeholder = mobile.text,
        )

        TextButton(
            onClick = {
                viewModel.deleteUser(viewModel.userId.value)
                navController.navigate("signup")
            }, modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Delete Account",
                color = Color.Red,
                style = typography.bodyLarge,
            )
        }
    }
}