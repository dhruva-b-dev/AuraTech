package com.dhruva.auratech.ui.profile

import com.dhruva.auratech.model.ActiveDelivery
import com.dhruva.auratech.model.BottomNavTab
import com.dhruva.auratech.model.OrderStatusCount
import com.dhruva.auratech.model.ProfileMenuItem
import com.dhruva.auratech.model.UserProfile

data class ProfileUiState(
    val userProfile: UserProfile?,
    val orderStatus: OrderStatusCount?,
    val activeDelivery: ActiveDelivery?,
    val menuItems: List<ProfileMenuItem>,
    val selectedTab: BottomNavTab = BottomNavTab.PROFILE,
    val cartItemCount: Int = 2,
    val toastMessage: String? = null,
    val isLoading: Boolean = false,
    val showEditProfileDialog: Boolean = false,
    val editNameInput: String = "",
    val editPhoneInput: String = "",
    val showCouponsDialog: Boolean = false
)

sealed interface ProfileAction {
    data object OnTopUpWallet : ProfileAction
    data object OnRedeemRewards : ProfileAction
    data object OnViewCoupons : ProfileAction
    data object OnDismissCoupons : ProfileAction
    data class OnOrderStatusClick(val statusName: String) : ProfileAction
    data object OnViewAllOrdersClick : ProfileAction
    data object OnTrackDeliveryClick : ProfileAction
    data class OnMenuItemClick(val itemId: String) : ProfileAction
    data object OnEditProfileClick : ProfileAction
    data object OnDismissEditProfile : ProfileAction
    data class OnEditNameChanged(val name: String) : ProfileAction
    data class OnEditPhoneChanged(val phone: String) : ProfileAction
    data object OnSaveProfile : ProfileAction
    data object OnNotificationsClick : ProfileAction
    data object OnLogoutClick : ProfileAction
    data class OnTabSelected(val tab: BottomNavTab) : ProfileAction
    data object OnDismissToast : ProfileAction
}
