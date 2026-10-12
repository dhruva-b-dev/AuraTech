package com.dhruva.auratech.ui.productdetails

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
import com.dhruva.auratech.ui.productdetails.components.ColorVariantPicker
import com.dhruva.auratech.ui.productdetails.components.CustomerReviewsSection
import com.dhruva.auratech.ui.productdetails.components.DeliveryPerksSection
import com.dhruva.auratech.ui.productdetails.components.KeyHighlightsSection
import com.dhruva.auratech.ui.productdetails.components.ProductDetailTopBar
import com.dhruva.auratech.ui.productdetails.components.ProductGallerySection
import com.dhruva.auratech.ui.productdetails.components.ProductMetadataSection
import com.dhruva.auratech.ui.productdetails.components.StickyAddToCartBar
import com.dhruva.auratech.ui.productdetails.components.WarrantyAndCareSection
import com.dhruva.auratech.ui.theme.AuraTechTheme
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
                onToggleFavorite = { onAction(ProductDetailAction.OnToggleFavorite) },
                onCartClick = { onAction(ProductDetailAction.OnCartClick) }
            )
        },
        bottomBar = {
            uiState.productDetail?.let {
                StickyAddToCartBar(
                    originalPrice = it.originalPrice,
                    currentPrice = uiState.productDetail.currentPrice,
                    discountPercent = uiState.productDetail.discountPercent,
                    isAddedToCart = uiState.isAddedToCart,
                    onAddToCartClick = { onAction(ProductDetailAction.OnAddToCart) }
                )
            }
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
                    galleryImages = uiState.productDetail?.galleryImages ?: emptyList(),
                    selectedIndex = uiState.selectedGalleryIndex,
                    isFavorite = uiState.isFavorite,
                    onSelectImage = { index -> onAction(ProductDetailAction.OnSelectGallery(index)) },
                    onShareClick = { onAction(ProductDetailAction.OnShareClick) },
                    onToggleFavorite = { onAction(ProductDetailAction.OnToggleFavorite) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                uiState.productDetail?.let { ProductMetadataSection(detail = it) }

                Spacer(modifier = Modifier.height(16.dp))

                uiState.productDetail?.let { KeyHighlightsSection(highlights = it.highlights) }

                Spacer(modifier = Modifier.height(16.dp))

                uiState.productDetail?.let {
                    ColorVariantPicker(
                        colors = it.colors,
                        selectedIndex = uiState.selectedColorIndex,
                        onSelectColor = { index -> onAction(ProductDetailAction.OnSelectColor(index)) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                WarrantyAndCareSection(
                    selectedOption = uiState.selectedWarrantyOption,
                    onSelectOption = { option -> onAction(ProductDetailAction.OnSelectWarranty(option)) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                DeliveryPerksSection()

                Spacer(modifier = Modifier.height(16.dp))

                uiState.productDetail?.let {
                    CustomerReviewsSection(
                        summary = uiState.productDetail.reviewsSummary,
                        featuredReview = it.featuredReview,
                        upvotes = uiState.reviewUpvotes,
                        downvotes = uiState.reviewDownvotes,
                        onVoteReview = { isUp -> onAction(ProductDetailAction.OnVoteReview(isUp)) },
                        onWriteReviewClick = { onAction(ProductDetailAction.OnWriteReviewClick) }
                    )
                }

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
fun ProductDetailScreenPreview() {
    AuraTechTheme {
        ProductDetailScreen(
            uiState = ProductDetailUiState(
                productDetail = MockData.sampleHeadphoneDetail,
            ),
            onAction = {}
        )
    }
}