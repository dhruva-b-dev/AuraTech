package com.dhruva.auratech.data.firestore

/**
 * Firestore-compatible data models.
 * These models use primitive types that can be stored directly in Firestore.
 * Compose-specific types (ImageVector, Color, @DrawableRes) are converted to strings.
 */

data class FirestoreProduct(
    val id: String = "",
    val title: String = "",
    val originalPrice: Double = 0.0,
    val currentPrice: Double = 0.0,
    val currency: String = "USD",
    val discountPercent: Int = 0,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val imageResName: String = "", // Drawable resource name (e.g., "product_earbuds")
    val badgeText: String? = null,
    val stockProgress: Float = 0f,
    val stockRemainingText: String? = null,
    val stockClaimedText: String? = null,
    val isFavorite: Boolean = false,
    val deliveryBadge: String? = null,
    val deliveryBadgeIconType: String = "SHIPPING" // SHIPPING, WARRANTY, VERIFIED
)

data class FirestoreCategory(
    val id: String = "",
    val name: String = "",
    val iconName: String = "", // Material icon name (e.g., "Devices")
    val iconColorHex: String = "", // Color hex string (e.g., "#FF0000")
    val containerColorHex: String = ""
)

data class FirestorePromoBanner(
    val id: String = "",
    val tag: String = "",
    val title: String = "",
    val promoCode: String = "",
    val remainingSeconds: Long = 0
)

data class FirestoreTrustBadge(
    val id: String = "",
    val label: String = "",
    val iconName: String = "",
    val tintHex: String = ""
)

data class FirestoreEditorialBanner(
    val id: String = "",
    val tag: String = "",
    val title: String = "",
    val subtitle: String = "",
    val imageResName: String = ""
)

data class FirestoreSubCategory(
    val id: String = "",
    val name: String = "",
    val itemCount: Int = 0,
    val iconName: String = ""
)

data class FirestoreCategoryCollection(
    val id: String = "",
    val name: String = "",
    val iconName: String = "",
    val bannerTag: String = "",
    val bannerTitle: String = "",
    val bannerSubtitle: String = "",
    val discountText: String = "",
    val subcategories: List<FirestoreSubCategory> = emptyList(),
    val products: List<FirestoreProduct> = emptyList()
)

data class FirestoreColorVariant(
    val id: String = "",
    val name: String = "",
    val colorHex: String = ""
)

data class FirestoreProductHighlight(
    val iconName: String = "",
    val title: String = "",
    val subtitle: String = ""
)

data class FirestoreReviewSummary(
    val averageRating: Float = 0f,
    val totalReviews: Int = 0,
    val satisfactionPercent: Int = 0,
    val star5Percent: Int = 0,
    val star4Percent: Int = 0,
    val star3Percent: Int = 0
)

data class FirestoreUserReview(
    val id: String = "",
    val reviewerName: String = "",
    val initials: String = "",
    val isVerified: Boolean = false,
    val dateText: String = "",
    val comment: String = "",
    val purchasedVariant: String = "",
    val upvotes: Int = 0,
    val downvotes: Int = 0
)

data class FirestoreProductDetail(
    val id: String = "",
    val brand: String = "",
    val sku: String = "",
    val title: String = "",
    val subtitle: String = "",
    val originalPrice: Double = 0.0,
    val currentPrice: Double = 0.0,
    val discountPercent: Int = 0,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val satisfactionPercent: Int = 0,
    val galleryImageResNames: List<String> = emptyList(),
    val colors: List<FirestoreColorVariant> = emptyList(),
    val highlights: List<FirestoreProductHighlight> = emptyList(),
    val reviewsSummary: FirestoreReviewSummary = FirestoreReviewSummary(),
    val featuredReview: FirestoreUserReview = FirestoreUserReview()
)

data class FirestoreCartItem(
    val id: String = "",
    val productId: String = "",
    val title: String = "",
    val imageResName: String = "",
    val colorName: String = "",
    val colorHex: String = "",
    val tagText: String = "",
    val isTagPrimary: Boolean = false,
    val originalPrice: Double = 0.0,
    val currentPrice: Double = 0.0,
    val quantity: Int = 1
)

data class FirestoreUserProfile(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val avatarResName: String = "",
    val isVerified: Boolean = false,
    val memberTier: String = "",
    val walletBalance: Double = 0.0,
    val rewardPoints: Int = 0,
    val activeCouponsCount: Int = 0
)

data class FirestoreOrderStatusCount(
    val userId: String = "",
    val processingCount: Int = 0,
    val deliveredCount: Int = 0,
    val cancelledCount: Int = 0,
    val returnedCount: Int = 0
)

data class FirestoreActiveDelivery(
    val userId: String = "",
    val orderId: String = "",
    val statusText: String = "",
    val subtext: String = ""
)

data class FirestoreProfileMenuItem(
    val id: String = "",
    val title: String = "",
    val subtitle: String = "",
    val iconName: String = "",
    val badgeText: String? = null,
    val isHighlightedBadge: Boolean = false
)

data class FirestoreAddress(
    val id: String = "",
    val title: String = "",
    val fullName: String = "",
    val streetAddress: String = "",
    val city: String = "",
    val state: String = "",
    val postalCode: String = "",
    val phone: String = "",
    val isDefault: Boolean = false
)

data class FirestoreOrder(
    val id: String = "",
    val orderId: String = "",
    val dateText: String = "",
    val totalAmount: Double = 0.0,
    val status: String = "Processing",
    val itemsSummary: String = "",
    val shippingAddress: String = "",
    val paymentMethod: String = ""
)
