package com.dhruva.auratech.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    firebaseAuth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {

    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"

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
                val totalPayable = (subtotal - discountSavings).coerceAtLeast(0.0)

                _uiState.update { state ->
                    state.copy(
                        addresses = addresses,
                        selectedAddressId = state.selectedAddressId ?: addresses.firstOrNull { it.isDefault }?.id ?: addresses.firstOrNull()?.id,
                        cartItems = cartItems,
                        subtotal = subtotal,
                        discountSavings = discountSavings,
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

            is CheckoutAction.OnPlaceOrder -> {
                if (_uiState.value.cartItems.isEmpty()) {
                    showToast("Your cart is empty!")
                    return
                }
                if (_uiState.value.selectedAddress == null) {
                    showToast("Please select a shipping address.")
                    return
                }

                _uiState.update { it.copy(isProcessing = true) }
                viewModelScope.launch {
                    delay(1500.milliseconds) // Simulate secure payment gateway processing
                    val orderId = "AURA-${Random.nextInt(100000, 999999)}"
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
                    
                    // Clear cart
                    firestoreRepository.clearUserCart(userId)

                    _uiState.update {
                        it.copy(
                            isProcessing = false,
                            orderSuccessId = orderId,
                            currentStep = CheckoutStep.SUCCESS
                        )
                    }
                }
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
}
