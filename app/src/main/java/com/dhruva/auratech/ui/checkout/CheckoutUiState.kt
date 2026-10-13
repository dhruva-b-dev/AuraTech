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
    val appliedCoupon: String? = null,
    val totalPayable: Double = 0.0,
    val isProcessing: Boolean = false,
    val stripeClientSecret: String? = null,
    val orderSuccessId: String? = null,
    val toastMessage: String? = null,
    val isLoading: Boolean = true,
    val showAddAddressDialog: Boolean = false,
    val titleInput: String = "Home",
    val fullNameInput: String = "",
    val streetAddressInput: String = "",
    val cityInput: String = "",
    val stateInput: String = "",
    val postalCodeInput: String = "",
    val phoneInput: String = ""
) {
    val selectedAddress: Address?
        get() = addresses.find { it.id == selectedAddressId } ?: addresses.firstOrNull { it.isDefault } ?: addresses.firstOrNull()
}

sealed interface CheckoutAction {
    data class OnSelectAddress(val addressId: String) : CheckoutAction
    data class OnSelectPayment(val method: String) : CheckoutAction
    data class OnSelectStep(val step: CheckoutStep) : CheckoutAction
    data object OnNextStep : CheckoutAction
    data object OnPreviousStep : CheckoutAction
    data object OnPlaceOrder : CheckoutAction
    data class OnStripePaymentResult(val result: com.stripe.android.paymentsheet.PaymentSheetResult) : CheckoutAction
    data object OnClearStripeSecret : CheckoutAction
    data object OnFinishCheckout : CheckoutAction
    data object OnDismissToast : CheckoutAction
    data object OnBackClick : CheckoutAction
    data object OnAddAddressClick : CheckoutAction
    data object OnDismissAddressDialog : CheckoutAction
    data object OnSaveAddress : CheckoutAction
    data class OnTitleChanged(val value: String) : CheckoutAction
    data class OnFullNameChanged(val value: String) : CheckoutAction
    data class OnStreetChanged(val value: String) : CheckoutAction
    data class OnCityChanged(val value: String) : CheckoutAction
    data class OnStateChanged(val value: String) : CheckoutAction
    data class OnPostalCodeChanged(val value: String) : CheckoutAction
    data class OnPhoneChanged(val value: String) : CheckoutAction
}

