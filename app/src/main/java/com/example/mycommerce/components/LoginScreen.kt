package com.example.mycommerce.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycommerce.DestinationGraph
import com.example.mycommerce.components.common.navigateTo
import com.example.mycommerce.viewModels.MyCommerceViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier, navController: NavController, viewModel: MyCommerceViewModel
) {

    SignInContent(
        modifier = modifier, navController = navController, viewModel = viewModel
    )
}


@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MyCommerceViewModel,
) {

    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppEntryLogo()

            Text(
                text = "Login",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )

            val username by viewModel.username.collectAsState()
            val usernameError by viewModel.usernameError.collectAsState()

            EmailTextField(
                value = username,
                onValueChange = viewModel::onUsernameChange,
                isError = usernameError != null,
                errorMessage = usernameError
            )

            val password by viewModel.password.collectAsState()
            val passwordError by viewModel.passwordError.collectAsState()

            PassTextField(
                value = password,
                onValueChange = viewModel::onPasswordChange,
                isError = passwordError != null,
                errorMessage = passwordError
            )

            Button(onClick = {
                focusManager.clearFocus(force = true)
            }, modifier = Modifier.padding(16.dp)) {
                Text(text = "LOGIN")
            }

            ClickableText(text = AnnotatedString(
                text = "New here? Go to Signup ->",
                spanStyle = SpanStyle(color = Color.DarkGray)
            ), modifier = Modifier.padding(8.dp), onClick = {
                navigateTo(navController, DestinationGraph.Signup)
            })
        }
    }
}