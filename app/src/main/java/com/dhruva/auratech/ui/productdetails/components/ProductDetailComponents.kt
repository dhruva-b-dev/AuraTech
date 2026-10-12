package com.dhruva.auratech.ui.productdetails.components

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.R
import com.dhruva.auratech.model.ColorVariant
import com.dhruva.auratech.model.ProductDetail
import com.dhruva.auratech.model.ProductHighlight
import com.dhruva.auratech.model.ReviewSummary
import com.dhruva.auratech.model.UserReview
import com.dhruva.auratech.ui.theme.CardBackground
import com.dhruva.auratech.ui.theme.CodeBg
import com.dhruva.auratech.ui.theme.CyanAccent
import com.dhruva.auratech.ui.theme.DarkBackground
import com.dhruva.auratech.ui.theme.PurpleAccent
import com.dhruva.auratech.ui.theme.TextGray

// =============================================================================
// 1. PRODUCT DETAIL TOP BAR
// =============================================================================

@Composable
fun ProductDetailTopBar(
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onCartClick: () -> Unit = {},
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Text(
                text = "Product Details",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                onClick = onShareClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Share",
                    tint = TextGray,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else TextGray,
                    modifier = Modifier.size(20.dp)
                )
            }

            Box {
                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = "Bag",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = 2.dp)
                        .size(16.dp)
                        .background(CyanAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "2",
                        color = Color(0xFF0A0B10),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


// =============================================================================
// 2. PRODUCT GALLERY SECTION
// =============================================================================

@Composable
fun ProductGallerySection(
    galleryImages: List<Int>,
    selectedIndex: Int,
    isFavorite: Boolean,
    onSelectImage: (Int) -> Unit,
    onShareClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Tag Row Above Image
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // SPECIAL DEAL Badge
            Box(
                modifier = Modifier
                    .background(Color(0xFF231C28), RoundedCornerShape(50.dp))
                    .border(1.dp, Color(0xFF8A3D54), RoundedCornerShape(50.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalFireDepartment,
                        contentDescription = null,
                        tint = PurpleAccent,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "SPECIAL DEAL",
                        color = PurpleAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // VERIFIED AUTHENTIC Badge
            Box(
                modifier = Modifier
                    .background(Color(0xFF132A2B), RoundedCornerShape(50.dp))
                    .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(50.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = CyanAccent,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "VERIFIED AUTHENTIC",
                        color = CyanAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        // Main Showcase Image Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.3f)
                .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                val currentImg = galleryImages.getOrNull(selectedIndex) ?: galleryImages.firstOrNull() ?: R.drawable.user_avatar
                Image(
                    painter = painterResource(id = currentImg),
                    contentDescription = "Product image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Page Indicator Dots at Bottom Center
                if (galleryImages.size > 1) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        galleryImages.indices.forEach { index ->
                            Box(
                                modifier = Modifier
                                    .size(
                                        width = if (index == selectedIndex) 20.dp else 6.dp,
                                        height = 6.dp
                                    )
                                    .background(
                                        color = if (index == selectedIndex) PurpleAccent else Color(0x66FFFFFF),
                                        shape = RoundedCornerShape(3.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Thumbnails Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(galleryImages) { index, imgRes ->
                val isSelected = index == selectedIndex
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardBackground)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) PurpleAccent else Color(0xFF282B3C),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelectImage(index) }
                ) {
                    Image(
                        painter = painterResource(id = imgRes),
                        contentDescription = "Thumbnail $index",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}


// =============================================================================
// 3. PRODUCT METADATA SECTION
// =============================================================================

@Composable
fun ProductMetadataSection(
    detail: ProductDetail,
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
                text = detail.brand.uppercase(),
                color = CyanAccent,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Text(
                text = "SKU: ${detail.sku}",
                color = TextGray,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = detail.title,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Rating & Recommendation Badges Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(CodeBg, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
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
                    text = "${detail.rating}",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "(${detail.reviewCount} reviews)",
                    color = TextGray,
                    fontSize = 11.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Recommended",
                    tint = CyanAccent,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "${detail.satisfactionPercent}% recommended",
                    color = CyanAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


// =============================================================================
// 4. COLOR VARIANT PICKER
// =============================================================================

@Composable
fun ColorVariantPicker(
    colors: List<ColorVariant>,
    selectedIndex: Int,
    onSelectColor: (Int) -> Unit,
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
                text = "Selected Color",
                color = TextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = colors.getOrNull(selectedIndex)?.name ?: "",
                color = PurpleAccent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            colors.forEachIndexed { index, colorVariant ->
                val isSelected = index == selectedIndex
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) CardBackground else CodeBg)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) PurpleAccent else Color(0xFF282B3C),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelectColor(index) }
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(colorVariant.color, CircleShape)
                            .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = colorVariant.name,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


// =============================================================================
// 5. KEY HIGHLIGHTS SECTION
// =============================================================================

@Composable
fun KeyHighlightsSection(
    highlights: List<ProductHighlight>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        highlights.forEach { highlight ->
            Card(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = highlight.iconVector,
                        contentDescription = highlight.title,
                        tint = CyanAccent,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = highlight.title,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = highlight.subtitle,
                        color = TextGray,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


// =============================================================================
// 6. WARRANTY AND CARE SECTION
// =============================================================================

@Composable
fun WarrantyAndCareSection(
    selectedOption: Int,
    onSelectOption: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "ACOUSTIC ARCHITECTURE",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Driver Unit", color = TextGray, fontSize = 12.sp)
                Text("40mm Custom Bio-Cellulose", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Frequency Curve", color = TextGray, fontSize = 12.sp)
                Text("4 Hz – 45,000 Hz", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}


// =============================================================================
// 7. DELIVERY PERKS SECTION
// =============================================================================

@Composable
fun DeliveryPerksSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocalShipping,
                    contentDescription = null,
                    tint = CyanAccent,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Free Express Shipping On All Orders",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Security,
                    contentDescription = null,
                    tint = PurpleAccent,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "2-Year Official Manufacturer Warranty",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


// =============================================================================
// 8. CUSTOMER REVIEWS SECTION
// =============================================================================

@Composable
fun CustomerReviewsSection(
    summary: ReviewSummary,
    featuredReview: UserReview,
    upvotes: Int,
    downvotes: Int,
    onVoteReview: (Boolean) -> Unit,
    onWriteReviewClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "CUSTOMER REVIEWS",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${summary.averageRating}",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Column {
                    Row {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = CyanAccent,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                    Text(
                        text = "Based on ${summary.totalReviews} reviews",
                        color = TextGray,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Featured Review Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CodeBg, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = featuredReview.reviewerName,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = featuredReview.dateText,
                        color = TextGray,
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = featuredReview.comment,
                    color = TextGray,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Verified Buyer",
                        color = CyanAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.clickable { onVoteReview(true) },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ThumbUp,
                                contentDescription = "Upvote",
                                tint = TextGray,
                                modifier = Modifier.size(14.dp)
                            )
                            Text("$upvotes", color = TextGray, fontSize = 11.sp)
                        }

                        Row(
                            modifier = Modifier.clickable { onVoteReview(false) },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ThumbDown,
                                contentDescription = "Downvote",
                                tint = TextGray,
                                modifier = Modifier.size(14.dp)
                            )
                            Text("$downvotes", color = TextGray, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingBarRow(
    starLabel: String,
    percent: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(starLabel, color = TextGray, fontSize = 11.sp, modifier = Modifier.width(28.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .background(CodeBg, CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percent / 100f)
                    .background(CyanAccent, CircleShape)
            )
        }
        Text("$percent%", color = TextGray, fontSize = 11.sp, modifier = Modifier.width(32.dp))
    }
}


// =============================================================================
// 9. STICKY ADD TO CART BAR
// =============================================================================

@Composable
fun StickyAddToCartBar(
    originalPrice: Double,
    currentPrice: Double,
    discountPercent: Int,
    isAddedToCart: Boolean,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = DarkBackground,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF8A2E35), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "$discountPercent% OFF",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "$${originalPrice.toInt()}",
                        color = TextGray,
                        fontSize = 12.sp,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "$${String.format("%.2f", currentPrice)}",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onAddToCartClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleAccent,
                    contentColor = Color(0xFF0A0B10)
                ),
                shape = RoundedCornerShape(40.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = "Add to cart",
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = if (isAddedToCart) "Added to Cart" else "Add to Cart",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


// =============================================================================
// PREVIEWS
// =============================================================================

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductDetailTopBarPreview() {
    ProductDetailTopBar(
        isFavorite = false,
        onBackClick = {},
        onShareClick = {},
        onToggleFavorite = {}
    )
}
