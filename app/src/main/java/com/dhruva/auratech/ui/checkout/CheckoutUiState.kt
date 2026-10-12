package com.dhruva.auratech.ui.checkout

import com.dhruva.auratech.model.Address
import com.dhruva.auratech.model.CartItem

enum class CheckoutStep {
    ADDRESS,
    PAYMENT,
    REVIEW,
    SUCCESS
}

data class CheckoutUiState(
    val currentStep: CheckoutStep = CheckoutStep.ADDRESS,
    val addresses: List<Address> = emptyList(),
    val selectedAddressId: String? = null,
    val paymentMethods: List<String> = listOf("Credit / Debit Card", "Google Pay / Apple Pay", "Cash on Delivery"),
    val selectedPaymentMethod: String = "Credit / Debit Card",
    val cartItems: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val discountSavings: Double = 0.0,
    val couponDiscount: Double = 0.0,
    val totalPayable: Double = 0.0,
    val isProcessing: Boolean = false,
    val orderSuccessId: String? = null,
    val toastMessage: String? = null,
    val isLoading: Boolean = true
) {
    val selectedAddress: Address?
        get() = addresses.find { it.id == selectedAddressId } ?: addresses.firstOrNull { it.isDefault } ?: addresses.firstOrNull()
}

sealed interface CheckoutAction {
    data class OnSelectAddress(val addressId: String) : CheckoutAction
    data class OnSelectPayment(val method: String) : CheckoutAction
    data object OnNextStep : CheckoutAction
    data object OnPreviousStep : CheckoutAction
    data object OnPlaceOrder : CheckoutAction
    data object OnFinishCheckout : CheckoutAction
    data object OnDismissToast : CheckoutAction
    data object OnBackClick : CheckoutAction
}
