package com.dhruva.auratech.ui.cart

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    firebaseAuth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {

    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"

    private val _uiState = MutableStateFlow(
        CartUiState(
            items = emptyList(),
            isLoading = true
        )
    )
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null
    private var checkoutJob: Job? = null

    init {
        loadUserCart()
    }

    private fun loadUserCart() {
        viewModelScope.launch {
            firestoreRepository.ensureUserProfileExists(userId)
            firestoreRepository.getUserCartItems(userId).collect { firestoreItems ->
                val cartItems = firestoreItems.map { FirestoreModelMapper.toCartItem(it) }
                _uiState.update { it.copy(items = cartItems, isLoading = false) }
            }
        }
    }

    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.OnQuantityChanged -> {
                val item = _uiState.value.items.find { it.id == action.itemId }
                if (item != null) {
                    val newQuantity = (item.quantity + action.delta).coerceAtLeast(1)
                    viewModelScope.launch {
                        firestoreRepository.updateCartItem(
                            userId,
                            action.itemId,
                            mapOf("quantity" to newQuantity)
                        )
                    }
                }
            }

            is CartAction.OnRemoveItem -> {
                viewModelScope.launch {
                    firestoreRepository.removeCartItem(userId, action.itemId)
                }
                showToast("Item removed from cart")
            }

            is CartAction.OnClearCart -> {
                viewModelScope.launch {
                    firestoreRepository.clearUserCart(userId)
                }
                showToast("Cart cleared")
            }

            is CartAction.OnCouponInputChanged -> {
                _uiState.update { it.copy(couponInput = action.code) }
            }

            is CartAction.OnApplyCoupon -> {
                val input = _uiState.value.couponInput.trim().uppercase()
                when {
                    input.isEmpty() -> {
                        _uiState.update {
                            it.copy(
                                couponFeedback = "Please enter a promo code.",
                                isCouponError = true
                            )
                        }
                    }
                    input in listOf("NOROOZ", "FALL1403", "OFF10", "SAVE10") -> {
                        _uiState.update {
                            it.copy(
                                appliedCoupon = input,
                                couponDiscount = 10.00,
                                couponFeedback = "$10.00 promo discount successfully applied.",
                                isCouponError = false
                            )
                        }
                        showToast("Promo discount of $10.00 applied!")
                    }
                    else -> {
                        _uiState.update {
                            it.copy(
                                couponFeedback = "Invalid or expired promo code.",
                                isCouponError = true
                            )
                        }
                    }
                }
            }

            is CartAction.OnProceedToCheckout -> {
                if (_uiState.value.items.isEmpty()) {
                    showToast("Your cart is empty!")
                    return
                }
                _uiState.update { it.copy(isCheckingOut = true) }
                checkoutJob?.cancel()
                checkoutJob = viewModelScope.launch {
                    delay(1200)
                    _uiState.update { it.copy(isCheckingOut = false) }
                    showToast("Order placed successfully! Thank you.")
                }
            }

            is CartAction.OnNotificationsClick -> {
                showToast("No new notifications")
            }

            is CartAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTab = action.tab) }
            }

            is CartAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }

            else -> {
                // Other navigation-related actions are handled at NavHost level
            }
        }
    }

    private fun showToast(message: String) {
        toastJob?.cancel()
        _uiState.update { it.copy(toastMessage = message) }
        toastJob = viewModelScope.launch {
            delay(2200)
            _uiState.update { it.copy(toastMessage = null) }
        }
    }

    override fun onCleared() {
        toastJob?.cancel()
        checkoutJob?.cancel()
    }
}
