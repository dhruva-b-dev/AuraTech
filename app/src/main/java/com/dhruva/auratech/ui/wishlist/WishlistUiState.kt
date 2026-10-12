package com.dhruva.auratech.ui.wishlist

import com.dhruva.auratech.model.Product

data class WishlistUiState(
    val wishlistProducts: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val toastMessage: String? = null
)

sealed interface WishlistAction {
    data class OnProductClick(val productId: String) : WishlistAction
    data class OnToggleFavorite(val productId: String) : WishlistAction
    data class OnAddToCart(val product: Product) : WishlistAction
    data object OnBackClick : WishlistAction
    data object OnDismissToast : WishlistAction
}
