package com.dhruva.auratech.ui.addresses

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
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class AddressesViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    firebaseAuth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {

    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"

    private val _uiState = MutableStateFlow(AddressUiState())
    val uiState: StateFlow<AddressUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null

    init {
        loadAddresses()
    }

    private fun loadAddresses() {
        viewModelScope.launch {
            firestoreRepository.getUserAddresses(userId).collect { firestoreAddresses ->
                val addresses = firestoreAddresses.map { FirestoreModelMapper.toAddress(it) }
                _uiState.update {
                    it.copy(
                        addresses = addresses,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onAction(action: AddressAction) {
        when (action) {
            is AddressAction.OnAddAddressClick -> {
                _uiState.update {
                    it.copy(
                        showAddDialog = true,
                        titleInput = "Home",
                        fullNameInput = "",
                        streetAddressInput = "",
                        cityInput = "",
                        stateInput = "",
                        postalCodeInput = "",
                        phoneInput = "",
                        isDefaultInput = _uiState.value.addresses.isEmpty()
                    )
                }
            }

            is AddressAction.OnDismissDialog -> {
                _uiState.update { it.copy(showAddDialog = false) }
            }

            is AddressAction.OnTitleChanged -> {
                _uiState.update { it.copy(titleInput = action.title) }
            }

            is AddressAction.OnFullNameChanged -> {
                _uiState.update { it.copy(fullNameInput = action.name) }
            }

            is AddressAction.OnStreetChanged -> {
                _uiState.update { it.copy(streetAddressInput = action.street) }
            }

            is AddressAction.OnCityChanged -> {
                _uiState.update { it.copy(cityInput = action.city) }
            }

            is AddressAction.OnStateChanged -> {
                _uiState.update { it.copy(stateInput = action.state) }
            }

            is AddressAction.OnPostalCodeChanged -> {
                _uiState.update { it.copy(postalCodeInput = action.code) }
            }

            is AddressAction.OnPhoneChanged -> {
                _uiState.update { it.copy(phoneInput = action.phone) }
            }

            is AddressAction.OnDefaultChanged -> {
                _uiState.update { it.copy(isDefaultInput = action.isDefault) }
            }

            is AddressAction.OnSaveAddress -> {
                val state = _uiState.value
                if (state.fullNameInput.isBlank() || state.streetAddressInput.isBlank() || state.cityInput.isBlank()) {
                    showToast("Please fill in required address fields.")
                    return
                }
                viewModelScope.launch {
                    val firestoreAddress = com.dhruva.auratech.data.firestore.FirestoreAddress(
                        title = state.titleInput,
                        fullName = state.fullNameInput,
                        streetAddress = state.streetAddressInput,
                        city = state.cityInput,
                        state = state.stateInput,
                        postalCode = state.postalCodeInput,
                        phone = state.phoneInput,
                        isDefault = state.isDefaultInput
                    )
                    firestoreRepository.addAddress(userId, firestoreAddress)
                    _uiState.update { it.copy(showAddDialog = false) }
                    showToast("Address saved successfully!")
                }
            }

            is AddressAction.OnDeleteAddress -> {
                viewModelScope.launch {
                    firestoreRepository.deleteAddress(userId, action.id)
                    showToast("Address deleted")
                }
            }

            is AddressAction.OnSetDefaultAddress -> {
                viewModelScope.launch {
                    firestoreRepository.setDefaultAddress(userId, action.id)
                    showToast("Default address updated")
                }
            }

            is AddressAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }

            else -> {}
        }
    }

    private fun showToast(message: String) {
        toastJob?.cancel()
        _uiState.update { it.copy(toastMessage = message) }
        toastJob = viewModelScope.launch {
            delay(2200.milliseconds)
            _uiState.update { it.copy(toastMessage = null) }
        }
    }
}
