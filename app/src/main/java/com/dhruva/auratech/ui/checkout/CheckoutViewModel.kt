package com.dhruva.auratech.ui.checkout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.auratech.data.firestore.FirestoreAddress
import com.dhruva.auratech.data.firestore.FirestoreModelMapper
import com.dhruva.auratech.data.firestore.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    savedStateHandle: SavedStateHandle,
    firebaseAuth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {

    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"

    private val couponCodeArg: String? = savedStateHandle.get<String>("couponCode")?.takeIf { it.isNotBlank() && it != "null" }
    private val couponDiscountArg: Double = savedStateHandle.get<String>("couponDiscount")?.toDoubleOrNull() ?: 0.0

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null

    init {
        loadCheckoutData()
    }

    private fun loadCheckoutData() {
        viewModelScope.launch {
            firestoreRepository.ensureUserProfileExists(userId)
            combine(
                firestoreRepository.getUserAddresses(userId),
                firestoreRepository.getUserCartItems(userId)
            ) { firestoreAddresses, firestoreCartItems ->
                val addresses = firestoreAddresses.map { FirestoreModelMapper.toAddress(it) }
                val cartItems = firestoreCartItems.map { FirestoreModelMapper.toCartItem(it) }

                val subtotal = cartItems.sumOf { it.currentPrice * it.quantity }
                val discountSavings = cartItems.sumOf { (it.originalPrice - it.currentPrice).coerceAtLeast(0.0) * it.quantity }
                val totalPayable = (subtotal - discountSavings - couponDiscountArg).coerceAtLeast(0.0)

                _uiState.update { state ->
                    state.copy(
                        addresses = addresses,
                        selectedAddressId = state.selectedAddressId ?: addresses.firstOrNull { it.isDefault }?.id ?: addresses.firstOrNull()?.id,
                        cartItems = cartItems,
                        subtotal = subtotal,
                        discountSavings = discountSavings,
                        couponDiscount = couponDiscountArg,
                        appliedCoupon = couponCodeArg,
                        totalPayable = totalPayable,
                        isLoading = false
                    )
                }
            }.collect {}
        }
    }

    fun onAction(action: CheckoutAction) {
        when (action) {
            is CheckoutAction.OnSelectAddress -> {
                _uiState.update { it.copy(selectedAddressId = action.addressId) }
            }

            is CheckoutAction.OnSelectPayment -> {
                _uiState.update { it.copy(selectedPaymentMethod = action.method) }
            }

            is CheckoutAction.OnSelectStep -> {
                _uiState.update { it.copy(currentStep = action.step) }
            }

            is CheckoutAction.OnNextStep -> {
                val current = _uiState.value.currentStep
                when (current) {
                    CheckoutStep.ADDRESS -> {
                        if (_uiState.value.selectedAddress == null) {
                            showToast("Please select a shipping address.")
                            return
                        }
                        _uiState.update { it.copy(currentStep = CheckoutStep.PAYMENT) }
                    }
                    CheckoutStep.PAYMENT -> {
                        _uiState.update { it.copy(currentStep = CheckoutStep.REVIEW) }
                    }
                    else -> {}
                }
            }

            is CheckoutAction.OnPreviousStep -> {
                val current = _uiState.value.currentStep
                when (current) {
                    CheckoutStep.PAYMENT -> _uiState.update { it.copy(currentStep = CheckoutStep.ADDRESS) }
                    CheckoutStep.REVIEW -> _uiState.update { it.copy(currentStep = CheckoutStep.PAYMENT) }
                    else -> {}
                }
            }

            is CheckoutAction.OnAddAddressClick -> {
                _uiState.update { it.copy(showAddAddressDialog = true) }
            }

            is CheckoutAction.OnDismissAddressDialog -> {
                _uiState.update { it.copy(showAddAddressDialog = false) }
            }

            is CheckoutAction.OnTitleChanged -> _uiState.update { it.copy(titleInput = action.value) }
            is CheckoutAction.OnFullNameChanged -> _uiState.update { it.copy(fullNameInput = action.value) }
            is CheckoutAction.OnStreetChanged -> _uiState.update { it.copy(streetAddressInput = action.value) }
            is CheckoutAction.OnCityChanged -> _uiState.update { it.copy(cityInput = action.value) }
            is CheckoutAction.OnStateChanged -> _uiState.update { it.copy(stateInput = action.value) }
            is CheckoutAction.OnPostalCodeChanged -> _uiState.update { it.copy(postalCodeInput = action.value) }
            is CheckoutAction.OnPhoneChanged -> _uiState.update { it.copy(phoneInput = action.value) }

            is CheckoutAction.OnSaveAddress -> {
                val state = _uiState.value
                if (state.fullNameInput.isBlank() || state.streetAddressInput.isBlank() || state.cityInput.isBlank() || state.phoneInput.isBlank()) {
                    showToast("Please fill in all required address fields.")
                    return
                }
                viewModelScope.launch {
                    val newId = "addr_${System.currentTimeMillis()}"
                    val isFirst = state.addresses.isEmpty()
                    val firestoreAddress = FirestoreAddress(
                        id = newId,
                        title = state.titleInput.ifBlank { "Home" },
                        fullName = state.fullNameInput,
                        streetAddress = state.streetAddressInput,
                        city = state.cityInput,
                        state = state.stateInput,
                        postalCode = state.postalCodeInput,
                        phone = state.phoneInput,
                        isDefault = isFirst
                    )
                    firestoreRepository.addAddress(userId, firestoreAddress)
                    _uiState.update {
                        it.copy(
                            showAddAddressDialog = false,
                            selectedAddressId = newId,
                            titleInput = "Home",
                            fullNameInput = "",
                            streetAddressInput = "",
                            cityInput = "",
                            stateInput = "",
                            postalCodeInput = "",
                            phoneInput = ""
                        )
                    }
                    showToast("New shipping address added!")
                }
            }

            is CheckoutAction.OnPlaceOrder -> {
                val state = _uiState.value
                if (state.cartItems.isEmpty()) {
                    showToast("Your cart is empty!")
                    return
                }
                if (state.selectedAddress == null) {
                    showToast("Please select a shipping address.")
                    return
                }

                _uiState.update { it.copy(isProcessing = true) }
                viewModelScope.launch {
                    if (state.selectedPaymentMethod.contains("Cash on Delivery", ignoreCase = true)) {
                        finalizeOrder("COD-${Random.nextInt(100000, 999999)}")
                    } else {
                        val amountCents = (state.totalPayable * 100).toLong().coerceAtLeast(50)
                        val clientSecret = com.dhruva.auratech.data.stripe.StripeApiHelper.createPaymentIntent(amountCents)
                        if (clientSecret != null && clientSecret.isNotBlank()) {
                            _uiState.update { it.copy(isProcessing = false, stripeClientSecret = clientSecret) }
                        } else {
                            _uiState.update { it.copy(isProcessing = false) }
                            showToast("Failed to initialize secure payment. Please try again.")
                        }
                    }
                }
            }

            is CheckoutAction.OnClearStripeSecret -> {
                _uiState.update { it.copy(stripeClientSecret = null) }
            }

            is CheckoutAction.OnStripePaymentResult -> {
                when (action.result) {
                    is com.stripe.android.paymentsheet.PaymentSheetResult.Completed -> {
                        val orderId = "AURA-${Random.nextInt(100000, 999999)}"
                        finalizeOrder(orderId)
                        showToast("Payment Successful!")
                    }
                    is com.stripe.android.paymentsheet.PaymentSheetResult.Canceled -> {
                        showToast("Payment Canceled")
                    }
                    is com.stripe.android.paymentsheet.PaymentSheetResult.Failed -> {
                        showToast("Payment Failed: ${action.result.error.localizedMessage}")
                    }
                }
                _uiState.update { it.copy(stripeClientSecret = null) }
            }

            is CheckoutAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }

            else -> {}
        }
    }

    private fun showToast(message: String) {
        toastJob?.cancel()
        _uiState.update { it.copy(toastMessage = message) }
        toastJob = viewModelScope.launch {
            delay(2200.milliseconds)
            _uiState.update { it.copy(toastMessage = null) }
        }
    }

    private fun finalizeOrder(orderId: String) {
        viewModelScope.launch {
            val state = _uiState.value
            val address = state.selectedAddress
            val addressStr = if (address != null) "${address.fullName}, ${address.streetAddress}, ${address.city}, ${address.state} ${address.postalCode}" else "Standard Shipping"
            val itemsSummary = state.cartItems.joinToString(", ") { "${it.quantity}x ${it.title}" }

            val firestoreOrder = com.dhruva.auratech.data.firestore.FirestoreOrder(
                orderId = orderId,
                dateText = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.US).format(java.util.Date()),
                totalAmount = state.totalPayable,
                status = "Processing",
                itemsSummary = itemsSummary,
                shippingAddress = addressStr,
                paymentMethod = state.selectedPaymentMethod
            )

            firestoreRepository.addOrder(userId, firestoreOrder)
            firestoreRepository.clearUserCart(userId)

            _uiState.update {
                it.copy(
                    isProcessing = false,
                    orderSuccessId = orderId,
                    currentStep = CheckoutStep.SUCCESS,
                    stripeClientSecret = null
                )
            }
        }
    }
}

