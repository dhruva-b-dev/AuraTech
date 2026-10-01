package com.dhruva.auratech.ui.cart

import android.content.res.Configuration
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onNavigateToProduct: (String) -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    var items by remember {
        mutableStateOf(
            listOf(
                CartItem(
                    id = "item-1",
                    productId = "p1",
                    title = "AURA SoundMax Pro ANC",
                    imageRes = R.drawable.user_avatar,
                    colorName = "Matte Obsidian",
                    colorHex = Color.Black,
                    tagText = "ANC",
                    isTagPrimary = true,
                    originalPrice = 249.0,
                    currentPrice = 239.0,
                    quantity = 1
                ),
                CartItem(
                    id = "item-2",
                    productId = "p2",
                    title = "AURA Pulse Chrono Smartwatch",
                    imageRes = R.drawable.user_avatar,
                    colorName = "Titanium Grey",
                    colorHex = Color.Gray,
                    tagText = "BESTSELLER",
                    isTagPrimary = false,
                    originalPrice = 269.0,
                    currentPrice = 258.0,
                    quantity = 2
                ),
                CartItem(
                    id = "item-3",
                    productId = "p3",
                    title = "AURA MagArmor Case",
                    imageRes = R.drawable.user_avatar,
                    colorName = "Midnight Indigo",
                    colorHex = Color.DarkGray,
                    tagText = "MAGSAFE",
                    isTagPrimary = true,
                    originalPrice = 35.0,
                    currentPrice = 29.0,
                    quantity = 1
                )
            )
        )
    }

    var couponInput by remember { mutableStateOf("") }
    var appliedCoupon by remember { mutableStateOf<String?>(null) }
    var couponDiscount by remember { mutableDoubleStateOf(0.0) }
    var couponFeedback by remember { mutableStateOf<String?>(null) }
    var isCouponError by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    val uiState = CartUiState(
        items = items,
        couponInput = couponInput,
        appliedCoupon = appliedCoupon,
        couponDiscount = couponDiscount,
        couponFeedback = couponFeedback,
        isCouponError = isCouponError,
        toastMessage = toastMessage
    )

    CartScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is CartAction.OnQuantityChanged -> {
                    items = items.mapNotNull { item ->
                        if (item.id == action.itemId) {
                            val newQty = item.quantity + action.delta
                            if (newQty > 0) item.copy(quantity = newQty) else null
                        } else item
                    }
                }
                is CartAction.OnRemoveItem -> {
                    items = items.filter { it.id != action.itemId }
                    toastMessage = "Item removed from cart"
                }
                is CartAction.OnClearCart -> {
                    items = emptyList()
                    toastMessage = "Cart cleared"
                }
                is CartAction.OnCouponInputChanged -> {
                    couponInput = action.code
                }
                is CartAction.OnApplyCoupon -> {
                    if (couponInput.equals("AURA30", ignoreCase = true)) {
                        appliedCoupon = "AURA30"
                        couponDiscount = 20.0
                        couponFeedback = "Coupon AURA30 applied! Saved $20.00"
                        isCouponError = false
                    } else {
                        couponFeedback = "Invalid coupon code"
                        isCouponError = true
                    }
                }
                is CartAction.OnProceedToCheckout -> {
                    toastMessage = "Proceeding to checkout..."
                }
                is CartAction.OnProductClick -> onNavigateToProduct(action.productId)
                is CartAction.OnStartShoppingClick -> onNavigateToHome()
                else -> {}
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CartScreenPreview() {
    CartScreen()
}
