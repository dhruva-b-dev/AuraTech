package com.dhruva.auratech.ui.orders

import com.dhruva.auratech.model.OrderItem

data class OrderUiState(
    val orders: List<OrderItem> = emptyList(),
    val isLoading: Boolean = true,
    val toastMessage: String? = null
)

sealed interface OrderAction {
    data object OnBackClick : OrderAction
    data object OnDismissToast : OrderAction
}
