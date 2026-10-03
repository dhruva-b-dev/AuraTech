package com.dhruva.auratech.ui.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.auratech.data.firestore.FirestoreModelMapper
import com.dhruva.auratech.data.firestore.FirestoreRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    productId: String = "deal_3"
) : ViewModel() {

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
    }

    private fun loadProductDetail(productId: String) {
        viewModelScope.launch {
            FirestoreRepository.getProductDetail(productId).collect { firestoreDetail ->
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
                _uiState.update {
                    val newFav = !it.isFavorite
                    it.copy(isFavorite = newFav)
                }
                val msg = if (_uiState.value.isFavorite) "Added to Wishlist" else "Removed from Wishlist"
                showToast(msg)
            }

            is ProductDetailAction.OnSelectWarranty -> {
                _uiState.update { it.copy(selectedWarrantyOption = action.option) }
            }

            is ProductDetailAction.OnAddToCart -> {
                _uiState.update {
                    it.copy(
                        cartItemCount = it.cartItemCount + 1,
                        isAddedToCart = true
                    )
                }
                showToast("Added to Cart ✓")
                cartBtnJob?.cancel()
                cartBtnJob = viewModelScope.launch {
                    delay(2000)
                    _uiState.update { it.copy(isAddedToCart = false) }
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
        cartBtnJob?.cancel()
    }
}
