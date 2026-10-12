package com.dhruva.auratech.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class BadgeIconType {
    SHIPPING,
    WARRANTY,
    VERIFIED
}

data class Category(
    val id: String,
    val name: String,
    val iconVector: ImageVector,
    val iconColor: Color,
    val containerColor: Color
)

data class Product(
    val id: String,
    val title: String,
    val originalPrice: Double,
    val currentPrice: Double,
    val currency: String = "USD",
    val discountPercent: Int = 0,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    @param:DrawableRes val imageRes: Int,
    val badgeText: String? = null,
    val stockProgress: Float = 0f,
    val stockRemainingText: String? = null,
    val stockClaimedText: String? = null,
    val isFavorite: Boolean = false,
    val deliveryBadge: String? = null,
    val deliveryBadgeIconType: BadgeIconType = BadgeIconType.SHIPPING
)

data class PromoBanner(
    val id: String,
    val tag: String,
    val title: String,
    val promoCode: String,
    val remainingSeconds: Long
)

data class TrustBadge(
    val id: String,
    val label: String,
    val iconVector: ImageVector,
    val tint: Color
)

data class EditorialBanner(
    val id: String,
    val tag: String,
    val title: String,
    val subtitle: String,
    @param:DrawableRes val imageRes: Int
)

enum class BottomNavTab(
    val title: String,
    val badgeCount: Int = 0
) {
    HOME("Home"),
    CATEGORIES("Categories"),
    CART("Cart", badgeCount = 0),
    PROFILE("Profile")
}

// =============================================================================
// PRODUCT DETAILS SCREEN DATA MODELS
// =============================================================================

data class ColorVariant(
    val id: String,
    val name: String,
    val color: Color
)

data class ProductHighlight(
    val iconVector: ImageVector,
    val title: String,
    val subtitle: String
)

data class ReviewSummary(
    val averageRating: Float,
    val totalReviews: Int,
    val satisfactionPercent: Int,
    val star5Percent: Int,
    val star4Percent: Int,
    val star3Percent: Int
)

data class UserReview(
    val id: String,
    val reviewerName: String,
    val initials: String,
    val isVerified: Boolean,
    val dateText: String,
    val comment: String,
    val purchasedVariant: String,
    val upvotes: Int,
    val downvotes: Int
)

data class ProductDetail(
    val id: String,
    val brand: String,
    val sku: String,
    val title: String,
    val subtitle: String,
    val originalPrice: Double,
    val currentPrice: Double,
    val discountPercent: Int,
    val rating: Float,
    val reviewCount: Int,
    val satisfactionPercent: Int,
    val galleryImages: List<Int>,
    val colors: List<ColorVariant>,
    val highlights: List<ProductHighlight>,
    val reviewsSummary: ReviewSummary,
    val featuredReview: UserReview
)

// =============================================================================
// USER PROFILE SCREEN DATA MODELS
// =============================================================================

data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    @param:DrawableRes val avatarRes: Int,
    val isVerified: Boolean,
    val memberTier: String,
    val walletBalance: Double,
    val rewardPoints: Int,
    val activeCouponsCount: Int
)

data class OrderStatusCount(
    val processingCount: Int,
    val deliveredCount: Int,
    val cancelledCount: Int,
    val returnedCount: Int
)

data class ActiveDelivery(
    val orderId: String,
    val statusText: String,
    val subtext: String
)

data class ProfileMenuItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconVector: ImageVector,
    val badgeText: String? = null,
    val isHighlightedBadge: Boolean = false
)

data class Address(
    val id: String,
    val title: String,
    val fullName: String,
    val streetAddress: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val phone: String,
    val isDefault: Boolean
)

data class OrderItem(
    val id: String,
    val orderId: String,
    val dateText: String,
    val totalAmount: Double,
    val status: String,
    val itemsSummary: String,
    val shippingAddress: String,
    val paymentMethod: String
)
