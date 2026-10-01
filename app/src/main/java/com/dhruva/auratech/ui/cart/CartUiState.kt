package com.dhruva.auratech.ui.cart

import com.dhruva.auratech.model.BottomNavTab
import com.dhruva.auratech.model.CartItem

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val couponInput: String = "",
    val appliedCoupon: String? = null,
    val couponDiscount: Double = 0.0,
    val couponFeedback: String? = null,
    val isCouponError: Boolean = false,
    val isCheckingOut: Boolean = false,
    val selectedTab: BottomNavTab = BottomNavTab.CART,
    val toastMessage: String? = null
) {
    val totalUniqueItems: Int
        get() = items.size

    val totalItemUnits: Int
        get() = items.sumOf { it.quantity }

    val subtotal: Double
        get() = items.sumOf { it.currentPrice * it.quantity }

    val discountSavings: Double
        get() = if (items.isEmpty()) 0.0 else items.sumOf { item ->
            when (item.id) {
                "item-1" -> 10.0 * item.quantity
                "item-2" -> 5.0 * item.quantity
                "item-3" -> 4.0 * item.quantity
                else -> (item.originalPrice - item.currentPrice).coerceAtLeast(0.0) * item.quantity
            }
        }

    val totalPayable: Double
        get() = if (items.isEmpty()) 0.0 else (subtotal - discountSavings - couponDiscount).coerceAtLeast(0.0)
}

sealed interface CartAction {
    data class OnQuantityChanged(val itemId: String, val delta: Int) : CartAction
    data class OnRemoveItem(val itemId: String) : CartAction
    data object OnClearCart : CartAction
    data class OnCouponInputChanged(val code: String) : CartAction
    data object OnApplyCoupon : CartAction
    data object OnProceedToCheckout : CartAction
    data class OnProductClick(val productId: String) : CartAction
    data object OnNotificationsClick : CartAction
    data object OnStartShoppingClick : CartAction
    data class OnTabSelected(val tab: BottomNavTab) : CartAction
    data object OnDismissToast : CartAction
}
