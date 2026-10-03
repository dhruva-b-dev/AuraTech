package com.dhruva.auratech.ui.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.auratech.data.MockData
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.home.components.StoreBottomNavBar
import com.dhruva.auratech.ui.profile.components.AccountMenuCard
import com.dhruva.auratech.ui.profile.components.LogoutButton
import com.dhruva.auratech.ui.profile.components.OrdersStatusCard
import com.dhruva.auratech.ui.profile.components.ProfileHeaderCard
import com.dhruva.auratech.ui.profile.components.ProfileTopBar
import com.dhruva.auratech.ui.profile.components.WalletLoyaltyWidget
import com.dhruva.auratech.ui.theme.AuraTechTheme
import com.dhruva.auratech.ui.theme.DarkBackground

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            ProfileTopBar(
                onNotificationsClick = { onAction(ProfileAction.OnNotificationsClick) }
            )
        },
        bottomBar = {
            StoreBottomNavBar(
                selectedTab = uiState.selectedTab,
                cartItemCount = uiState.cartItemCount,
                onTabSelected = { tab -> onAction(ProfileAction.OnTabSelected(tab)) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                uiState.userProfile?.let {
                    ProfileHeaderCard(
                        user = it,
                        onEditClick = { onAction(ProfileAction.OnEditProfileClick) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                uiState.userProfile?.let {
                    WalletLoyaltyWidget(
                        walletBalance = it.walletBalance,
                        rewardPoints = uiState.userProfile.rewardPoints,
                        couponsCount = uiState.userProfile.activeCouponsCount,
                        onTopUpClick = { onAction(ProfileAction.OnTopUpWallet) },
                        onRedeemClick = { onAction(ProfileAction.OnRedeemRewards) },
                        onViewCouponsClick = { onAction(ProfileAction.OnViewCoupons) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                uiState.orderStatus?.let {
                    uiState.activeDelivery?.let { activeDelivery ->
                        OrdersStatusCard(
                            orderStatus = it,
                            activeDelivery = activeDelivery,
                            onStatusClick = { status -> onAction(ProfileAction.OnOrderStatusClick(status)) },
                            onViewAllClick = { onAction(ProfileAction.OnViewAllOrdersClick) },
                            onTrackDeliveryClick = { onAction(ProfileAction.OnTrackDeliveryClick) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                AccountMenuCard(
                    menuItems = uiState.menuItems,
                    onItemClick = { itemId -> onAction(ProfileAction.OnMenuItemClick(itemId)) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                LogoutButton(
                    onClick = { onAction(ProfileAction.OnLogoutClick) }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            CartFeedbackToast(
                message = uiState.toastMessage,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    AuraTechTheme() {
        ProfileScreen(
            uiState = ProfileUiState(
                userProfile = MockData.sampleUserProfile,
                orderStatus = MockData.sampleOrderStatus,
                activeDelivery = MockData.sampleActiveDelivery,
                menuItems = MockData.profileMenuItems,
                cartItemCount = 2
            ),
            onAction = {}
        )
    }
}