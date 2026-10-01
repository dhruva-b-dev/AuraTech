package com.dhruva.auratech.ui.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.R
import com.dhruva.auratech.model.ActiveDelivery
import com.dhruva.auratech.model.OrderStatusCount
import com.dhruva.auratech.model.ProfileMenuItem
import com.dhruva.auratech.model.UserProfile
import com.dhruva.auratech.ui.theme.CardBackground
import com.dhruva.auratech.ui.theme.CodeBg
import com.dhruva.auratech.ui.theme.CyanAccent
import com.dhruva.auratech.ui.theme.DarkBackground
import com.dhruva.auratech.ui.theme.PurpleAccent
import com.dhruva.auratech.ui.theme.TextGray

// =============================================================================
// 1. PROFILE TOP BAR
// =============================================================================

@Composable
fun ProfileTopBar(
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkBackground)
            .statusBarsPadding()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "My Profile",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                onClick = onNotificationsClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = TextGray,
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = TextGray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


// =============================================================================
// 2. PROFILE HEADER CARD
// =============================================================================

@Composable
fun ProfileHeaderCard(
    user: UserProfile,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Avatar Box with Verification Badge
                    Box {
                        Image(
                            painter = painterResource(
                                id = if (user.avatarRes != 0) user.avatarRes else R.drawable.user_avatar
                            ),
                            contentDescription = user.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .border(2.dp, PurpleAccent, CircleShape)
                        )

                        if (user.isVerified) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(20.dp)
                                    .background(CyanAccent, CircleShape)
                                    .border(2.dp, CardBackground, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Verified",
                                    tint = Color(0xFF0A0B10),
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }

                    // Name & Member Tier Tag
                    Column {
                        Text(
                            text = user.name,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .background(Color(0xFF232538), RoundedCornerShape(20.dp))
                                .border(1.dp, PurpleAccent.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "★ ${user.memberTier} ★",
                                color = PurpleAccent,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                // Edit Profile Icon Button
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(CodeBg, CircleShape)
                        .clickable { onEditClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit Profile",
                        tint = TextGray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone & Email Details
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = "Phone",
                        tint = TextGray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = user.phone,
                        color = TextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email",
                        tint = TextGray,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = user.email,
                        color = TextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}


// =============================================================================
// 3. WALLET & LOYALTY WIDGET
// =============================================================================

@Composable
fun WalletLoyaltyWidget(
    walletBalance: Double,
    rewardPoints: Int,
    couponsCount: Int,
    onTopUpClick: () -> Unit,
    onRedeemClick: () -> Unit,
    onViewCouponsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Wallet Card
        Card(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("WALLET", color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Icon(
                        imageVector = Icons.Outlined.AccountBalanceWallet,
                        contentDescription = null,
                        tint = PurpleAccent,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "$${String.format("%.2f", walletBalance)}",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onTopUpClick,
                    colors = ButtonDefaults.buttonColors(containerColor = CodeBg, contentColor = PurpleAccent),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Text("+ Top Up", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Rewards Card
        Card(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("REWARDS", color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Icon(
                        imageVector = Icons.Outlined.Stars,
                        contentDescription = null,
                        tint = CyanAccent,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "%,d".format(rewardPoints),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("Pts", color = TextGray, fontSize = 10.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onRedeemClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF132A2B), contentColor = CyanAccent),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Text("Redeem", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Coupons Card
        Card(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("COUPONS", color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Icon(
                        imageVector = Icons.Outlined.ConfirmationNumber,
                        contentDescription = null,
                        tint = PurpleAccent,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$couponsCount",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("Active", color = TextGray, fontSize = 10.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onViewCouponsClick,
                    colors = ButtonDefaults.buttonColors(containerColor = CodeBg, contentColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(vertical = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                ) {
                    Text("View All", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


// =============================================================================
// 4. ORDERS STATUS CARD
// =============================================================================

@Composable
fun OrdersStatusCard(
    orderStatus: OrderStatusCount,
    activeDelivery: ActiveDelivery,
    onStatusClick: (String) -> Unit,
    onViewAllClick: () -> Unit,
    onTrackDeliveryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Orders",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.clickable { onViewAllClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text("View All", color = PurpleAccent, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = PurpleAccent,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Status Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .background(PurpleAccent, RoundedCornerShape(20.dp))
                        .clickable { onStatusClick("Processing") }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("Processing", color = Color(0xFF0A0B10), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .background(Color(0xFF0A0B10), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${orderStatus.processingCount}", color = PurpleAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .background(CodeBg, RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp))
                        .clickable { onStatusClick("Delivered") }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Delivered ${orderStatus.deliveredCount}", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .background(CodeBg, RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp))
                        .clickable { onStatusClick("Cancelled") }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Cancelled ${orderStatus.cancelledCount}", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Active Delivery Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp))
                .clickable { onTrackDeliveryClick() },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(CodeBg, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocalShipping,
                                contentDescription = null,
                                tint = PurpleAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "ORDER #${activeDelivery.orderId}",
                                    color = CyanAccent,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(CyanAccent, CircleShape)
                                )
                            }
                            Text(
                                text = activeDelivery.statusText,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = activeDelivery.subtext,
                                color = TextGray,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Track",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress Indicator Line
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(4) { index ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .background(
                                    color = if (index < 3) CyanAccent else Color(0xFF282B3C),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}


// =============================================================================
// 5. ACCOUNT MENU CARD
// =============================================================================

@Composable
fun AccountMenuCard(
    menuItems: List<ProfileMenuItem>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Account Settings",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                menuItems.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onItemClick(item.id) }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(CodeBg, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = item.iconVector,
                                    contentDescription = item.title,
                                    tint = PurpleAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = item.title,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = item.subtitle,
                                    color = TextGray,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = TextGray,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    if (index < menuItems.lastIndex) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                                .height(1.dp)
                                .background(Color(0xFF232636))
                        )
                    }
                }
            }
        }
    }
}


// =============================================================================
// 6. LOGOUT BUTTON
// =============================================================================

@Composable
fun LogoutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = CardBackground,
            contentColor = Color(0xFFFF5252)
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B2025)),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Logout,
                contentDescription = "Log Out",
                tint = Color(0xFFFF5252),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Log Out",
                color = Color(0xFFFF5252),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// =============================================================================
// PREVIEWS
// =============================================================================

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileHeaderCardPreview() {
    ProfileHeaderCard(
        user = UserProfile(
            name = "Alex Mercer",
            email = "alex.mercer@auratech.io",
            phone = "+1 (555) 234-8901",
            avatarRes = R.drawable.user_avatar,
            isVerified = true,
            memberTier = "DIAMOND TIER MEMBER",
            walletBalance = 420.0,
            rewardPoints = 2850,
            activeCouponsCount = 4
        ),
        onEditClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
