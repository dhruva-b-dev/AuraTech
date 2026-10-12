package com.dhruva.auratech.ui.profile

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    firebaseAuth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {

    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            userProfile = null,
            orderStatus = null,
            activeDelivery = null,
            menuItems = emptyList(),
            cartItemCount = 2,
            isLoading = true
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var toastJob: Job? = null

    init {
        loadFirestoreData()
    }

    private fun loadFirestoreData() {
        viewModelScope.launch {
            firestoreRepository.ensureUserProfileExists(userId)
            combine(
                firestoreRepository.getUserProfile(userId),
                firestoreRepository.getOrderStatusCount(userId),
                firestoreRepository.getActiveDelivery(userId),
                firestoreRepository.getProfileMenuItems()
            ) { userProfile, orderStatus, activeDelivery, menuItems ->
                _uiState.update { currentState ->
                    currentState.copy(
                        userProfile = userProfile?.let { FirestoreModelMapper.toUserProfile(it) },
                        orderStatus = orderStatus?.let { FirestoreModelMapper.toOrderStatusCount(it) },
                        activeDelivery = activeDelivery?.let { FirestoreModelMapper.toActiveDelivery(it) },
                        menuItems = menuItems.map { FirestoreModelMapper.toProfileMenuItem(it) },
                        isLoading = false
                    )
                }
            }.collect{}
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnTopUpWallet -> {
                viewModelScope.launch {
                    firestoreRepository.updateUserProfile(
                        userId,
                        mapOf("walletBalance" to (_uiState.value.userProfile?.walletBalance ?: 0.0) + 25.00)
                    )
                }
                showToast("Added $25.00 to Wallet!")
            }

            is ProfileAction.OnRedeemRewards -> {
                showToast("Rewards Catalog opened")
            }

            is ProfileAction.OnViewCoupons -> {
                _uiState.update { it.copy(showCouponsDialog = true) }
            }

            is ProfileAction.OnDismissCoupons -> {
                _uiState.update { it.copy(showCouponsDialog = false) }
            }

            is ProfileAction.OnOrderStatusClick -> {
                showToast("Viewing ${action.statusName} orders")
            }

            is ProfileAction.OnViewAllOrdersClick -> {
                showToast("All orders history")
            }

            is ProfileAction.OnTrackDeliveryClick -> {
                showToast("Tracking Order #${_uiState.value.activeDelivery?.orderId}")
            }

            is ProfileAction.OnMenuItemClick -> {
                val item = _uiState.value.menuItems.find { it.id == action.itemId }
                showToast("Opened ${item?.title ?: "Menu"}")
            }

            is ProfileAction.OnEditProfileClick -> {
                val profile = _uiState.value.userProfile
                _uiState.update {
                    it.copy(
                        showEditProfileDialog = true,
                        editNameInput = profile?.name ?: "",
                        editPhoneInput = profile?.phone ?: ""
                    )
                }
            }

            is ProfileAction.OnDismissEditProfile -> {
                _uiState.update { it.copy(showEditProfileDialog = false) }
            }

            is ProfileAction.OnEditNameChanged -> {
                _uiState.update { it.copy(editNameInput = action.name) }
            }

            is ProfileAction.OnEditPhoneChanged -> {
                _uiState.update { it.copy(editPhoneInput = action.phone) }
            }

            is ProfileAction.OnSaveProfile -> {
                val state = _uiState.value
                viewModelScope.launch {
                    firestoreRepository.updateUserProfile(
                        userId,
                        mapOf(
                            "name" to state.editNameInput,
                            "phone" to state.editPhoneInput
                        )
                    )
                    _uiState.update { it.copy(showEditProfileDialog = false) }
                    showToast("Profile updated successfully!")
                }
            }

            is ProfileAction.OnNotificationsClick -> {
                showToast("No new notifications")
            }

            is ProfileAction.OnLogoutClick -> {
                showToast("Logged out successfully")
            }

            is ProfileAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTab = action.tab) }
            }

            is ProfileAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
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
