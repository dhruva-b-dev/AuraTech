package com.dhruva.auratech.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.auratech.R
import com.dhruva.auratech.model.ActiveDelivery
import com.dhruva.auratech.model.OrderStatusCount
import com.dhruva.auratech.model.ProfileMenuItem
import com.dhruva.auratech.model.UserProfile
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.home.components.StoreBottomNavBar
import com.dhruva.auratech.ui.profile.components.AccountMenuCard
import com.dhruva.auratech.ui.profile.components.LogoutButton
import com.dhruva.auratech.ui.profile.components.OrdersStatusCard
import com.dhruva.auratech.ui.profile.components.ProfileHeaderCard
import com.dhruva.auratech.ui.profile.components.ProfileTopBar
import com.dhruva.auratech.ui.profile.components.WalletLoyaltyWidget
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

                ProfileHeaderCard(
                    user = uiState.userProfile,
                    onEditClick = { onAction(ProfileAction.OnEditProfileClick) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                WalletLoyaltyWidget(
                    walletBalance = uiState.userProfile.walletBalance,
                    rewardPoints = uiState.userProfile.rewardPoints,
                    couponsCount = uiState.userProfile.activeCouponsCount,
                    onTopUpClick = { onAction(ProfileAction.OnTopUpWallet) },
                    onRedeemClick = { onAction(ProfileAction.OnRedeemRewards) },
                    onViewCouponsClick = { onAction(ProfileAction.OnViewCoupons) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OrdersStatusCard(
                    orderStatus = uiState.orderStatus,
                    activeDelivery = uiState.activeDelivery,
                    onStatusClick = { status -> onAction(ProfileAction.OnOrderStatusClick(status)) },
                    onViewAllClick = { onAction(ProfileAction.OnViewAllOrdersClick) },
                    onTrackDeliveryClick = { onAction(ProfileAction.OnTrackDeliveryClick) }
                )

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

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val sampleUser = remember {
        UserProfile(
            name = "Alex Mercer",
            email = "alex.mercer@auratech.io",
            phone = "+1 (555) 234-8901",
            avatarRes = R.drawable.user_avatar,
            isVerified = true,
            memberTier = "DIAMOND TIER MEMBER",
            walletBalance = 420.00,
            rewardPoints = 2850,
            activeCouponsCount = 4
        )
    }

    val sampleOrderStatus = remember {
        OrderStatusCount(
            processingCount = 1,
            deliveredCount = 28,
            cancelledCount = 2,
            returnedCount = 0
        )
    }

    val sampleActiveDelivery = remember {
        ActiveDelivery(
            orderId = "AU-88392",
            statusText = "Arriving Tomorrow",
            subtext = "In transit from distribution hub"
        )
    }

    val sampleMenuItems = remember {
        listOf(
            ProfileMenuItem(
                id = "orders",
                title = "Order History",
                subtitle = "Packages, invoices & tracking",
                iconVector = Icons.Outlined.FavoriteBorder
            ),
            ProfileMenuItem(
                id = "wishlist",
                title = "Wishlist & Saved Items",
                subtitle = "14 Items",
                iconVector = Icons.Outlined.FavoriteBorder
            ),
            ProfileMenuItem(
                id = "addresses",
                title = "Shipping Addresses",
                subtitle = "2 saved",
                iconVector = Icons.Outlined.LocationOn
            ),
            ProfileMenuItem(
                id = "payment",
                title = "Payment Methods",
                subtitle = "Visa ending in 4242",
                iconVector = Icons.Outlined.CreditCard
            ),
            ProfileMenuItem(
                id = "support",
                title = "Help & Support Center",
                subtitle = "FAQ, chat & concierge support",
                iconVector = Icons.Outlined.HelpOutline
            )
        )
    }

    var toastMessage by remember { mutableStateOf<String?>(null) }

    val uiState = ProfileUiState(
        userProfile = sampleUser,
        orderStatus = sampleOrderStatus,
        activeDelivery = sampleActiveDelivery,
        menuItems = sampleMenuItems,
        toastMessage = toastMessage
    )

    ProfileScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is ProfileAction.OnTopUpWallet -> toastMessage = "Wallet top up requested"
                is ProfileAction.OnRedeemRewards -> toastMessage = "Rewards catalog opened"
                is ProfileAction.OnViewCoupons -> toastMessage = "Coupons screen opened"
                is ProfileAction.OnLogoutClick -> toastMessage = "Logged out successfully"
                else -> {}
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
