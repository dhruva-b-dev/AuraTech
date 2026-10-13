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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.ui.theme.*

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

            if (uiState.showEditProfileDialog) {
                EditProfileDialog(uiState = uiState, onAction = onAction)
            }

            if (uiState.showCouponsDialog) {
                CouponsDialog(onDismiss = { onAction(ProfileAction.OnDismissCoupons) })
            }

            if (uiState.showSupportDialog) {
                SupportDialog(onDismiss = { onAction(ProfileAction.OnDismissSupport) })
            }

            if (uiState.showSettingsDialog) {
                SettingsDialog(onDismiss = { onAction(ProfileAction.OnDismissSettings) })
            }

            if (uiState.showMessagesDialog) {
                MessagesDialog(onDismiss = { onAction(ProfileAction.OnDismissMessages) })
            }
        }
    }
}

@Composable
fun EditProfileDialog(uiState: ProfileUiState, onAction: (ProfileAction) -> Unit) {
    AlertDialog(
        onDismissRequest = { onAction(ProfileAction.OnDismissEditProfile) },
        containerColor = CardBackground,
        title = { Text("Edit Profile", color = Color.White) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = uiState.editNameInput,
                    onValueChange = { onAction(ProfileAction.OnEditNameChanged(it)) },
                    label = { Text("Full Name") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
                OutlinedTextField(
                    value = uiState.editPhoneInput,
                    onValueChange = { onAction(ProfileAction.OnEditPhoneChanged(it)) },
                    label = { Text("Phone Number") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onAction(ProfileAction.OnSaveProfile) },
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text("Save Changes", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = { onAction(ProfileAction.OnDismissEditProfile) }) {
                Text("Cancel", color = TextGray)
            }
        }
    )
}

@Composable
fun CouponsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardBackground,
        title = { Text("Available Coupons", color = Color.White, fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CouponItem("FALL1403", "$10.00 OFF on orders above $50")
                CouponItem("NOROOZ", "$10.00 promo discount")
                CouponItem("WELCOME10", "10% OFF your first order")
                CouponItem("FREESHIP", "Free Express Delivery")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                Text("Close", color = Color.Black)
            }
        }
    )
}

@Composable
fun CouponItem(code: String, desc: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = CodeBg,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = code, color = CyanAccent, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = desc, color = TextGray, fontSize = 12.sp)
        }
    }
}

@Composable
fun SupportDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardBackground,
        title = { Text("Customer Support 24/7", color = Color.White, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Need assistance with your orders or account?", color = TextGray, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = RoundedCornerShape(8.dp), color = CodeBg, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Email Support", color = CyanAccent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("support@auratech.com", color = Color.White, fontSize = 13.sp)
                    }
                }
                Surface(shape = RoundedCornerShape(8.dp), color = CodeBg, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Toll-Free Phone", color = CyanAccent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("+1 (800) 555-AURA", color = Color.White, fontSize = 13.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                Text("Close", color = Color.Black)
            }
        }
    )
}

@Composable
fun SettingsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardBackground,
        title = { Text("Account & Privacy", color = Color.White, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Push Notifications", color = Color.White)
                    Switch(checked = true, onCheckedChange = {}, colors = SwitchDefaults.colors(checkedThumbColor = Color.Black, checkedTrackColor = Primary))
                }
                HorizontalDivider(color = Color(0xFF282B3C))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Biometric Authentication", color = Color.White)
                    Switch(checked = false, onCheckedChange = {}, colors = SwitchDefaults.colors(checkedThumbColor = Color.Black, checkedTrackColor = Primary))
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                Text("Done", color = Color.Black)
            }
        }
    )
}

@Composable
fun MessagesDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardBackground,
        title = { Text("Messages & Alerts", color = Color.White, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(shape = RoundedCornerShape(8.dp), color = CodeBg, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Welcome to Aura Tech!", color = CyanAccent, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Use code FALL1403 for $10 off your first order.", color = TextGray, fontSize = 12.sp)
                    }
                }
                Surface(shape = RoundedCornerShape(8.dp), color = CodeBg, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Order Shipment Alert", color = Primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Order #449102 is out for delivery.", color = TextGray, fontSize = 12.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Primary)) {
                Text("Close", color = Color.Black)
            }
        }
    )
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