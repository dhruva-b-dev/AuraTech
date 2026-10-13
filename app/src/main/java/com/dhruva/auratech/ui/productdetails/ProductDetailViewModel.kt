package com.dhruva.auratech.ui.productdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    firebaseAuth: com.google.firebase.auth.FirebaseAuth,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"
    private val productId: String = savedStateHandle.get<String>("productId") ?: "deal_4"

    private val _uiState = MutableStateFlow(
        ProductDetailUiState(
            productDetail = null,
            selectedGalleryIndex = 0,
            selectedColorIndex = 0,
            isFavorite = false,
            selectedWarrantyOption = 0,
            cartItemCount = 2,
            reviewUpvotes = 0,
            reviewDownvotes = 0,
            isLoading = true
        )
    )
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null
    private var cartBtnJob: Job? = null

    init {
        loadProductDetail(productId)
        observeCartCount()
    }

    private fun observeCartCount() {
        viewModelScope.launch {
            firestoreRepository.getUserCartItems(userId).collect { items ->
                val count = items.sumOf { it.quantity }
                _uiState.update { it.copy(cartItemCount = count) }
            }
        }
    }

    private fun loadProductDetail(productId: String) {
        Log.d("ProductDetailViewModel", "Loading product detail for productId: $productId")
        viewModelScope.launch {
            firestoreRepository.getProductDetail(productId).collect { firestoreDetail ->
                val productDetail = firestoreDetail?.let { FirestoreModelMapper.toProductDetail(it) }
                _uiState.update { currentState ->
                    currentState.copy(
                        productDetail = productDetail!!,
                        reviewUpvotes = productDetail.featuredReview?.upvotes ?: 0,
                        reviewDownvotes = productDetail?.featuredReview?.downvotes ?: 0,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onAction(action: ProductDetailAction) {
        when (action) {
            is ProductDetailAction.OnSelectGallery -> {
                _uiState.update { it.copy(selectedGalleryIndex = action.index) }
            }

            is ProductDetailAction.OnSelectColor -> {
                _uiState.update { it.copy(selectedColorIndex = action.index) }
            }

            is ProductDetailAction.OnToggleFavorite -> {
                viewModelScope.launch {
                    val detail = _uiState.value.productDetail
                    if (detail != null) {
                        val firestoreProd = com.dhruva.auratech.data.firestore.FirestoreProduct(
                            id = detail.id,
                            title = detail.title,
                            originalPrice = detail.originalPrice,
                            currentPrice = detail.currentPrice,
                            discountPercent = detail.discountPercent,
                            rating = detail.rating,
                            reviewCount = detail.reviewCount,
                            imageResName = when (detail.galleryImages.firstOrNull()) {
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
                            isFavorite = !_uiState.value.isFavorite
                        )
                        val isAdded = firestoreRepository.toggleUserWishlistProduct(userId, firestoreProd)
                        _uiState.update { it.copy(isFavorite = isAdded) }
                        showToast(if (isAdded) "Added to Wishlist" else "Removed from Wishlist")
                    }
                }
            }

            is ProductDetailAction.OnSelectWarranty -> {
                _uiState.update { it.copy(selectedWarrantyOption = action.option) }
            }

            is ProductDetailAction.OnAddToCart -> {
                viewModelScope.launch {
                    try {
                        firestoreRepository.ensureUserProfileExists(userId)
                        val detail = _uiState.value.productDetail
                        if (detail != null) {
                            val cartItem = com.dhruva.auratech.data.firestore.FirestoreCartItem(
                                id = "pdp_cart_${detail.id}",
                                productId = detail.id,
                                title = detail.title,
                                imageResName = when (detail.galleryImages.firstOrNull()) {
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
                                originalPrice = detail.originalPrice,
                                currentPrice = detail.currentPrice,
                                quantity = 1,
                                tagText = if (detail.discountPercent > 0) "${detail.discountPercent}% OFF" else "In Stock",
                                colorName = detail.colors.getOrNull(_uiState.value.selectedColorIndex)?.name ?: "Standard",
                                colorHex = "#1E293B"
                            )
                            firestoreRepository.addCartItem(userId, cartItem)
                        }
                        _uiState.update {
                            it.copy(
                                cartItemCount = it.cartItemCount + 1,
                                isAddedToCart = true
                            )
                        }
                        showToast("Added to Cart ✓")
                        cartBtnJob?.cancel()
                        cartBtnJob = viewModelScope.launch {
                            delay(2000.milliseconds)
                            _uiState.update { it.copy(isAddedToCart = false) }
                        }
                    } catch (e: Exception) {
                        showToast("Failed to add to cart: ${e.message}")
                    }
                }
            }

            is ProductDetailAction.OnShareClick -> {
                showToast("Product link copied to clipboard")
            }

            is ProductDetailAction.OnWriteReviewClick -> {
                showToast("Review submission dialog")
            }

            is ProductDetailAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }

            is ProductDetailAction.OnVoteReview -> {
                _uiState.update {
                    if (action.isUpvote) {
                        it.copy(reviewUpvotes = it.reviewUpvotes + 1)
                    } else {
                        it.copy(reviewDownvotes = it.reviewDownvotes + 1)
                    }
                }
                showToast("Thank you for your feedback!")
            }

            is ProductDetailAction.OnBackClick -> {
                // Handled in navigation
            }

            is ProductDetailAction.OnCartClick -> {
                // Handled in navigation
            }
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

    override fun onCleared() {
        toastJob?.cancel()
        cartBtnJob?.cancel()
    }
}
