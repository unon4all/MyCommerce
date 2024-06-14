package com.example.mycommerce.viewModels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycommerce.data.extra.Event
import com.example.mycommerce.data.models.ECommerceItem
import com.example.mycommerce.data.models.OrderHistoryItem
import com.example.mycommerce.data.models.OrderStatus
import com.example.mycommerce.data.repository.UserRepository
import com.example.mycommerce.data.models.User
import com.example.mycommerce.data.repository.OrderHistoryRepository
import com.example.mycommerce.util.NetworkUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.math.roundToInt

const val USERS = "users"

@HiltViewModel
class MyCommerceViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val orderHistoryRepository: OrderHistoryRepository,
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

    private val _allUsersWithOrders =
        MutableStateFlow<List<Pair<User, List<OrderHistoryItem>>>>(emptyList())
    val allUsersWithOrders: StateFlow<List<Pair<User, List<OrderHistoryItem>>>> =
        _allUsersWithOrders

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    private val _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn: StateFlow<Boolean> = _isUserSignedIn

    fun fetchAllUsersWithOrders() {

    }

    private fun fetchUserOrderHistory(userId: String, callback: (List<OrderHistoryItem>) -> Unit) {

    }

    // Function to update user ID and signed-in state
    private fun updateUserIdAndSignInState(userId: String?, isSignedIn: Boolean) {
        _userId.value = userId
        _isUserSignedIn.value = isSignedIn
    }

    fun placeOrder() {
        if (_isUserSignedIn.value) {
            val userId = _userId.value ?: return  // Ensure userId is not null
            val items = _cartItems.value
            val totalPrice = calculateTotalPrice()
            val orderStatus = OrderStatus.PLACED

            val orderHistoryItem = OrderHistoryItem(
                userId = userId, items = items, status = orderStatus, totalPrice = totalPrice
            )

            viewModelScope.launch {
                try {
                    // Save order to local database
                    orderHistoryRepository.addOrderHistoryItem(orderHistoryItem)

                    // Update local order status and history
                    _orderStatus.value = orderStatus
                    val updatedOrderHistory = _orderHistory.value.toMutableList()
                    updatedOrderHistory.add(orderHistoryItem)
                    _orderHistory.value = updatedOrderHistory

                    // Clear the cart after placing the order
                    clearCart()
                } catch (e: Exception) {
                    handleException(e, "Failed to place order")
                }
            }
        } else {
            // Handle case where user is not signed in
            _popupNotification.value = Event("Please sign in to place an order")
        }
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

    private fun getItemFromCart(itemId: String): ECommerceItem? {
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

    fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {
            if (validateForm()) {
                val existingUser = userRepository.getUserByEmail(email).firstOrNull()
                if (existingUser == null) {
                    val newUser = User(
                        id = UUID.randomUUID().toString(),
                        username = username,
                        email = email,
                        passwordHash = ""
                    )
                    val success = userRepository.insertUser(newUser, password)
                    if (success) {
                        _popupNotification.value = Event("Sign-up successful")
                        updateSignedInState(true)
                    } else {
                        _popupNotification.value = Event("Sign-up failed")
                    }
                } else {
                    _popupNotification.value = Event("Email already exists")
                }
            }
        }
    }


    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            userRepository.getUserByEmail(email).collect { user ->
                if (user != null && userRepository.validatePassword(password, user.passwordHash)) {
                    _popupNotification.value = Event("Login successful")
                    updateUserIdAndSignInState(user.id, true)
                    updateSignedInState(true)
                    fetchOrderHistory()
                    // Handle successful login
                } else {
                    _popupNotification.value = Event("Invalid email or password")
                }
            }
        }
    }

    private fun updateSignedInState(isSignedIn: Boolean) {
        _isUserSignedIn.value = isSignedIn
    }

    // Function to sign out user
    fun signOut() {
        updateUserIdAndSignInState(null, false)
        clearCart()
        _orderHistory.value = emptyList()
        _orderStatus.value = null
        resetForm()
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


    private fun fetchOrderHistory() {
        viewModelScope.launch {
            try {
                val userId = _userId.value ?: return@launch
                val history = orderHistoryRepository.getOrderHistory(userId).first()
                _orderHistory.value = history
            } catch (e: Exception) {
                handleException(e, "Failed to fetch order history")
            }
        }
    }


    private fun getItemDetails(itemId: String): ECommerceItem? {
        return _cartItems.value.find { it.id == itemId }
    }
}