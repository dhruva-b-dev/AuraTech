package com.dhruva.auratech.ui.categories

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

class CategoriesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        CategoriesUiState(
            categories = emptyList(),
            selectedCategoryId = "",
            cartItemCount = 2,
            isLoading = true
        )
    )
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null

    init {
        loadFirestoreData()
    }

    private fun loadFirestoreData() {
        viewModelScope.launch {
            FirestoreRepository.getAllCategoryCollections().collect { firestoreCategories ->
                val categories = firestoreCategories.map { FirestoreModelMapper.toCategoryCollection(it) }
                _uiState.update { currentState ->
                    currentState.copy(
                        categories = categories,
                        selectedCategoryId = categories.firstOrNull()?.id.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onAction(action: CategoriesAction) {
        when (action) {
            is CategoriesAction.OnSelectCategory -> {
                _uiState.update {
                    it.copy(
                        selectedCategoryId = action.categoryId,
                        selectedSubcategoryId = null,
                        searchQuery = ""
                    )
                }
            }

            is CategoriesAction.OnSelectSubcategory -> {
                _uiState.update {
                    it.copy(selectedSubcategoryId = action.subcategoryId)
                }
            }

            is CategoriesAction.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = action.query) }
            }

            is CategoriesAction.OnClearSearch -> {
                _uiState.update { it.copy(searchQuery = "") }
            }

            is CategoriesAction.OnAddToCart -> {
                _uiState.update { it.copy(cartItemCount = it.cartItemCount + 1) }
                showToast("Added ${action.product.title.take(24)}... to Cart!")
            }

            is CategoriesAction.OnToggleFavorite -> {
                _uiState.update { state ->
                    val updatedCategories = state.categories.map { category ->
                        val updatedProducts = category.products.map { product ->
                            if (product.id == action.productId) {
                                product.copy(isFavorite = !product.isFavorite)
                            } else {
                                product
                            }
                        }
                        category.copy(products = updatedProducts)
                    }
                    state.copy(categories = updatedCategories)
                }
            }

            is CategoriesAction.OnNotificationsClick -> {
                showToast("No new category updates")
            }

            is CategoriesAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTab = action.tab) }
            }

            is CategoriesAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }

            else -> {
                // Product click is handled at navigation level
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
    }
}
