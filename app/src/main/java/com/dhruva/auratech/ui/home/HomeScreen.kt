package com.dhruva.auratech.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.dhruva.auratech.ui.home.components.BestSellersSection
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.home.components.EditorialBannersSection
import com.dhruva.auratech.ui.home.components.FeaturedCategoriesSection
import com.dhruva.auratech.ui.home.components.FlashDealsSection
import com.dhruva.auratech.ui.home.components.HomeTopBar
import com.dhruva.auratech.ui.home.components.PromoBanner
import com.dhruva.auratech.ui.home.components.SearchAndFilter
import com.dhruva.auratech.ui.home.components.StoreBottomNavBar
import com.dhruva.auratech.ui.home.components.TrustBadgesBlocks
import com.dhruva.auratech.ui.theme.AuraTechTheme
import com.dhruva.auratech.ui.theme.DarkBackground

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSearching = uiState.searchQuery.isNotBlank()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            HomeTopBar(
                onProfileClick = { onAction(HomeAction.OnProfileClick) },
                onNotificationsClick = { onAction(HomeAction.OnNotificationsClick) },
                onWishlistClick = { onAction(HomeAction.OnWishlistClick) }
            )
        },
        bottomBar = {
            StoreBottomNavBar(
                selectedTab = uiState.selectedTab,
                cartItemCount = uiState.cartItemCount,
                onTabSelected = { tab -> onAction(HomeAction.OnTabSelected(tab)) }

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
                    .verticalScroll(rememberScrollState())
            ) {
                // Search & Filter
                SearchAndFilter(
                    query = uiState.searchQuery,
                    onQueryChanged = { onAction(HomeAction.OnSearchQueryChanged(it)) },
                    onClearSearch = { onAction(HomeAction.OnClearSearch) },
                    onFilterClick = { onAction(HomeAction.OnFilterClick) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                if (isSearching) {
                    // Dedicated Search Results View right below search bar
                    val searchResults = (uiState.filteredFlashDeals + uiState.filteredBestSellers).distinctBy { it.id }
                    BestSellersSection(
                        products = searchResults,
                        onToggleFavorite = { onAction(HomeAction.OnToggleFavorite(it)) },
                        onAddToCart = { onAction(HomeAction.OnAddToCart(it)) },
                        onProductClick = { onAction(HomeAction.OnProductClick(it)) },
                        onFilterSortClick = { onAction(HomeAction.OnFilterClick) }
                    )
                } else {
                    // Standard Home Screen Flow

                    // 1. Promo Banner
                    if (uiState.heroBanner != null) {
                        PromoBanner(
                            banner = uiState.heroBanner,
                            hours = uiState.countdownHours,
                            minutes = uiState.countdownMinutes,
                            seconds = uiState.countdownSeconds,
                            onShopNowClick = { onAction(HomeAction.OnBannerShopNowClick) }
                        )
                    }

                    // 2. Hardware Sectors / Categories
                    FeaturedCategoriesSection(
                        categories = uiState.categories,
                        selectedCategoryId = uiState.selectedCategoryId,
                        onCategoryClick = { onAction(HomeAction.OnCategorySelected(it)) },
                        onViewAllClick = { onAction(HomeAction.OnViewAllCategoriesClick) }
                    )

                    // 3. Flash Deals / Allocations
                    FlashDealsSection(
                        deals = uiState.filteredFlashDeals,
                        onProductClick = { onAction(HomeAction.OnProductClick(it)) },
                        onViewAllClick = { onAction(HomeAction.OnViewAllFlashDealsClick) }
                    )

                    // 4. Best Sellers
                    BestSellersSection(
                        products = uiState.filteredBestSellers,
                        onToggleFavorite = { onAction(HomeAction.OnToggleFavorite(it)) },
                        onAddToCart = { onAction(HomeAction.OnAddToCart(it)) },
                        onProductClick = { onAction(HomeAction.OnProductClick(it)) },
                        onFilterSortClick = { onAction(HomeAction.OnFilterClick) }
                    )

                    // 5. Editorial Banners
                    EditorialBannersSection(
                        banners = uiState.editorialBanners,
                        onBannerClick = { onAction(HomeAction.OnViewAllCategoriesClick) }
                    )

                    // 6. Trust Guarantees at bottom
                    TrustBadgesBlocks(badges = uiState.trustBadges)
                }
            }

            // Toast feedback
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
fun HomeScreenPreview() {
    AuraTechTheme {
        HomeScreen(
            uiState = HomeUiState(
                trustBadges = MockData.trustBadges,
                heroBanner = MockData.heroPromoBanner,
                remainingSeconds = MockData.heroPromoBanner.remainingSeconds,
                categories = MockData.categories,
                flashDeals = MockData.flashDeals,
                editorialBanners = MockData.editorialBanners,
                bestSellers = MockData.bestSellers,
                cartItemCount = 2
            ),
            onAction = {}
        )
    }
}