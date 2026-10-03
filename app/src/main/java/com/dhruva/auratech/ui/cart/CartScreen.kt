package com.dhruva.auratech.ui.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
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
import com.dhruva.auratech.R
import com.dhruva.auratech.model.CartItem
import com.dhruva.auratech.ui.cart.components.CartHeaderSection
import com.dhruva.auratech.ui.cart.components.CartItemCard
import com.dhruva.auratech.ui.cart.components.CartTopBar
import com.dhruva.auratech.ui.cart.components.EmptyCartView
import com.dhruva.auratech.ui.cart.components.FreeDeliveryBanner
import com.dhruva.auratech.ui.cart.components.OrderSummaryCard
import com.dhruva.auratech.ui.cart.components.PromoCouponSection
import com.dhruva.auratech.ui.cart.components.StickyCheckoutActionBar
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.home.components.StoreBottomNavBar
import com.dhruva.auratech.ui.theme.AuraTechTheme
import com.dhruva.auratech.ui.theme.DarkBackground

@Composable
fun CartScreen(
    uiState: CartUiState,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            CartTopBar(
                onNotificationsClick = { onAction(CartAction.OnNotificationsClick) }
            )
        },
        bottomBar = {
            StoreBottomNavBar(
                selectedTab = uiState.selectedTab,
                cartItemCount = uiState.totalItemUnits,
                onTabSelected = { tab -> onAction(CartAction.OnTabSelected(tab)) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.items.isEmpty()) {
                EmptyCartView(
                    onStartShopping = { onAction(CartAction.OnStartShoppingClick) },
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    FreeDeliveryBanner()

                    Spacer(modifier = Modifier.height(16.dp))

                    CartHeaderSection(
                        totalItems = uiState.totalItemUnits,
                        onClearCart = { onAction(CartAction.OnClearCart) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        uiState.items.forEach { item ->
                            CartItemCard(
                                item = item,
                                onQuantityChanged = { itemId, delta ->
                                    onAction(CartAction.OnQuantityChanged(itemId, delta))
                                },
                                onRemoveItem = { itemId ->
                                    onAction(CartAction.OnRemoveItem(itemId))
                                },
                                onProductClick = { productId ->
                                    onAction(CartAction.OnProductClick(productId))
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    PromoCouponSection(
                        couponInput = uiState.couponInput,
                        onCouponInputChanged = { code ->
                            onAction(CartAction.OnCouponInputChanged(code))
                        },
                        onApplyCoupon = { onAction(CartAction.OnApplyCoupon) },
                        appliedCoupon = uiState.appliedCoupon,
                        couponFeedback = uiState.couponFeedback,
                        isCouponError = uiState.isCouponError
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OrderSummaryCard(
                        totalUnits = uiState.totalItemUnits,
                        subtotal = uiState.subtotal,
                        discountSavings = uiState.discountSavings,
                        appliedCoupon = uiState.appliedCoupon,
                        couponDiscount = uiState.couponDiscount,
                        totalPayable = uiState.totalPayable
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    StickyCheckoutActionBar(
                        totalPayable = uiState.totalPayable,
                        isCheckingOut = uiState.isCheckingOut,
                        onProceedToCheckout = { onAction(CartAction.OnProceedToCheckout) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
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
fun CartScreenPreview() {
    AuraTechTheme() {
        CartScreen(
            uiState = CartUiState(
                items = listOf(
                    CartItem(
                        id = "item-1",
                        productId = "deal_3",
                        title = "Pro Sound Wireless Headphones",
                        imageRes = R.drawable.product_headphones_main,
                        colorName = "Color: Matte Black",
                        colorHex = androidx.compose.ui.graphics.Color(0xFF1E293B),
                        tagText = "18-month warranty",
                        isTagPrimary = false,
                        originalPrice = 70.00,
                        currentPrice = 59.00,
                        quantity = 1
                    ),
                    CartItem(
                        id = "item-2",
                        productId = "deal_2",
                        title = "Smart Watch Fit Pro",
                        imageRes = R.drawable.product_smartwatch,
                        colorName = "Strap: Graphite Silver",
                        colorHex = androidx.compose.ui.graphics.Color(0xFFCBD5E1),
                        tagText = "Best Seller",
                        isTagPrimary = true,
                        originalPrice = 55.00,
                        currentPrice = 45.00,
                        quantity = 2
                    ),
                    CartItem(
                        id = "item-3",
                        productId = "deal_1",
                        title = "Silicone Protective Case",
                        imageRes = R.drawable.product_case_peach,
                        colorName = "Color: Pastel Peach",
                        colorHex = androidx.compose.ui.graphics.Color(0xFFFFB3B6),
                        tagText = "Shockproof",
                        isTagPrimary = false,
                        originalPrice = 12.00,
                        currentPrice = 9.00,
                        quantity = 1
                    )
                ),
            ),
            onAction = {}
        )
    }
}
