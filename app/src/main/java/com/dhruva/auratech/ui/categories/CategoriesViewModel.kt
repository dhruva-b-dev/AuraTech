package com.dhruva.auratech.ui.categories

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
class CategoriesViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    firebaseAuth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {

    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"

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

    private fun loadFirestoreData() {
        viewModelScope.launch {
            firestoreRepository.getAllCategoryCollections().collect { firestoreCategories ->
                val mapped = firestoreCategories.map { FirestoreModelMapper.toCategoryCollection(it) }
                val categories = if (mapped.isEmpty() || mapped.all { it.subcategories.isEmpty() }) {
                    com.dhruva.auratech.data.MockData.sampleCategoryCollections
                } else {
                    mapped
                }
                _uiState.update { currentState ->
                    currentState.copy(
                        categories = categories,
                        selectedCategoryId = currentState.selectedCategoryId.ifEmpty { categories.firstOrNull()?.id.toString() },
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
                val newSubId = if (_uiState.value.selectedSubcategoryId == action.subcategoryId) null else action.subcategoryId
                _uiState.update {
                    it.copy(selectedSubcategoryId = newSubId)
                }
                val activeSub = _uiState.value.currentCategory?.subcategories?.find { it.id == newSubId }
                val msg = if (activeSub != null) "Filtered: ${activeSub.name}" else "Showing all ${uiState.value.currentCategory?.name ?: "items"}"
                showToast(msg)
            }

            is CategoriesAction.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = action.query) }
            }

            is CategoriesAction.OnClearSearch -> {
                _uiState.update { it.copy(searchQuery = "") }
            }

            is CategoriesAction.OnAddToCart -> {
                viewModelScope.launch {
                    try {
                        firestoreRepository.ensureUserProfileExists(userId)
                        val product = action.product
                        val cartItem = com.dhruva.auratech.data.firestore.FirestoreCartItem(
                            id = "cat_cart_${product.id}",
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
                        _uiState.update { it.copy(cartItemCount = it.cartItemCount + 1) }
                        showToast("Added '${product.title.take(18)}...' to Cart!")
                    } catch (e: Exception) {
                        showToast("Failed to add to cart: ${e.message}")
                    }
                }
            }

            is CategoriesAction.OnToggleFavorite -> {
                viewModelScope.launch {
                    val allProds = _uiState.value.categories.flatMap { it.products }
                    val targetProd = allProds.find { it.id == action.productId }
                    if (targetProd != null) {
                        val firestoreProd = FirestoreModelMapper.toFirestoreProduct(targetProd)
                        val isAdded = firestoreRepository.toggleUserWishlistProduct(userId, firestoreProd)
                        _uiState.update { state ->
                            val updatedCategories = state.categories.map { category ->
                                val updatedProducts = category.products.map { product ->
                                    if (product.id == action.productId) {
                                        product.copy(isFavorite = isAdded)
                                    } else {
                                        product
                                    }
                                }
                                category.copy(products = updatedProducts)
                            }
                            state.copy(categories = updatedCategories)
                        }
                        showToast(if (isAdded) "Added to Wishlist" else "Removed from Wishlist")
                    }
                }
            }

            is CategoriesAction.OnNotificationsClick -> {
                showToast("No new category updates")
            }

            is CategoriesAction.OnCertificationClick -> {
                showToast("Lossless Audio: Certified Hi-Res 96kHz/24bit")
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
