package com.example.mycommerce.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.mycommerce.data.ECommerceItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.example.mycommerce.data.Event
import com.example.mycommerce.data.OrderHistoryItem
import com.example.mycommerce.data.OrderStatus
import kotlin.math.roundToInt

const val USERS = "users"
const val POSTS = "posts"
const val COMMENTS = "comments"

@HiltViewModel
class MyCommerceViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
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

    private val _cartItems = MutableStateFlow<List<ECommerceItem>>(emptyList())
    val cartItems: StateFlow<List<ECommerceItem>> get() = _cartItems

    private val _orderStatus = MutableStateFlow<OrderStatus?>(null)
    val orderStatus: StateFlow<OrderStatus?> = _orderStatus

    private val _orderHistory = MutableStateFlow<List<OrderHistoryItem>>(emptyList())
    val orderHistory: StateFlow<List<OrderHistoryItem>> = _orderHistory

    fun placeOrder() {
        // Assuming order placement logic here, then update order status
        _orderStatus.value = OrderStatus.PLACED

        val orderHistoryList = _orderHistory.value.toMutableList()

        // Add current cart items to order history
        orderHistoryList.add(OrderHistoryItem(_cartItems.value, OrderStatus.PLACED))
        _orderHistory.value = orderHistoryList

        // Clear the cart after placing the order
        clearCart()
    }

    private fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun addToCart(item: ECommerceItem) {
        val updatedList = _cartItems.value.toMutableList()
        updatedList.add(item)
        _cartItems.value = updatedList
    }

    fun removeFromCart(itemId: String) {
        val updatedList = _cartItems.value.toMutableList()
        updatedList.removeAll { it.id == itemId }
        _cartItems.value = updatedList
    }

    fun getItemFromCart(itemId: String): ECommerceItem? {
        return _cartItems.value.find { it.id == itemId }
    }

    fun calculateTotalPrice(): Int {
        return _cartItems.value.sumOf { it.itemPrice }.roundToInt()
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val error = exception?.localizedMessage ?: "Something went wrong"
        val message = if (customMessage.isNotEmpty()) "$customMessage: $error" else error
        _popupNotification.value = Event(message)
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

    fun signUp(
        username: String, email: String, password: String
    ) {
        if (validateForm()) {
            db.collection(USERS).whereEqualTo("username", username).get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.documents.isNotEmpty()) {
                        _popupNotification.value = Event("Username already exists")
                        return@addOnSuccessListener
                    } else {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    //User created successfully
                                    _popupNotification.value = Event("User created successfully")
                                } else {
                                    handleException(task.exception, "Error creating user")
                                }
                            }
                    }
                }.addOnFailureListener {
                    handleException(it)
                }
        }
    }

    fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //User signed in successfully
                _popupNotification.value = Event("Signed in successfully")
            } else {
                handleException(task.exception, "Error signing in")
            }
        }.addOnFailureListener {
            handleException(it)
        }
    }

    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
        _popupNotification.value = Event("Signed out successfully")
    }

    fun resetForm() {
        _username.value = TextFieldValue("")
        _email.value = TextFieldValue("")
        _password.value = TextFieldValue("")
        _usernameError.value = null
        _emailError.value = null
        _passwordError.value = null
    }
}