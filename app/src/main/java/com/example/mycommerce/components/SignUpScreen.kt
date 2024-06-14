package com.example.mycommerce.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycommerce.DestinationGraph
import com.example.mycommerce.R
import com.example.mycommerce.components.common.CheckedSignIn
import com.example.mycommerce.components.common.navigateTo
import com.example.mycommerce.viewModels.MyCommerceViewModel

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier, navController: NavController, viewModel: MyCommerceViewModel
) {

    CheckedSignIn(navController = navController, vm = viewModel)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SignUpContent(
            modifier = Modifier,
            viewModel = viewModel,
            navController = navController,
        )
    }
}


@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    viewModel: MyCommerceViewModel,
    navController: NavController,
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppEntryLogo()

        Text(
            text = "Sign Up",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )

        val username by viewModel.username.collectAsState()

        UsernameTextField(
            value = username,
            onValueChange = viewModel::onUsernameChange,
        )

        val email by viewModel.email.collectAsState()

        EmailTextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
        )

        val password by viewModel.password.collectAsState()

        PassTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
        )

        Button(onClick = {
            focusManager.clearFocus(force = true)
            viewModel.signUp(
                username = username.text, email = email.text, password = password.text
            )
        }, modifier = Modifier.padding(16.dp)) {
            Text(text = "SIGN UP")
        }

        ClickableText(text = AnnotatedString(
            text = "Already have an account? Sign in",
            spanStyle = SpanStyle(color = Color.DarkGray, fontWeight = FontWeight.Bold)
        ),
            modifier = Modifier.padding(8.dp),
            onClick = { navigateTo(navController, DestinationGraph.Login) })

        ClickableText(text = AnnotatedString(
            text = "Login as Admin", spanStyle = SpanStyle(color = Color.DarkGray)
        ),
            modifier = Modifier.padding(8.dp),
            onClick = { navigateTo(navController, DestinationGraph.Admin) })
    }
}


@Composable
fun UsernameTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Username") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        maxLines = 1,
    )
}

@Composable
fun EmailTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        maxLines = 1,
    )
}

@Composable
fun PassTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        maxLines = 1,
    )
}


@Composable
fun AppEntryLogo() {
    Image(
        painter = painterResource(id = R.drawable.main_screen_logo),
        contentDescription = null,
        modifier = Modifier.aspectRatio(16f / 9f),
        contentScale = ContentScale.Fit,
    )
}