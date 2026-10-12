package com.dhruva.auratech.ui.wishlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.home.components.GridProductCard
import com.dhruva.auratech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    uiState: WishlistUiState,
    onAction: (WishlistAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("Wishlist & Saved Items", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onAction(WishlistAction.OnBackClick) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.wishlistProducts.isEmpty() && !uiState.isLoading) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = TextGray, modifier = Modifier.size(56.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Your Wishlist is Empty", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Tap the heart icon on any product to save it here.", color = TextGray, fontSize = 14.sp)
                }
            } else {
                val rows = uiState.wishlistProducts.chunked(2)
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(rows) { rowProducts ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            rowProducts.forEach { product ->
                                GridProductCard(
                                    product = product,
                                    onToggleFavorite = { onAction(WishlistAction.OnToggleFavorite(product.id)) },
                                    onAddToCart = { onAction(WishlistAction.OnAddToCart(product)) },
                                    onClick = { onAction(WishlistAction.OnProductClick(product.id)) },
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

            CartFeedbackToast(
                message = uiState.toastMessage,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
            )
        }
    }
}
