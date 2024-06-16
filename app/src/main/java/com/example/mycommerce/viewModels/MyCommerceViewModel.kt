package com.example.mycommerce.viewModels

import android.content.SharedPreferences
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycommerce.data.extra.Event
import com.example.mycommerce.data.models.ECommerceItem
import com.example.mycommerce.data.models.OrderHistoryItem
import com.example.mycommerce.data.models.OrderStatus
import com.example.mycommerce.data.models.User
import com.example.mycommerce.data.models.UserAddressDetails
import com.example.mycommerce.data.repository.AddressRepository
import com.example.mycommerce.data.repository.OrderHistoryRepository
import com.example.mycommerce.data.repository.UserRepository
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

@HiltViewModel
class MyCommerceViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val orderHistoryRepository: OrderHistoryRepository,
    private val addressRepository: AddressRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {


    private val _popupNotification = MutableStateFlow<Event<String>?>(null)
    val popupNotification: StateFlow<Event<String>?> get() = _popupNotification.asStateFlow()

    private val _username = MutableStateFlow(TextFieldValue(""))
    val username: StateFlow<TextFieldValue> get() = _username

    private val _email = MutableStateFlow(TextFieldValue(""))
    val email: StateFlow<TextFieldValue> get() = _email

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password: StateFlow<TextFieldValue> get() = _password

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> get() = _usernameError

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> get() = _emailError

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> get() = _passwordError

    private val _cartItems = MutableStateFlow<List<ECommerceItem>>(emptyList())
    val cartItems: StateFlow<List<ECommerceItem>> get() = _cartItems

    private val _orderStatus = MutableStateFlow<OrderStatus?>(null)
    val orderStatus: StateFlow<OrderStatus?> get() = _orderStatus

    private val _orderHistory = MutableStateFlow<List<OrderHistoryItem>>(emptyList())
    val orderHistory: StateFlow<List<OrderHistoryItem>> get() = _orderHistory

    private val _allUsersWithOrders =
        MutableStateFlow<List<Pair<User, List<OrderHistoryItem>>>>(emptyList())
    val allUsersWithOrders: StateFlow<List<Pair<User, List<OrderHistoryItem>>>> get() = _allUsersWithOrders

    private val _userId = MutableStateFlow<String>("")
    val userId: StateFlow<String> get() = _userId

    private val _firstName = MutableStateFlow(TextFieldValue(""))
    val firstName: StateFlow<TextFieldValue> get() = _firstName

    private val _lastName = MutableStateFlow(TextFieldValue(""))
    val lastName: StateFlow<TextFieldValue> get() = _lastName

    private val _mobileNumber = MutableStateFlow(TextFieldValue(""))
    val mobileNumber: StateFlow<TextFieldValue> get() = _mobileNumber

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> get() = _imageUrl

    private val _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn: StateFlow<Boolean> get() = _isUserSignedIn

    private val _firstNameError = MutableStateFlow<String?>(null)
    val firstNameError: StateFlow<String?> get() = _firstNameError

    private val _lastNameError = MutableStateFlow<String?>(null)
    val lastNameError: StateFlow<String?> get() = _lastNameError

    private val _mobileNumberError = MutableStateFlow<String?>(null)
    val mobileNumberError: StateFlow<String?> get() = _mobileNumberError

    private val _userAddresses = MutableStateFlow<List<UserAddressDetails>>(emptyList())
    val userAddresses: StateFlow<List<UserAddressDetails>> get() = _userAddresses

    private val _selectedAddress = MutableStateFlow<UserAddressDetails?>(null)
    val selectedAddress: StateFlow<UserAddressDetails?> get() = _selectedAddress


    init {
        viewModelScope.launch {
            val currentUser = userRepository.getCurrentUser().first()
            if (currentUser != null) {
                _userId.value = currentUser.id
                updateUserIdAndSignInState(currentUser.id, true)
                fetchOrderHistory(currentUser.id)
                fetchUserDetails(currentUser.id)
                fetchUserAddresses(currentUser.id)
            }
        }
    }


    private fun clearUserData() {
        _orderHistory.value = emptyList()
        _userAddresses.value = emptyList()
        _firstName.value = TextFieldValue("")
        _lastName.value = TextFieldValue("")
        _mobileNumber.value = TextFieldValue("")
        _email.value = TextFieldValue("")
        _imageUrl.value = null
    }

    private fun updateUserIdAndSignInState(userId: String?, isSignedIn: Boolean) {
        if (userId != null) {
            _userId.value = userId
        }
        _isUserSignedIn.value = isSignedIn
    }

    fun placeOrder() {
        if (_isUserSignedIn.value) {
            val userId = _userId.value ?: return
            val items = _cartItems.value
            val totalPrice = calculateTotalPrice()
            val orderStatus = OrderStatus.PLACED

            val orderHistoryItem = OrderHistoryItem(
                userId = userId, items = items, status = orderStatus, totalPrice = totalPrice
            )

            viewModelScope.launch {
                try {
                    orderHistoryRepository.addOrderHistoryItem(orderHistoryItem)
                    _orderStatus.value = orderStatus
                    val updatedOrderHistory = _orderHistory.value.toMutableList()
                    updatedOrderHistory.add(orderHistoryItem)
                    _orderHistory.value = updatedOrderHistory
                    clearCart()
                } catch (e: Exception) {
                    handleException(e, "Failed to place order")
                }
            }
        } else {
            _popupNotification.value = Event("Please sign in to place an order")
        }
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
                        firstName = "",
                        passwordHash = "",
                        lastName = "",
                        phoneNumber = "",
                        profileImage = ""
                    )
                    val success = userRepository.insertUser(newUser, password)
                    if (success) {
                        _popupNotification.value = Event("Sign-up successful")
                        userRepository.setCurrentUser(newUser.id)
                        updateUserIdAndSignInState(newUser.id, true)
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
                    userRepository.setCurrentUser(user.id)
                    fetchUserAddresses(user.id)
                    fetchOrderHistory(user.id)
                    fetchUserDetails(user.id)
                } else {
                    _popupNotification.value = Event("Invalid email or password")
                }
            }
        }
    }

    fun signOut() {
        userRepository.setCurrentUser(null)
        updateUserIdAndSignInState(null, false)
        clearCart()
        _orderHistory.value = emptyList()
        _orderStatus.value = null
        resetForm()
        _popupNotification.value = Event("Signed out successfully")
    }

    private fun resetForm() {
        _username.value = TextFieldValue("")
        _email.value = TextFieldValue("")
        _password.value = TextFieldValue("")
        _firstName.value = TextFieldValue("")
        _lastName.value = TextFieldValue("")
        _mobileNumber.value = TextFieldValue("")
        _imageUrl.value = null
        _usernameError.value = null
        _emailError.value = null
        _passwordError.value = null
        _firstNameError.value = null
        _lastNameError.value = null
        _mobileNumberError.value = null
    }

    fun onFirstNameChange(newValue: TextFieldValue) {
        _firstName.value = newValue
        validateFirstName(newValue.text)
    }

    fun onLastNameChange(newValue: TextFieldValue) {
        _lastName.value = newValue
        validateLastName(newValue.text)
    }

    fun onMobileNumberChange(newValue: TextFieldValue) {
        _mobileNumber.value = newValue
        validateMobileNumber(newValue.text)
    }

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
        _usernameError.value = if (username.isEmpty()) "Username is required" else null
    }

    private fun validateEmail(email: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        _emailError.value = when {
            email.isEmpty() -> "Email is required"
            !email.matches(emailRegex) -> "Invalid email format"
            else -> null
        }
    }

    private fun validatePassword(password: String) {
        _passwordError.value = if (password.isEmpty()) "Password is required" else null
    }

    private fun validateForm(): Boolean {
        validateUsername(_username.value.text)
        validateEmail(_email.value.text)
        validatePassword(_password.value.text)
        return _usernameError.value == null && _emailError.value == null && _passwordError.value == null
    }

    private fun validateFirstName(firstName: String) {
        _firstNameError.value = if (firstName.isEmpty()) "First name is required" else null
    }

    private fun validateLastName(lastName: String) {
        _lastNameError.value = if (lastName.isEmpty()) "Last name is required" else null
    }

    private fun validateMobileNumber(mobileNumber: String) {
        val mobileRegex = "^\\+?[1-9]\\d{1,14}$".toRegex()
        _mobileNumberError.value = when {
            mobileNumber.isEmpty() -> "Mobile number is required"
            !mobileNumber.matches(mobileRegex) -> "Invalid mobile number format"
            else -> null
        }
    }

    fun onFirstNameDoneClick(newValue: TextFieldValue) {
        viewModelScope.launch {
            val currentUser = _userId.value?.let { userRepository.getUser(it).firstOrNull() }
            if (currentUser != null) {
                val updatedUser = currentUser.copy(firstName = newValue.text)
                userRepository.updateUser(updatedUser)
                Event("First name updated successfully")
            }
        }
    }

    fun onLastNameDoneClick(newValue: TextFieldValue) {
        viewModelScope.launch {
            val currentUser = _userId.value?.let { userRepository.getUser(it).firstOrNull() }
            if (currentUser != null) {
                val updatedUser = currentUser.copy(lastName = newValue.text)
                userRepository.updateUser(updatedUser)
                Event("Last name updated successfully")
            }
        }
    }

    fun onEmailDoneClick(newValue: TextFieldValue) {
        viewModelScope.launch {
            val currentUser = _userId.value?.let { userRepository.getUser(it).firstOrNull() }
            if (currentUser != null) {
                val updatedUser = currentUser.copy(email = newValue.text)
                userRepository.updateUser(updatedUser)
                Event("Email updated successfully")
            }
        }
    }

    fun onPhoneNumberDoneClick(newValue: TextFieldValue) {
        viewModelScope.launch {
            val currentUser = _userId.value?.let { userRepository.getUser(it).firstOrNull() }
            if (currentUser != null) {
                val updatedUser = currentUser.copy(phoneNumber = newValue.text)
                userRepository.updateUser(updatedUser)
                Event("Phone number updated successfully")
            }
        }
    }

    fun onImageUrlChange(newValue: String) {
        viewModelScope.launch {
            _imageUrl.value = newValue
            val currentUser = userRepository.getCurrentUser().first()
            if (currentUser != null) {
                val updatedUser = currentUser.copy(profileImage = newValue)
                userRepository.updateUser(updatedUser)
            }
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

    fun calculateTotalPrice(): Int {
        return _cartItems.value.sumOf { it.itemPrice }.roundToInt()
    }

    private fun getItemDetails(itemId: String): ECommerceItem? {
        return _cartItems.value.find { it.id == itemId }
    }

    private fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val error = exception?.localizedMessage ?: "Something went wrong"
        val message = if (customMessage.isNotEmpty()) "$customMessage: $error" else error
        _popupNotification.value = Event(message)
    }

    private suspend fun fetchUserDetails(userId: String) {
        userRepository.getUser(userId).collect { user ->
            if (user != null) {
                _firstName.value = TextFieldValue(user.firstName)
                _lastName.value = TextFieldValue(user.lastName)
                _mobileNumber.value = TextFieldValue(user.phoneNumber)
                _email.value = TextFieldValue(user.email)
                _imageUrl.value = user.profileImage
            }
        }
    }

    fun fetchAllUsersWithOrders() {
        viewModelScope.launch {
            try {
                val usersWithOrders = orderHistoryRepository.getAllUsersWithOrders()
                _allUsersWithOrders.value = usersWithOrders
            } catch (e: Exception) {
                handleException(e, "Failed to fetch all users with orders")
            }
        }
    }

    private suspend fun fetchOrderHistory(userId: String) {
        viewModelScope.launch {
            try {
                val history = orderHistoryRepository.getOrderHistory(userId).first()
                _orderHistory.value = history
            } catch (e: Exception) {
                handleException(e, "Failed to fetch order history")
            }
        }
    }

    fun fetchUserAddresses(userId: String) {
        viewModelScope.launch {
            try {
                val addresses = addressRepository.getUserAddresses(userId).first()
                _userAddresses.value = addresses
            } catch (e: Exception) {
                handleException(e, "Failed to fetch user addresses")
            }
        }
    }

    // Ensure to call fetchUserAddresses whenever an address is added or deleted
    fun saveAddress(addressDetails: UserAddressDetails) {
        viewModelScope.launch {
            try {
                addressRepository.insertAddress(addressDetails)
                fetchUserAddresses(addressDetails.userId) // Update the list after save/update
                _popupNotification.value = Event("Address saved successfully")
            } catch (e: Exception) {
                handleException(e, "Failed to save address")
            }
        }
    }

    fun deleteAddress(addressDetails: UserAddressDetails) {
        viewModelScope.launch {
            try {
                addressRepository.deleteAddress(addressDetails)
                fetchUserAddresses(addressDetails.userId) // Update the list after deletion
                _popupNotification.value = Event("Address deleted successfully")
            } catch (e: Exception) {
                handleException(e, "Failed to delete address")
            }
        }
    }

    // Method to fetch details of a specific address
    fun getUserAddressDetails(addressId: Int) {
        viewModelScope.launch {
            try {
                val address = addressRepository.getAddressById(addressId).first()
                _selectedAddress.value = address
            } catch (e: Exception) {
                handleException(e, "Failed to fetch address details")
            }
        }
    }

    // Method to clear selected address
    fun clearSelectedAddress() {
        _selectedAddress.value = null
    }

    fun markAsDefault(addressId: Int, userId: String) {
        viewModelScope.launch {
            try {
                addressRepository.updateDefaultAddress(addressId, userId)
                fetchUserAddresses(userId) // Refresh the address list after update
            } catch (e: Exception) {
                handleException(e, "Failed to update default address")
            }
        }
    }

    fun updateAddress(addressDetails: UserAddressDetails) {
        viewModelScope.launch {
            try {
                addressRepository.updateAddress(addressDetails)
                fetchUserAddresses(addressDetails.userId) // Update the list after update
                _popupNotification.value = Event("Address updated successfully")
            } catch (e: Exception) {
                handleException(e, "Failed to update address")
            }
        }
    }
}
