package com.dhruva.auratech.ui.addresses

import com.dhruva.auratech.model.Address

data class AddressUiState(
    val addresses: List<Address> = emptyList(),
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false,
    val titleInput: String = "Home",
    val fullNameInput: String = "",
    val streetAddressInput: String = "",
    val cityInput: String = "",
    val stateInput: String = "",
    val postalCodeInput: String = "",
    val phoneInput: String = "",
    val isDefaultInput: Boolean = false,
    val toastMessage: String? = null
)

sealed interface AddressAction {
    data object OnBackClick : AddressAction
    data object OnAddAddressClick : AddressAction
    data object OnDismissDialog : AddressAction
    data class OnTitleChanged(val title: String) : AddressAction
    data class OnFullNameChanged(val name: String) : AddressAction
    data class OnStreetChanged(val street: String) : AddressAction
    data class OnCityChanged(val city: String) : AddressAction
    data class OnStateChanged(val state: String) : AddressAction
    data class OnPostalCodeChanged(val code: String) : AddressAction
    data class OnPhoneChanged(val phone: String) : AddressAction
    data class OnDefaultChanged(val isDefault: Boolean) : AddressAction
    data object OnSaveAddress : AddressAction
    data class OnDeleteAddress(val id: String) : AddressAction
    data class OnSetDefaultAddress(val id: String) : AddressAction
    data object OnDismissToast : AddressAction
}
