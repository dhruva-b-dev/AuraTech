package com.dhruva.auratech.ui.productdetails

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
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.auratech.R
import com.dhruva.auratech.model.ColorVariant
import com.dhruva.auratech.model.ProductDetail
import com.dhruva.auratech.model.ProductHighlight
import com.dhruva.auratech.model.ReviewSummary
import com.dhruva.auratech.model.UserReview
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.productdetails.components.ColorVariantPicker
import com.dhruva.auratech.ui.productdetails.components.CustomerReviewsSection
import com.dhruva.auratech.ui.productdetails.components.DeliveryPerksSection
import com.dhruva.auratech.ui.productdetails.components.KeyHighlightsSection
import com.dhruva.auratech.ui.productdetails.components.ProductDetailTopBar
import com.dhruva.auratech.ui.productdetails.components.ProductGallerySection
import com.dhruva.auratech.ui.productdetails.components.ProductMetadataSection
import com.dhruva.auratech.ui.productdetails.components.StickyAddToCartBar
import com.dhruva.auratech.ui.productdetails.components.WarrantyAndCareSection
import com.dhruva.auratech.ui.theme.DarkBackground

@Composable
fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    onAction: (ProductDetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            ProductDetailTopBar(
                isFavorite = uiState.isFavorite,
                onBackClick = { onAction(ProductDetailAction.OnBackClick) },
                onShareClick = { onAction(ProductDetailAction.OnShareClick) },
                onToggleFavorite = { onAction(ProductDetailAction.OnToggleFavorite) }
            )
        },
        bottomBar = {
            StickyAddToCartBar(
                originalPrice = uiState.productDetail.originalPrice,
                currentPrice = uiState.productDetail.currentPrice,
                discountPercent = uiState.productDetail.discountPercent,
                isAddedToCart = uiState.isAddedToCart,
                onAddToCartClick = { onAction(ProductDetailAction.OnAddToCart) }
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

                ProductGallerySection(
                    galleryImages = uiState.productDetail.galleryImages,
                    selectedIndex = uiState.selectedGalleryIndex,
                    isFavorite = uiState.isFavorite,
                    onSelectImage = { index -> onAction(ProductDetailAction.OnSelectGallery(index)) },
                    onShareClick = { onAction(ProductDetailAction.OnShareClick) },
                    onToggleFavorite = { onAction(ProductDetailAction.OnToggleFavorite) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProductMetadataSection(detail = uiState.productDetail)

                Spacer(modifier = Modifier.height(16.dp))

                KeyHighlightsSection(highlights = uiState.productDetail.highlights)

                Spacer(modifier = Modifier.height(16.dp))

                ColorVariantPicker(
                    colors = uiState.productDetail.colors,
                    selectedIndex = uiState.selectedColorIndex,
                    onSelectColor = { index -> onAction(ProductDetailAction.OnSelectColor(index)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                WarrantyAndCareSection(
                    selectedOption = uiState.selectedWarrantyOption,
                    onSelectOption = { option -> onAction(ProductDetailAction.OnSelectWarranty(option)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                DeliveryPerksSection()

                Spacer(modifier = Modifier.height(16.dp))

                CustomerReviewsSection(
                    summary = uiState.productDetail.reviewsSummary,
                    featuredReview = uiState.productDetail.featuredReview,
                    upvotes = uiState.reviewUpvotes,
                    downvotes = uiState.reviewDownvotes,
                    onVoteReview = { isUp -> onAction(ProductDetailAction.OnVoteReview(isUp)) },
                    onWriteReviewClick = { onAction(ProductDetailAction.OnWriteReviewClick) }
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
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    val sampleDetail = remember {
        ProductDetail(
            id = "p1",
            brand = "AURA AUDIO STUDIO",
            sku = "AU-9908X",
            title = "AURA SoundMax Pro ANC Wireless Headphones",
            subtitle = "Planar Magnetic Drivers • Lossless Audio • 40H Battery",
            originalPrice = 299.0,
            currentPrice = 239.00,
            discountPercent = 20,
            rating = 4.9f,
            reviewCount = 248,
            satisfactionPercent = 98,
            galleryImages = listOf(
                R.drawable.user_avatar,
                R.drawable.user_avatar,
                R.drawable.user_avatar,
                R.drawable.user_avatar
            ),
            colors = listOf(
                ColorVariant("c1", "Matte Obsidian", Color.Black),
                ColorVariant("c2", "Titanium Silver", Color.LightGray),
                ColorVariant("c3", "Midnight Navy", Color(0xFF1B2038))
            ),
            highlights = listOf(
                ProductHighlight(Icons.Filled.BatteryChargingFull, "45h Battery", "Fast USB-C"),
                ProductHighlight(Icons.Filled.GraphicEq, "Spatial Audio", "3D Head Track"),
                ProductHighlight(Icons.Filled.HighQuality, "Lossless Hi-Res", "LDAC & aptX HD")
            ),
            reviewsSummary = ReviewSummary(4.9f, 248, 98, 85, 12, 3),
            featuredReview = UserReview(
                id = "r1",
                reviewerName = "Marcus Vance",
                initials = "MV",
                isVerified = true,
                dateText = "2 days ago",
                comment = "The soundstage on these planar magnetic drivers is incredible. Deep tight bass and pristine highs.",
                purchasedVariant = "Matte Obsidian",
                upvotes = 22,
                downvotes = 1
            )
        )
    }

    var selectedGalleryIndex by remember { mutableIntStateOf(0) }
    var selectedColorIndex by remember { mutableIntStateOf(0) }
    var isFavorite by remember { mutableStateOf(false) }
    var selectedWarrantyOption by remember { mutableIntStateOf(0) }
    var isAddedToCart by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var upvotes by remember { mutableIntStateOf(22) }
    var downvotes by remember { mutableIntStateOf(1) }

    val uiState = ProductDetailUiState(
        productDetail = sampleDetail,
        selectedGalleryIndex = selectedGalleryIndex,
        selectedColorIndex = selectedColorIndex,
        isFavorite = isFavorite,
        selectedWarrantyOption = selectedWarrantyOption,
        isAddedToCart = isAddedToCart,
        toastMessage = toastMessage,
        reviewUpvotes = upvotes,
        reviewDownvotes = downvotes
    )

    ProductDetailScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is ProductDetailAction.OnSelectGallery -> selectedGalleryIndex = action.index
                is ProductDetailAction.OnSelectColor -> selectedColorIndex = action.index
                is ProductDetailAction.OnToggleFavorite -> isFavorite = !isFavorite
                is ProductDetailAction.OnSelectWarranty -> selectedWarrantyOption = action.option
                is ProductDetailAction.OnAddToCart -> {
                    isAddedToCart = true
                    toastMessage = "Added to Cart"
                }
                is ProductDetailAction.OnVoteReview -> {
                    if (action.isUpvote) upvotes++ else downvotes++
                }
                is ProductDetailAction.OnBackClick -> onNavigateBack()
                else -> {}
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailsScreen()
}
