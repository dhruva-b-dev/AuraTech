package com.dhruva.auratech.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.R
import com.dhruva.auratech.model.BottomNavTab
import com.dhruva.auratech.model.Category
import com.dhruva.auratech.model.EditorialBanner
import com.dhruva.auratech.model.Product
import com.dhruva.auratech.model.PromoBanner
import com.dhruva.auratech.model.TrustBadge
import com.dhruva.auratech.ui.theme.CardBackground
import com.dhruva.auratech.ui.theme.CodeBg
import com.dhruva.auratech.ui.theme.CyanAccent
import com.dhruva.auratech.ui.theme.DarkBackground
import com.dhruva.auratech.ui.theme.Primary
import com.dhruva.auratech.ui.theme.PurpleAccent
import com.dhruva.auratech.ui.theme.TextGray
import com.dhruva.auratech.ui.theme.TimerBoxBg


// =============================================================================
// 1. HOME TOP BAR
// =============================================================================
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeTopBar(
    onProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onBrandBagClick: () -> Unit = {},
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
        // Left: Avatar thumbnail + Brand/Home Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_avatar),
                contentDescription = "User Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFF282C3F), CircleShape)
                    .clickable { onProfileClick() }
            )

            Text(
                text = "AURA TECH",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        // Right: Notification Bell & Brand Shopping Bag
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
                onClick = onBrandBagClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = "Brand Bag",
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

// Search and Filter
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchAndFilter(
    query: String = "",
    onQueryChanged: (String) -> Unit = {},
    onScanBarcodeClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = modifier.fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.White
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CardBackground,
            unfocusedContainerColor = CardBackground,
            disabledContainerColor = CardBackground,
            focusedBorderColor = Color(0xFF2D3142),
            unfocusedBorderColor = Color(0xFF232636),
            focusedLeadingIconColor = Primary,
            unfocusedLeadingIconColor = Primary,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = TextGray,
            unfocusedPlaceholderColor = TextGray
        ),
        singleLine = true,
        placeholder = {
            Text(
                text = "Search among thousands of products...",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextGray
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        shape = RoundedCornerShape(percent = 50),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = Primary,
                modifier = Modifier.size(22.dp)
            )
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 4.dp)
            ) {
                IconButton(onClick = onScanBarcodeClick) {
                    Icon(
                        imageVector = Icons.Outlined.QrCodeScanner,
                        contentDescription = "Scan Barcode",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                FilledIconButton(
                    onClick = onFilterClick,
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = CodeBg,
                        contentColor = Primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Tune,
                        contentDescription = "Filter",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    )
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 150,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TrustBadgesBlocks(
    badges: List<TrustBadge> = listOf(
        TrustBadge(
            id = "1",
            label = "Fast express",
            iconVector = Icons.Filled.ElectricBolt,
            tint = Color(0xFF4DEEEA)
        ),
        TrustBadge(
            id = "2",
            label = "100% Genuine",
            iconVector = Icons.Filled.Star,
            tint = Primary
        ),
        TrustBadge(
            id = "3",
            label = "7-day return",
            iconVector = Icons.Filled.Analytics,
            tint = Primary
        )
    ),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        badges.forEach { badge ->
            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = badge.iconVector,
                        contentDescription = badge.label,
                        tint = badge.tint,
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = badge.label.uppercase(Locale.current.platformLocale),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PromoBanner(
    banner: PromoBanner = PromoBanner(
        id = "1",
        tag = "CYBER SEASON",
        title = "30% OFF ACOUSTICS",
        promoCode = "AURA30",
        remainingSeconds = 10
    ),
    hours: String = "07",
    minutes: String = "54",
    seconds: String = "46",
    onShopNowClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkBackground)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBackground, RoundedCornerShape(24.dp))
                .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .border(1.dp, CyanAccent.copy(alpha = 0.5F), RoundedCornerShape(50.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = banner.tag,
                        color = CyanAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .background(CodeBg, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CODE: ",
                        color = TextGray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = banner.promoCode,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = banner.title,
                color = PurpleAccent,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Precision ANC drivers and lossless portable\namplification systems.",
                color = TextGray,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TimerUnit(value = hours, label = "HRS", valueColor = PurpleAccent)
                    TimerColon()
                    TimerUnit(value = minutes, label = "MIN", valueColor = PurpleAccent)
                    TimerColon()
                    TimerUnit(value = seconds, label = "SEC", valueColor = CyanAccent)
                }

                Button(
                    onClick = onShopNowClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PurpleAccent,
                        contentColor = Color(0xFF0A0B10)
                    ),
                    shape = RoundedCornerShape(40.dp),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Explore",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Now",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Explore arrow",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimerUnit(value: String, label: String, valueColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = 48.dp, height = 48.dp)
                .background(TimerBoxBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                color = valueColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            color = TextGray,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TimerColon() {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(48.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(PurpleAccent, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(PurpleAccent, RoundedCornerShape(2.dp))
        )
    }
}

// Featured categories
@Composable
fun FeaturedCategoriesSection(
    categories: List<Category>,
    selectedCategoryId: String?,
    onCategoryClick: (String) -> Unit,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSectorId by remember { mutableStateOf(selectedCategoryId ?: categories.firstOrNull()?.id ?: "") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hardware Sectors",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${categories.size} TIERS",
                color = Primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(categories) { sector ->
                SectorCircleButton(
                    item = sector,
                    isSelected = sector.id == selectedSectorId,
                    onClick = {
                        selectedSectorId = sector.id
                        onCategoryClick(sector.id)
                    }
                )
            }
        }
    }
}

@Composable
fun SectorCircleButton(
    item: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    val circleBackgroundColor = Color(0xFF131622)
    val strokeColor = if (isSelected) Color(0xFF1E2336) else Color(0xFF161A29)
    val textColor = if (isSelected) Color(0xFFFFFFFF) else Color(0xFF8B95B3)
    val badgeColor = Color(0xFF4DEEEA)

    Column(
        modifier = modifier.width(76.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .padding(4.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, strokeColor, CircleShape)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onClick() },
                shape = CircleShape,
                color = circleBackgroundColor
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = item.iconVector,
                        contentDescription = item.name,
                        tint = item.iconColor,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            if (isSelected) {
                Surface(
                    modifier = Modifier
                        .size(10.dp)
                        .align(Alignment.TopEnd),
                    shape = CircleShape,
                    color = badgeColor
                ) {}
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.name,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            maxLines = 1
        )
    }
}


// =============================================================================
// FLASH DEALS SECTION & CARD
// =============================================================================

@Composable
fun FlashDealsSection(
    deals: List<Product>,
    onProductClick: (String) -> Unit,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Flash Allocations",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF3B1E22),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFFFF3B30).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Color(0xFFFF3B30), CircleShape)
                        )
                        Text(
                            text = "LIVE",
                            color = Color(0xFFFF3B30),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.clickable { onViewAllClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "View All",
                    color = Primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "View All",
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(deals) { product ->
                FlashDealCard(
                    product = product,
                    onClick = { onProductClick(product.id) },
                    modifier = Modifier.width(180.dp)
                )
            }
        }
    }
}

@Composable
fun FlashDealCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0F121C))
            ) {
                Image(
                    painter = painterResource(
                        id = if (product.imageRes != 0) product.imageRes else R.drawable.user_avatar
                    ),
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (product.discountPercent > 0 || product.badgeText != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(
                                color = Color(0xFF132A2B),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = CyanAccent.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = product.badgeText ?: "-${product.discountPercent}%",
                            color = CyanAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(30.dp)
                        .background(Color(0x66000000), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (product.isFavorite) Color.Red else Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = CyanAccent,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "${product.rating}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "(${product.reviewCount})",
                    color = TextGray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.stockRemainingText ?: "Planar Magnetic • 40H",
                color = TextGray,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "$${product.currentPrice.toInt()}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (product.originalPrice > product.currentPrice) {
                        Text(
                            text = "$${product.originalPrice.toInt()}",
                            color = TextGray,
                            fontSize = 11.sp,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(PurpleAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to cart",
                        tint = Color(0xFF0A0B10),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}


// =============================================================================
// EDITORIAL BANNERS SECTION & CARD
// =============================================================================

@Composable
fun EditorialBannersSection(
    banners: List<EditorialBanner>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        banners.forEach { banner ->
            EditorialBannerCard(banner = banner)
        }
    }
}

@Composable
fun EditorialBannerCard(
    banner: EditorialBanner,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(CodeBg, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Analytics,
                        contentDescription = banner.title,
                        tint = PurpleAccent,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Text(
                        text = banner.title,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = banner.subtitle,
                        color = TextGray,
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .border(1.dp, CyanAccent.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = banner.tag.ifEmpty { "AUDIT" },
                    color = CyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}


// =============================================================================
// BEST SELLERS SECTION & GRID PRODUCT CARD
// =============================================================================

@Composable
fun BestSellersSection(
    products: List<Product>,
    onToggleFavorite: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    onProductClick: (String) -> Unit,
    onFilterSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Best Sellers",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = onFilterSortClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filter or Sort",
                    tint = TextGray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val rows = products.chunked(2)
        rows.forEach { rowProducts ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                rowProducts.forEach { product ->
                    GridProductCard(
                        product = product,
                        onToggleFavorite = { onToggleFavorite(product.id) },
                        onAddToCart = { onAddToCart(product) },
                        onClick = { onProductClick(product.id) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun GridProductCard(
    product: Product,
    onToggleFavorite: () -> Unit,
    onAddToCart: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0F121C))
            ) {
                Image(
                    painter = painterResource(
                        id = if (product.imageRes != 0) product.imageRes else R.drawable.user_avatar
                    ),
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (product.discountPercent > 0 || product.badgeText != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(Color(0xFF132A2B), RoundedCornerShape(8.dp))
                            .border(1.dp, CyanAccent.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = product.badgeText ?: "-${product.discountPercent}%",
                            color = CyanAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(30.dp)
                        .background(Color(0x66000000), CircleShape)
                        .clickable { onToggleFavorite() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (product.isFavorite) Color.Red else Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = CyanAccent,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "${product.rating}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "(${product.reviewCount})",
                    color = TextGray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.stockRemainingText ?: "Standard Edition",
                color = TextGray,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "$${product.currentPrice.toInt()}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (product.originalPrice > product.currentPrice) {
                        Text(
                            text = "$${product.originalPrice.toInt()}",
                            color = TextGray,
                            fontSize = 11.sp,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(PurpleAccent, CircleShape)
                        .clickable { onAddToCart() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to Cart",
                        tint = Color(0xFF0A0B10),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}


// =============================================================================
// STORE BOTTOM NAV BAR
// =============================================================================

@Composable
fun StoreBottomNavBar(
    selectedTab: BottomNavTab,
    cartItemCount: Int,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xFF0B0F19),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavTab.entries.forEach { tab ->
                val isSelected = tab == selectedTab
                val icon = when (tab) {
                    BottomNavTab.HOME -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
                    BottomNavTab.CATEGORIES -> if (isSelected) Icons.Filled.GridView else Icons.Outlined.GridView
                    BottomNavTab.CART -> if (isSelected) Icons.Filled.ShoppingBag else Icons.Outlined.ShoppingBag
                    BottomNavTab.PROFILE -> if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Box {
                        Icon(
                            imageVector = icon,
                            contentDescription = tab.title,
                            tint = if (isSelected) PurpleAccent else TextGray,
                            modifier = Modifier.size(24.dp)
                        )

                        val displayBadge = if (tab == BottomNavTab.CART) {
                            if (cartItemCount > 0) cartItemCount else tab.badgeCount
                        } else tab.badgeCount

                        if (displayBadge > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = 6.dp, y = (-4).dp)
                                    .size(16.dp)
                                    .background(CyanAccent, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$displayBadge",
                                    color = Color(0xFF0A0B10),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = tab.title,
                        color = if (isSelected) Color.White else TextGray,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )

                    if (isSelected) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(PurpleAccent, CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StoreBottomNavBarPreview() {
    StoreBottomNavBar(
        selectedTab = BottomNavTab.HOME,
        cartItemCount = 2,
        onTabSelected = {}
    )
}

// =============================================================================
// CART FEEDBACK TOAST
// =============================================================================

@Composable
fun CartFeedbackToast(
    message: String?,
    modifier: Modifier = Modifier
) {
    if (message != null) {
        Surface(
            modifier = modifier.padding(16.dp),
            shape = RoundedCornerShape(50.dp),
            color = Color(0xFF1E2538),
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = CyanAccent,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
fun CartFeedbackToastPreview() {
    CartFeedbackToast(message = "Item added to cart")
}

// =============================================================================
// PREVIEWS
// =============================================================================

@Preview(showBackground = true, backgroundColor = 0xFF0B0E14)
@Composable
fun HardwareSectorsPreview() {
    val sampleCategories = listOf(
        Category("1", "Audio", Icons.Default.Headset, Color(0xFFECEFF4), Color(0xFF131622)),
        Category("2", "Wearables", Icons.Default.Watch, Color(0xFF535D7E), Color(0xFF131622)),
        Category("3", "Laptops", Icons.Default.Laptop, Color(0xFF535D7E), Color(0xFF131622)),
        Category("4", "Gaming", Icons.Default.Gamepad, Color(0xFF535D7E), Color(0xFF131622)),
        Category("5", "Smart Home", Icons.Default.DeveloperBoard, Color(0xFF535D7E), Color(0xFF131622))
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF0B0E14)
    ) {
        FeaturedCategoriesSection(
            categories = sampleCategories,
            selectedCategoryId = "1",
            onCategoryClick = {},
            onViewAllClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 1100, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenFullPreview() {
    val sampleCategories = listOf(
        Category("1", "Audio", Icons.Default.Headset, Color(0xFFECEFF4), Color(0xFF131622)),
        Category("2", "Wearables", Icons.Default.Watch, Color(0xFF535D7E), Color(0xFF131622)),
        Category("3", "Laptops", Icons.Default.Laptop, Color(0xFF535D7E), Color(0xFF131622)),
        Category("4", "Gaming", Icons.Default.Gamepad, Color(0xFF535D7E), Color(0xFF131622)),
        Category("5", "Smart Home", Icons.Default.DeveloperBoard, Color(0xFF535D7E), Color(0xFF131622))
    )

    val sampleDeals = listOf(
        Product(
            id = "1",
            title = "AeroPro Wireless ANC",
            originalPrice = 385.0,
            currentPrice = 249.0,
            discountPercent = 35,
            rating = 4.9f,
            reviewCount = 128,
            imageRes = R.drawable.user_avatar,
            stockRemainingText = "Planar Magnetic • 40H",
            isFavorite = false
        ),
        Product(
            id = "2",
            title = "Chronos Bio Pulse",
            originalPrice = 399.0,
            currentPrice = 319.0,
            discountPercent = 20,
            rating = 4.8f,
            reviewCount = 94,
            imageRes = R.drawable.user_avatar,
            stockRemainingText = "Sapphire Glass • ECG",
            isFavorite = false
        )
    )

    val sampleBanners = listOf(
        EditorialBanner(
            id = "1",
            tag = "AUDIT",
            title = "Spatial Engine v4.2",
            subtitle = "Firmware calibration live for all hardware.",
            imageRes = R.drawable.user_avatar
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0B0F19)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HomeTopBar()
            SearchAndFilter(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            TrustBadgesBlocks()
            PromoBanner(hours = "07", minutes = "54", seconds = "46")
            FeaturedCategoriesSection(
                categories = sampleCategories,
                selectedCategoryId = "1",
                onCategoryClick = {},
                onViewAllClick = {}
            )
            FlashDealsSection(
                deals = sampleDeals,
                onProductClick = {},
                onViewAllClick = {}
            )
            EditorialBannersSection(banners = sampleBanners)
            BestSellersSection(
                products = sampleDeals,
                onToggleFavorite = {},
                onAddToCart = {},
                onProductClick = {},
                onFilterSortClick = {}
            )
        }
    }
}
