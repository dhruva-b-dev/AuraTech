package com.dhruva.auratech.ui.categories.components

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.dhruva.auratech.model.CategoryCollection
import com.dhruva.auratech.model.Product
import com.dhruva.auratech.model.SubCategory
import com.dhruva.auratech.ui.theme.CardBackground
import com.dhruva.auratech.ui.theme.CodeBg
import com.dhruva.auratech.ui.theme.CyanAccent
import com.dhruva.auratech.ui.theme.DarkBackground
import com.dhruva.auratech.ui.theme.PurpleAccent
import com.dhruva.auratech.ui.theme.TextGray


// =============================================================================
// 1. CATEGORIES TOP BAR
// =============================================================================

@Composable
fun CategoriesTopBar(
    onNotificationsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkBackground)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "AURA TECH",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                /*Icon(
                    imageVector = Icons.Outlined.QrCodeScanner,
                    contentDescription = "Scan Barcode",
                    tint = TextGray,
                    modifier = Modifier.size(20.dp)
                )*/

                IconButton(
                    onClick = onNotificationsClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = CyanAccent,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.user_avatar),
                    contentDescription = "User Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0xFF282C3F), CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sub-header Label & Title
        Text(
            text = "HARDWARE CATALOG",
            color = CyanAccent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Categories",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


// =============================================================================
// 2. CATEGORIES SEARCH BAR
// =============================================================================

@Composable
fun CategoriesSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.White
        ),
        singleLine = true,
        placeholder = {
            Text(
                text = "Search audio, gadgets, accessories...",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextGray
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        shape = RoundedCornerShape(percent = 50),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CardBackground,
            unfocusedContainerColor = CardBackground,
            disabledContainerColor = CardBackground,
            focusedBorderColor = Color(0xFF2D3142),
            unfocusedBorderColor = Color(0xFF232636),
            focusedLeadingIconColor = TextGray,
            unfocusedLeadingIconColor = TextGray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = TextGray,
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 4.dp)
            ) {
                if (query.isNotEmpty()) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "Clear",
                            tint = TextGray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                FilledIconButton(
                    onClick = {},
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = CodeBg,
                        contentColor = TextGray
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Tune,
                        contentDescription = "Filter",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    )
}


// =============================================================================
// 3. CATEGORY VERTICAL RAIL
// =============================================================================

@Composable
fun CategoryVerticalRail(
    categories: List<CategoryCollection>,
    selectedCategoryId: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(96.dp)
            .fillMaxHeight()
            .background(Color(0xFF0C0F1B))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category.id == selectedCategoryId
            val interactionSource = remember { MutableInteractionSource() }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onCategorySelected(category.id) }
                    .padding(vertical = 6.dp, horizontal = 4.dp)
            ) {
                // Circle Icon Box
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            color = if (isSelected) Color(0xFF1E2336) else Color(0xFF131624),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) PurpleAccent.copy(alpha = 0.6f) else Color(0xFF1B2032),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = category.iconVector,
                        contentDescription = category.name,
                        tint = if (isSelected) PurpleAccent else TextGray,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = category.name,
                    color = if (isSelected) PurpleAccent else TextGray,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    lineHeight = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}


// =============================================================================
// 4. CATEGORY HERO PROMO CARD
// =============================================================================

@Composable
fun CategoryHeroPromoCard(
    category: CategoryCollection,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // "FEATURED SECTOR" Tag
        Text(
            text = "FEATURED SECTOR",
            color = CyanAccent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Grid of subcategories (2 columns)
        val rows = category.subcategories.chunked(2)
        rows.forEach { rowSubcats ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowSubcats.forEach { subCat ->
                    SubcategoryGridCard(
                        subCategory = subCat,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowSubcats.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Certification Badge Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardBackground
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFF132A2B), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = "Certified",
                            tint = CyanAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Lossless Certification",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "All audio tuned to Hi-Res 96kHz/24bit",
                            color = TextGray,
                            fontSize = 11.sp
                        )
                    }
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Details",
                    tint = TextGray,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun SubcategoryGridCard(
    subCategory: SubCategory,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Header Row (Item count + Arrow)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF18262E), RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "${subCategory.itemCount} ITEMS",
                        color = CyanAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go",
                    tint = TextGray,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Center Circle Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color(0xFF121522), CircleShape)
                    .border(1.dp, Color(0xFF22273B), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = subCategory.iconVector,
                    contentDescription = subCategory.name,
                    tint = CyanAccent,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Name
            Text(
                text = subCategory.name,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle / Specs
            Text(
                text = when (subCategory.name) {
                    "Over-Ear Headphones" -> "ANC • Studio grade"
                    "True Wireless Earbuds" -> "IPX8 • Hi-Res LDAC"
                    "Studio DACs & Amps" -> "32-Bit Lossless"
                    "Wireless Speakers" -> "Spatial 360° Field"
                    "Boomboxes & Outdoor" -> "Rugged IP67"
                    else -> "OFC Silver plated"
                },
                color = TextGray,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


// =============================================================================
// 5. SUBCATEGORY CHIPS ROW
// =============================================================================

@Composable
fun SubcategoryChipsRow(
    subcategories: List<SubCategory>,
    selectedSubcategoryId: String?,
    onSubcategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        // "All" Chip
        item {
            val isAllSelected = selectedSubcategoryId == null
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isAllSelected) PurpleAccent else CodeBg)
                    .border(
                        width = 1.dp,
                        color = if (isAllSelected) PurpleAccent else Color(0xFF282B3C),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onSubcategorySelected(null) }
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "All",
                    color = if (isAllSelected) Color(0xFF0A0B10) else Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(subcategories) { subcat ->
            val isSelected = subcat.id == selectedSubcategoryId
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) PurpleAccent else CodeBg)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) PurpleAccent else Color(0xFF282B3C),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onSubcategorySelected(subcat.id) }
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = subcat.name,
                    color = if (isSelected) Color(0xFF0A0B10) else Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


// =============================================================================
// 6. CATEGORY PRODUCT CARD
// =============================================================================

@Composable
fun CategoryProductCard(
    product: Product,
    onProductClick: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable { onProductClick(product.id) },
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
                        .clickable { onToggleFavorite(product.id) },
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
                        .clickable { onAddToCart(product) },
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
// PREVIEWS
// =============================================================================

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CategoriesTopBarPreview() {
    CategoriesTopBar(onNotificationsClick = {})
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CategoriesSearchBarPreview() {
    CategoriesSearchBar(
        query = "",
        onQueryChanged = {},
        onClearQuery = {}
    )
}
