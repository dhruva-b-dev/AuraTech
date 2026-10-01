package com.dhruva.auratech.ui.home

import com.dhruva.auratech.model.BottomNavTab
import com.dhruva.auratech.model.Category
import com.dhruva.auratech.model.EditorialBanner
import com.dhruva.auratech.model.Product
import com.dhruva.auratech.model.PromoBanner
import com.dhruva.auratech.model.TrustBadge
data class HomeUiState(
    val searchQuery: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: String? = null,
    val heroBanner: PromoBanner? = null,
    val remainingSeconds: Long = 0L,
    val trustBadges: List<TrustBadge> = emptyList(),
    val flashDeals: List<Product> = emptyList(),
    val editorialBanners: List<EditorialBanner> = emptyList(),
    val bestSellers: List<Product> = emptyList(),
    val selectedTab: BottomNavTab = BottomNavTab.HOME,
    val cartItemCount: Int = 2,
    val toastMessage: String? = null,
    val isLoading: Boolean = false
) {
    val countdownHours: String
        get() = (remainingSeconds / 3600).coerceAtLeast(0).toString().padStart(2, '0')

    val countdownMinutes: String
        get() = ((remainingSeconds % 3600) / 60).coerceAtLeast(0).toString().padStart(2, '0')

    val countdownSeconds: String
        get() = (remainingSeconds % 60).coerceAtLeast(0).toString().padStart(2, '0')
}

sealed interface HomeAction {
    data class OnSearchQueryChanged(val query: String) : HomeAction
    data class OnCategorySelected(val categoryId: String) : HomeAction
    data class OnToggleFavorite(val productId: String) : HomeAction
    data class OnAddToCart(val product: Product) : HomeAction
    data object OnDismissToast : HomeAction
    data class OnTabSelected(val tab: BottomNavTab) : HomeAction
    data class OnProductClick(val productId: String) : HomeAction
    data object OnScanBarcodeClick : HomeAction
    data object OnFilterClick : HomeAction
    data object OnBannerShopNowClick : HomeAction
    data object OnViewAllCategoriesClick : HomeAction
    data object OnViewAllFlashDealsClick : HomeAction
    data object OnNotificationsClick : HomeAction
    data object OnBrandBagClick : HomeAction
}
