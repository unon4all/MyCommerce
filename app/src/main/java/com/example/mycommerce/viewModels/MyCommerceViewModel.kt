package com.example.mycommerce.viewModels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.example.mycommerce.data.Event

const val USERS = "users"
const val POSTS = "posts"
const val COMMENTS = "comments"

@HiltViewModel
class MyCommerceViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {

    private val _popupNotification = MutableStateFlow<Event<String>?>(null)
    val popupNotification: StateFlow<Event<String>?> get() = _popupNotification.asStateFlow()

    private val _username = MutableStateFlow(TextFieldValue(""))
    val username: StateFlow<TextFieldValue> = _username

    private val _email = MutableStateFlow(TextFieldValue(""))
    val email: StateFlow<TextFieldValue> = _email

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password: StateFlow<TextFieldValue> = _password

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val error = exception?.localizedMessage ?: "Something went wrong"
        val message = if (customMessage.isNotEmpty()) "$customMessage: $error" else error
        _popupNotification.value = com.example.mycommerce.data.Event(message)
    }

    //Text field Validation
    fun onUsernameChange(newValue: TextFieldValue) {
        _username.value = newValue
        validateUsername(newValue.text)
    }

    fun onEmailChange(newValue: TextFieldValue) {
        _email.value = newValue
        validateEmail(newValue.text)
    }

    fun onPasswordChange(newValue: TextFieldValue) {
        _password.value = newValue
        validatePassword(newValue.text)
    }

    private fun validateUsername(username: String) {
        if (username.isEmpty()) {
            _usernameError.value = "Username is required"
        } else {
            _usernameError.value = null
        }
    }

    private fun validateEmail(email: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        if (email.isEmpty()) {
            _emailError.value = "Email is required"
        } else if (!email.matches(emailRegex)) {
            _emailError.value = "Invalid email format"
        } else {
            _emailError.value = null
        }
    }

    private fun validatePassword(password: String) {
        if (password.isEmpty()) {
            _passwordError.value = "Password is required"
        } else {
            _passwordError.value = null
        }
    }

    fun validateForm(): Boolean {
        validateUsername(_username.value.text)
        validateEmail(_email.value.text)
        validatePassword(_password.value.text)
        return _usernameError.value == null && _emailError.value == null && _passwordError.value == null
    }
}