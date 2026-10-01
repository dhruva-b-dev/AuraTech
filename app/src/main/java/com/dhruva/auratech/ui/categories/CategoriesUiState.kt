package com.dhruva.auratech.ui.categories

import com.dhruva.auratech.model.BottomNavTab
import com.dhruva.auratech.model.CategoryCollection
import com.dhruva.auratech.model.Product

data class CategoriesUiState(
    val selectedCategoryId: String = "cat_digital",
    val selectedSubcategoryId: String? = null,
    val searchQuery: String = "",
    val categories: List<CategoryCollection> = emptyList(),
    val cartItemCount: Int = 2,
    val toastMessage: String? = null,
    val selectedTab: BottomNavTab = BottomNavTab.CATEGORIES
) {
    val currentCategory: CategoryCollection?
        get() = categories.find { it.id == selectedCategoryId } ?: categories.firstOrNull()

    val filteredProducts: List<Product>
        get() {
            val baseList = currentCategory?.products.orEmpty()
            return if (searchQuery.isBlank()) {
                baseList
            } else {
                baseList.filter {
                    it.title.contains(searchQuery, ignoreCase = true)
                }
            }
        }
}

sealed interface CategoriesAction {
    data class OnSelectCategory(val categoryId: String) : CategoriesAction
    data class OnSelectSubcategory(val subcategoryId: String?) : CategoriesAction
    data class OnSearchQueryChanged(val query: String) : CategoriesAction
    data object OnClearSearch : CategoriesAction
    data class OnProductClick(val productId: String) : CategoriesAction
    data class OnAddToCart(val product: Product) : CategoriesAction
    data class OnToggleFavorite(val productId: String) : CategoriesAction
    data object OnNotificationsClick : CategoriesAction
    data class OnTabSelected(val tab: BottomNavTab) : CategoriesAction
    data object OnDismissToast : CategoriesAction
}
