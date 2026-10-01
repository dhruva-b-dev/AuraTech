package com.dhruva.auratech.ui.productdetails

import com.dhruva.auratech.model.ProductDetail

data class ProductDetailUiState(
    val productDetail: ProductDetail,
    val selectedGalleryIndex: Int = 0,
    val selectedColorIndex: Int = 0,
    val isFavorite: Boolean = false,
    val selectedWarrantyOption: Int = 0,
    val cartItemCount: Int = 2,
    val isAddedToCart: Boolean = false,
    val toastMessage: String? = null,
    val reviewUpvotes: Int = 22,
    val reviewDownvotes: Int = 1
) {
    val currentImageRes: Int
        get() = productDetail.galleryImages.getOrElse(selectedGalleryIndex) {
            productDetail.galleryImages.first()
        }

    val selectedColorName: String
        get() = productDetail.colors.getOrElse(selectedColorIndex) {
            productDetail.colors.first()
        }.name
}

sealed interface ProductDetailAction {
    data class OnSelectGallery(val index: Int) : ProductDetailAction
    data class OnSelectColor(val index: Int) : ProductDetailAction
    data object OnToggleFavorite : ProductDetailAction
    data class OnSelectWarranty(val option: Int) : ProductDetailAction
    data object OnAddToCart : ProductDetailAction
    data object OnShareClick : ProductDetailAction
    data object OnWriteReviewClick : ProductDetailAction
    data object OnBackClick : ProductDetailAction
    data object OnDismissToast : ProductDetailAction
    data class OnVoteReview(val isUpvote: Boolean) : ProductDetailAction
}
