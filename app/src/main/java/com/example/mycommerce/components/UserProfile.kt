package com.example.mycommerce.components

import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mycommerce.components.common.CommonImage
import com.example.mycommerce.components.common.CommonTextfield
import com.example.mycommerce.viewModels.MyCommerceViewModel

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
                ProfileScreen()
                Spacer(modifier = Modifier.height(16.dp))
                UserProfileDetails()
            }
        }
    }
}

@Composable
fun ProfileScreen() {

    var imageUrl by remember {
        mutableStateOf("")
    }

    val launcher =
        rememberLauncherForActivityResult(contract = androidx.activity.result.contract.ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    imageUrl = it.toString()
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
                    CommonImage(
                        url = imageUrl,
                        modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
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
fun UserProfileDetails() {

    val focusManager = LocalFocusManager.current

    var firstName by remember {
        mutableStateOf(TextFieldValue())
    }
    var lastName by remember {
        mutableStateOf(TextFieldValue())
    }
    var email by remember {
        mutableStateOf(TextFieldValue())
    }
    var mobile by remember {
        mutableStateOf(TextFieldValue())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .imePadding(),
    ) {
        CommonTextfield(value = firstName,
            onValueChange = { firstName = it },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) }),
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
            ),
            label = "First Name",
            placeholder = firstName.text
        )

        CommonTextfield(value = lastName,
            onValueChange = { lastName = it },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) }),
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
            ),
            label = "Last Name",
            placeholder = lastName.text
        )

        CommonTextfield(value = email,
            onValueChange = { email = it },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) }),
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            label = "Email",
            placeholder = email.text
        )

        CommonTextfield(value = mobile,
            onValueChange = { mobile = it },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(force = true) }),
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            label = "Mobile",
            placeholder = mobile.text
        )

        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Delete Account",
                color = Color.Red,
                style = typography.bodyLarge,
            )
        }
    }
}