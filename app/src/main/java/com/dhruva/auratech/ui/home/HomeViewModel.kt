package com.dhruva.auratech.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.auratech.data.MockData
import com.dhruva.auratech.model.Product
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            trustBadges = MockData.trustBadges,
            heroBanner = MockData.heroPromoBanner,
            remainingSeconds = MockData.heroPromoBanner.remainingSeconds,
            categories = MockData.categories,
            flashDeals = MockData.flashDeals,
            editorialBanners = MockData.editorialBanners,
            bestSellers = MockData.bestSellers,
            cartItemCount = 2
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var countdownJob: Job? = null
    private var toastJob: Job? = null

    init {
        startCountdownTimer()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnSearchQueryChanged -> {
                _uiState.update { currentState ->
                    currentState.copy(searchQuery = action.query)
                }
            }

            is HomeAction.OnCategorySelected -> {
                _uiState.update { currentState ->
                    val newSelection = if (currentState.selectedCategoryId == action.categoryId) {
                        null
                    } else {
                        action.categoryId
                    }
                    currentState.copy(selectedCategoryId = newSelection)
                }
            }

            is HomeAction.OnToggleFavorite -> {
                _uiState.update { currentState ->
                    val updatedBestSellers = currentState.bestSellers.map { product ->
                        if (product.id == action.productId) {
                            product.copy(isFavorite = !product.isFavorite)
                        } else {
                            product
                        }
                    }
                    val updatedFlashDeals = currentState.flashDeals.map { product ->
                        if (product.id == action.productId) {
                            product.copy(isFavorite = !product.isFavorite)
                        } else {
                            product
                        }
                    }
                    currentState.copy(
                        bestSellers = updatedBestSellers,
                        flashDeals = updatedFlashDeals
                    )
                }
            }

            is HomeAction.OnAddToCart -> {
                addItemToCart(action.product)
            }

            is HomeAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }

            is HomeAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTab = action.tab) }
            }

            is HomeAction.OnProductClick -> {
                // Navigation to PDP handled by router/screen callbacks
            }

            is HomeAction.OnScanBarcodeClick -> {
                showToast("Barcode scanner opened")
            }

            is HomeAction.OnFilterClick -> {
                showToast("Filters opened")
            }

            is HomeAction.OnBannerShopNowClick -> {
                showToast("Applied promo code FALL1403!")
            }

            is HomeAction.OnViewAllCategoriesClick -> {
                showToast("Viewing all categories")
            }

            is HomeAction.OnViewAllFlashDealsClick -> {
                showToast("Viewing all flash deals")
            }

            is HomeAction.OnNotificationsClick -> {
                showToast("No new notifications")
            }

            is HomeAction.OnBrandBagClick -> {
                showToast("Store Official Collection")
            }
        }
    }

    private fun addItemToCart(product: Product) {
        _uiState.update { currentState ->
            currentState.copy(cartItemCount = currentState.cartItemCount + 1)
        }
        showToast("Added to cart!")
    }

    private fun showToast(message: String) {
        toastJob?.cancel()
        _uiState.update { it.copy(toastMessage = message) }
        toastJob = viewModelScope.launch {
            delay(2200)
            _uiState.update { it.copy(toastMessage = null) }
        }
    }

    private fun startCountdownTimer() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                _uiState.update { currentState ->
                    if (currentState.remainingSeconds > 0) {
                        currentState.copy(remainingSeconds = currentState.remainingSeconds - 1)
                    } else {
                        currentState
                    }
                }
            }
        }
    }

    override fun onCleared() {
        countdownJob?.cancel()
        toastJob?.cancel()
    }
}
