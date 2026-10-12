package com.dhruva.auratech.ui.categories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.auratech.R
import com.dhruva.auratech.model.CategoryCollection
import com.dhruva.auratech.model.Product
import com.dhruva.auratech.model.SubCategory
import com.dhruva.auratech.ui.categories.components.CategoriesSearchBar
import com.dhruva.auratech.ui.categories.components.CategoriesTopBar
import com.dhruva.auratech.ui.categories.components.CategoryHeroPromoCard
import com.dhruva.auratech.ui.categories.components.CategoryProductCard
import com.dhruva.auratech.ui.categories.components.CategoryVerticalRail
import com.dhruva.auratech.ui.categories.components.SubcategoryChipsRow
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.home.components.StoreBottomNavBar
import com.dhruva.auratech.ui.theme.AuraTechTheme
import com.dhruva.auratech.ui.theme.DarkBackground

@Composable
fun CategoriesScreen(
    uiState: CategoriesUiState,
    onAction: (CategoriesAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            CategoriesTopBar(
                onNotificationsClick = { onAction(CategoriesAction.OnNotificationsClick) }
            )
        },
        bottomBar = {
            StoreBottomNavBar(
                selectedTab = uiState.selectedTab,
                cartItemCount = uiState.cartItemCount,
                onTabSelected = { tab -> onAction(CategoriesAction.OnTabSelected(tab)) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Search Bar
                CategoriesSearchBar(
                    query = uiState.searchQuery,
                    onQueryChanged = { query -> onAction(CategoriesAction.OnSearchQueryChanged(query)) },
                    onClearQuery = { onAction(CategoriesAction.OnClearSearch) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Split Layout: Vertical Rail + Content Area
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Left Vertical Rail
                    CategoryVerticalRail(
                        categories = uiState.categories,
                        selectedCategoryId = uiState.selectedCategoryId,
                        onCategorySelected = { categoryId ->
                            onAction(CategoriesAction.OnSelectCategory(categoryId))
                        }
                    )

                    // Right Main Content Panel
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        uiState.currentCategory?.let { currentCat ->
                            // Subcategories Grid / Featured Sector Card
                            CategoryHeroPromoCard(category = currentCat)

                            Spacer(modifier = Modifier.height(16.dp))

                            // Subcategory Chips Row
                            if (currentCat.subcategories.isNotEmpty()) {
                                SubcategoryChipsRow(
                                    subcategories = currentCat.subcategories,
                                    selectedSubcategoryId = uiState.selectedSubcategoryId,
                                    onSubcategorySelected = { subCatId ->
                                        onAction(CategoriesAction.OnSelectSubcategory(subCatId))
                                    }
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            // Filtered Product Grid
                            val productsToDisplay = uiState.filteredProducts
                            if (productsToDisplay.isNotEmpty()) {
                                val rows = productsToDisplay.chunked(2)
                                rows.forEach { rowProducts ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 12.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        rowProducts.forEach { product ->
                                            CategoryProductCard(
                                                product = product,
                                                onProductClick = { id ->
                                                    onAction(
                                                        CategoriesAction.OnProductClick(
                                                            id
                                                        )
                                                    )
                                                },
                                                onAddToCart = { prod ->
                                                    onAction(
                                                        CategoriesAction.OnAddToCart(
                                                            prod
                                                        )
                                                    )
                                                },
                                                onToggleFavorite = { id ->
                                                    onAction(
                                                        CategoriesAction.OnToggleFavorite(
                                                            id
                                                        )
                                                    )
                                                },
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

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }

            CartFeedbackToast(
                message = uiState.toastMessage,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

/*
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onNavigateToProduct: (String) -> Unit = {}
) {
    val sampleAudioSubcats = remember {
        listOf(
            SubCategory("sub-1", "Over-Ear Headphones", 14, Icons.Default.Headset),
            SubCategory("sub-2", "True Wireless Earbuds", 22, Icons.Default.Headset),
            SubCategory("sub-3", "Studio DACs & Amps", 8, Icons.Default.Headset),
            SubCategory("sub-4", "Wireless Speakers", 16, Icons.Default.Headset),
            SubCategory("sub-5", "Boomboxes & Outdoor", 6, Icons.Default.Headset),
            SubCategory("sub-6", "Audio Cables & Accs", 34, Icons.Default.Headset)
        )
    }

    val sampleAudioProducts = remember {
        listOf(
            Product(
                id = "1",
                title = "AeroPro Wireless ANC",
                originalPrice = 385.0,
                currentPrice = 249.0,
                discountPercent = 35,
                rating = 4.9f,
                reviewCount = 128,
                imageRes = R.drawable.user_avatar,
                stockRemainingText = "Planar Magnetic • 40H"
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
                stockRemainingText = "Sapphire Glass • ECG"
            )
        )
    }

    val sampleCategories = remember {
        listOf(
            CategoryCollection(
                id = "cat_audio",
                name = "Audio & Sound",
                iconVector = Icons.Default.Headset,
                bannerTag = "ACOUSTICS",
                bannerTitle = "High-Fidelity Audio",
                bannerSubtitle = "Studio-grade ANC headphones, Lossless DACs & wireless speakers.",
                discountText = "30% OFF",
                subcategories = sampleAudioSubcats,
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_wearables",
                name = "Wearables & Health",
                iconVector = Icons.Default.Watch,
                bannerTag = "BIO SENSORS",
                bannerTitle = "Next-Gen Smartwatches",
                bannerSubtitle = "ECG monitoring & pulse tracking.",
                discountText = "20% OFF",
                subcategories = listOf(
                    SubCategory("sub-w1", "Smartwatches", 12, Icons.Default.Watch),
                    SubCategory("sub-w2", "Fitness Trackers", 8, Icons.Default.Watch)
                ),
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_gaming",
                name = "Computing & Gaming",
                iconVector = Icons.Default.Gamepad,
                bannerTag = "CYBER GEAR",
                bannerTitle = "Pro Gaming Hardware",
                bannerSubtitle = "Mechanical keyboards & ultra-low latency headsets.",
                discountText = "15% OFF",
                subcategories = listOf(
                    SubCategory("sub-g1", "Gaming Headsets", 10, Icons.Default.Gamepad),
                    SubCategory("sub-g2", "Keyboards", 15, Icons.Default.Gamepad)
                ),
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_smart",
                name = "Smart Living",
                iconVector = Icons.Default.DeveloperBoard,
                bannerTag = "IOT HUBS",
                bannerTitle = "Smart Home Control",
                bannerSubtitle = "Connected power & automated lighting.",
                discountText = "25% OFF",
                subcategories = listOf(
                    SubCategory("sub-s1", "Smart Hubs", 7, Icons.Default.DeveloperBoard)
                ),
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_mobile",
                name = "Mobile Access.",
                iconVector = Icons.Default.PhoneAndroid,
                bannerTag = "MAGSAFE",
                bannerTitle = "Armor Cases & Chargers",
                bannerSubtitle = "Military-grade protection & wireless stands.",
                discountText = "10% OFF",
                subcategories = listOf(
                    SubCategory("sub-m1", "Armor Cases", 25, Icons.Default.PhoneAndroid)
                ),
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_photo",
                name = "Photo & Drones",
                iconVector = Icons.Default.CameraAlt,
                bannerTag = "AERIAL 4K",
                bannerTitle = "Action Cameras & Drones",
                bannerSubtitle = "Cinematic 4K 120fps recording gear.",
                discountText = "18% OFF",
                subcategories = listOf(
                    SubCategory("sub-p1", "FPV Drones", 6, Icons.Default.CameraAlt)
                ),
                products = sampleAudioProducts
            )
        )
    }

    var selectedCategoryId by remember { mutableStateOf("cat_audio") }
    var selectedSubcategoryId by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var cartCount by remember { mutableIntStateOf(2) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    val uiState = CategoriesUiState(
        selectedCategoryId = selectedCategoryId,
        selectedSubcategoryId = selectedSubcategoryId,
        searchQuery = searchQuery,
        categories = sampleCategories,
        cartItemCount = cartCount,
        toastMessage = toastMessage
    )

    CategoriesScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is CategoriesAction.OnSelectCategory -> {
                    selectedCategoryId = action.categoryId
                    selectedSubcategoryId = null
                }
                is CategoriesAction.OnSelectSubcategory -> {
                    selectedSubcategoryId = action.subcategoryId
                }
                is CategoriesAction.OnSearchQueryChanged -> {
                    searchQuery = action.query
                }
                is CategoriesAction.OnClearSearch -> {
                    searchQuery = ""
                }
                is CategoriesAction.OnProductClick -> {
                    onNavigateToProduct(action.productId)
                }
                is CategoriesAction.OnAddToCart -> {
                    cartCount++
                    toastMessage = "Added ${action.product.title} to cart"
                }
                else -> {}
            }
        },
        modifier = modifier
    )
}
*/

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesScreenPreview() {
    val sampleAudioSubcats = remember {
        listOf(
            SubCategory("sub-1", "Over-Ear Headphones", 14, Icons.Default.Headset),
            SubCategory("sub-2", "True Wireless Earbuds", 22, Icons.Default.Headset),
            SubCategory("sub-3", "Studio DACs & Amps", 8, Icons.Default.Headset),
            SubCategory("sub-4", "Wireless Speakers", 16, Icons.Default.Headset),
            SubCategory("sub-5", "Boomboxes & Outdoor", 6, Icons.Default.Headset),
            SubCategory("sub-6", "Audio Cables & Accs", 34, Icons.Default.Headset)
        )
    }

    val sampleAudioProducts = remember {
        listOf(
            Product(
                id = "1",
                title = "AeroPro Wireless ANC",
                originalPrice = 385.0,
                currentPrice = 249.0,
                discountPercent = 35,
                rating = 4.9f,
                reviewCount = 128,
                imageRes = R.drawable.user_avatar,
                stockRemainingText = "Planar Magnetic • 40H"
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
                stockRemainingText = "Sapphire Glass • ECG"
            )
        )
    }

    val sampleCategories = remember {
        listOf(
            CategoryCollection(
                id = "cat_audio",
                name = "Audio & Sound",
                iconVector = Icons.Default.Headset,
                bannerTag = "ACOUSTICS",
                bannerTitle = "High-Fidelity Audio",
                bannerSubtitle = "Studio-grade ANC headphones, Lossless DACs & wireless speakers.",
                discountText = "30% OFF",
                subcategories = sampleAudioSubcats,
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_wearables",
                name = "Wearables & Health",
                iconVector = Icons.Default.Watch,
                bannerTag = "BIO SENSORS",
                bannerTitle = "Next-Gen Smartwatches",
                bannerSubtitle = "ECG monitoring & pulse tracking.",
                discountText = "20% OFF",
                subcategories = listOf(
                    SubCategory("sub-w1", "Smartwatches", 12, Icons.Default.Watch),
                    SubCategory("sub-w2", "Fitness Trackers", 8, Icons.Default.Watch)
                ),
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_gaming",
                name = "Computing & Gaming",
                iconVector = Icons.Default.Gamepad,
                bannerTag = "CYBER GEAR",
                bannerTitle = "Pro Gaming Hardware",
                bannerSubtitle = "Mechanical keyboards & ultra-low latency headsets.",
                discountText = "15% OFF",
                subcategories = listOf(
                    SubCategory("sub-g1", "Gaming Headsets", 10, Icons.Default.Gamepad),
                    SubCategory("sub-g2", "Keyboards", 15, Icons.Default.Gamepad)
                ),
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_smart",
                name = "Smart Living",
                iconVector = Icons.Default.DeveloperBoard,
                bannerTag = "IOT HUBS",
                bannerTitle = "Smart Home Control",
                bannerSubtitle = "Connected power & automated lighting.",
                discountText = "25% OFF",
                subcategories = listOf(
                    SubCategory("sub-s1", "Smart Hubs", 7, Icons.Default.DeveloperBoard)
                ),
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_mobile",
                name = "Mobile Access.",
                iconVector = Icons.Default.PhoneAndroid,
                bannerTag = "MAGSAFE",
                bannerTitle = "Armor Cases & Chargers",
                bannerSubtitle = "Military-grade protection & wireless stands.",
                discountText = "10% OFF",
                subcategories = listOf(
                    SubCategory("sub-m1", "Armor Cases", 25, Icons.Default.PhoneAndroid)
                ),
                products = sampleAudioProducts
            ),
            CategoryCollection(
                id = "cat_photo",
                name = "Photo & Drones",
                iconVector = Icons.Default.CameraAlt,
                bannerTag = "AERIAL 4K",
                bannerTitle = "Action Cameras & Drones",
                bannerSubtitle = "Cinematic 4K 120fps recording gear.",
                discountText = "18% OFF",
                subcategories = listOf(
                    SubCategory("sub-p1", "FPV Drones", 6, Icons.Default.CameraAlt)
                ),
                products = sampleAudioProducts
            )
        )
    }
    AuraTechTheme {
        CategoriesScreen(
            uiState = CategoriesUiState(
                categories = sampleCategories
            ),
            onAction = {}
        )
    }
}
