package com.dhruva.auratech.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.auratech.data.firestore.FirestoreModelMapper
import com.dhruva.auratech.data.firestore.FirestoreRepository
import com.dhruva.auratech.model.Product
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
class WishlistViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    firebaseAuth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {

    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"

    private val _uiState = MutableStateFlow(WishlistUiState())
    val uiState: StateFlow<WishlistUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null

    init {
        loadWishlist()
        observeCartCount()
    }

    private fun loadWishlist() {
        viewModelScope.launch {
            firestoreRepository.getUserWishlist(userId).collect { firestoreProducts ->
                val wishlist = firestoreProducts.map { FirestoreModelMapper.toProduct(it) }
                _uiState.update {
                    it.copy(
                        wishlistProducts = wishlist,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun observeCartCount() {
        viewModelScope.launch {
            firestoreRepository.getUserCartItems(userId).collect { items ->
                val count = items.sumOf { it.quantity }
                _uiState.update { it.copy(cartItemCount = count) }
            }
        }
    }

    fun onAction(action: WishlistAction) {
        when (action) {
            is WishlistAction.OnToggleFavorite -> {
                viewModelScope.launch {
                    val product = _uiState.value.wishlistProducts.find { it.id == action.productId }
                    if (product != null) {
                        val firestoreProduct = FirestoreModelMapper.toFirestoreProduct(product)
                        val added = firestoreRepository.toggleUserWishlistProduct(userId, firestoreProduct)
                        showToast(if (added) "Added to Wishlist" else "Removed from Wishlist")
                    }
                }
            }
            is WishlistAction.OnAddToCart -> {
                viewModelScope.launch {
                    try {
                        val product = action.product
                        val cartItem = com.dhruva.auratech.data.firestore.FirestoreCartItem(
                            id = "wish_cart_${product.id}",
                            productId = product.id,
                            title = product.title,
                            imageResName = when (product.imageRes) {
                                com.dhruva.auratech.R.drawable.product_earbuds -> "product_earbuds"
                                com.dhruva.auratech.R.drawable.product_smartwatch -> "product_smartwatch"
                                com.dhruva.auratech.R.drawable.product_headphones_main -> "product_headphones_main"
                                com.dhruva.auratech.R.drawable.product_keyboard -> "product_keyboard"
                                com.dhruva.auratech.R.drawable.product_serum -> "product_serum"
                                com.dhruva.auratech.R.drawable.product_tumbler -> "product_tumbler"
                                com.dhruva.auratech.R.drawable.product_lamp -> "product_lamp"
                                com.dhruva.auratech.R.drawable.product_case_peach -> "product_case_peach"
                                else -> "product_headphones_main"
                            },
                            originalPrice = product.originalPrice,
                            currentPrice = product.currentPrice,
                            quantity = 1,
                            tagText = product.badgeText ?: "In Stock",
                            colorName = "Color: Standard",
                            colorHex = "#1E293B"
                        )
                        firestoreRepository.addCartItem(userId, cartItem)
                        showToast("Added '${product.title.take(18)}...' to Cart!")
                    } catch (e: Exception) {
                        showToast("Failed to add to cart: ${e.message}")
                    }
                }
            }
            is WishlistAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }
            else -> {}
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
}
