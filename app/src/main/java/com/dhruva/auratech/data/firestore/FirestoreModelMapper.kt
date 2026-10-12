package com.dhruva.auratech.data.firestore

import android.graphics.Color as AndroidColor
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.NoiseAware
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.dhruva.auratech.R
import com.dhruva.auratech.model.ActiveDelivery
import com.dhruva.auratech.model.Address
import com.dhruva.auratech.model.BadgeIconType
import com.dhruva.auratech.model.CartItem
import com.dhruva.auratech.model.Category
import com.dhruva.auratech.model.CategoryCollection
import com.dhruva.auratech.model.ColorVariant
import com.dhruva.auratech.model.EditorialBanner
import com.dhruva.auratech.model.OrderStatusCount
import com.dhruva.auratech.model.OrderItem
import com.dhruva.auratech.model.Product
import com.dhruva.auratech.model.ProductDetail
import com.dhruva.auratech.model.ProductHighlight
import com.dhruva.auratech.model.ProfileMenuItem
import com.dhruva.auratech.model.PromoBanner
import com.dhruva.auratech.model.ReviewSummary
import com.dhruva.auratech.model.SubCategory
import com.dhruva.auratech.model.TrustBadge
import com.dhruva.auratech.model.UserProfile
import com.dhruva.auratech.model.UserReview

/**
 * Mapper to convert Firestore models to UI models.
 * Handles conversion of strings to Compose types (ImageVector, Color, DrawableRes).
 */
object FirestoreModelMapper {

    // =============================================================================
    // ICON MAPPING
    // =============================================================================

    private fun iconFromName(name: String): ImageVector = when (name) {
        "Devices" -> Icons.Outlined.Devices
        "Checkroom" -> Icons.Outlined.Checkroom
        "Spa" -> Icons.Outlined.Spa
        "Kitchen" -> Icons.Outlined.Kitchen
        "ShoppingCart" -> Icons.Outlined.ShoppingCart
        "FitnessCenter" -> Icons.Outlined.FitnessCenter
        "LocalShipping" -> Icons.Outlined.LocalShipping
        "Verified" -> Icons.Outlined.Verified
        "Autorenew" -> Icons.Outlined.Autorenew
        "BatteryChargingFull" -> Icons.Outlined.BatteryChargingFull
        "NoiseAware" -> Icons.Outlined.NoiseAware
        "WaterDrop" -> Icons.Outlined.WaterDrop
        "Security" -> Icons.Outlined.Security
        "LocationOn" -> Icons.Outlined.LocationOn
        "Mail" -> Icons.Outlined.Mail
        "SupportAgent" -> Icons.Outlined.SupportAgent
        "Favorite" -> Icons.Outlined.Favorite
        "Tune" -> Icons.Outlined.Tune
        "ShoppingBag" -> Icons.Outlined.ShoppingBag
        else -> Icons.Outlined.ShoppingCart // Default fallback
    }

    // =============================================================================
    // COLOR MAPPING
    // =============================================================================

    private fun colorFromHex(hex: String): Color {
        return try {
            val androidColor = AndroidColor.parseColor(hex)
            Color(androidColor)
        } catch (e: Exception) {
            Color.Gray // Fallback
        }
    }

    // =============================================================================
    // DRAWABLE RESOURCE MAPPING
    // =============================================================================

    @DrawableRes
    private fun drawableResFromName(name: String): Int = when (name) {
        "product_earbuds" -> R.drawable.product_earbuds
        "product_smartwatch" -> R.drawable.product_smartwatch
        "product_headphones_main" -> R.drawable.product_headphones_main
        "product_keyboard" -> R.drawable.product_keyboard
        "product_serum" -> R.drawable.product_serum
        "product_tumbler" -> R.drawable.product_tumbler
        "product_lamp" -> R.drawable.product_lamp
        "product_case_peach" -> R.drawable.product_case_peach
        "banner_sneakers" -> R.drawable.banner_sneakers
        "headphones_lifestyle" -> R.drawable.headphones_lifestyle
        "headphones_detail" -> R.drawable.headphones_detail
        "headphones_case" -> R.drawable.headphones_case
        "user_avatar" -> R.drawable.user_avatar
        else -> R.drawable.product_earbuds // Default fallback
    }

    // =============================================================================
    // BADGE ICON TYPE MAPPING
    // =============================================================================

    private fun badgeIconTypeFromString(type: String): BadgeIconType = when (type.uppercase()) {
        "SHIPPING" -> BadgeIconType.SHIPPING
        "WARRANTY" -> BadgeIconType.WARRANTY
        "VERIFIED" -> BadgeIconType.VERIFIED
        else -> BadgeIconType.SHIPPING
    }

    // =============================================================================
    // PRODUCT MAPPING
    // =============================================================================

    fun toProduct(firestoreProduct: FirestoreProduct): Product = Product(
        id = firestoreProduct.id,
        title = firestoreProduct.title,
        originalPrice = firestoreProduct.originalPrice,
        currentPrice = firestoreProduct.currentPrice,
        currency = firestoreProduct.currency,
        discountPercent = firestoreProduct.discountPercent,
        rating = firestoreProduct.rating,
        reviewCount = firestoreProduct.reviewCount,
        imageRes = drawableResFromName(firestoreProduct.imageResName),
        badgeText = firestoreProduct.badgeText,
        stockProgress = firestoreProduct.stockProgress,
        stockRemainingText = firestoreProduct.stockRemainingText,
        stockClaimedText = firestoreProduct.stockClaimedText,
        isFavorite = firestoreProduct.isFavorite,
        deliveryBadge = firestoreProduct.deliveryBadge,
        deliveryBadgeIconType = badgeIconTypeFromString(firestoreProduct.deliveryBadgeIconType)
    )

    fun toFirestoreProduct(product: Product): FirestoreProduct = FirestoreProduct(
        id = product.id,
        title = product.title,
        originalPrice = product.originalPrice,
        currentPrice = product.currentPrice,
        currency = product.currency,
        discountPercent = product.discountPercent,
        rating = product.rating,
        reviewCount = product.reviewCount,
        imageResName = getResourceNameFromRes(product.imageRes),
        badgeText = product.badgeText,
        stockProgress = product.stockProgress,
        stockRemainingText = product.stockRemainingText,
        stockClaimedText = product.stockClaimedText,
        isFavorite = product.isFavorite,
        deliveryBadge = product.deliveryBadge,
        deliveryBadgeIconType = product.deliveryBadgeIconType.name
    )

    // =============================================================================
    // CATEGORY MAPPING
    // =============================================================================

    fun toCategory(firestoreCategory: FirestoreCategory): Category = Category(
        id = firestoreCategory.id,
        name = firestoreCategory.name,
        iconVector = iconFromName(firestoreCategory.iconName),
        iconColor = colorFromHex(firestoreCategory.iconColorHex),
        containerColor = colorFromHex(firestoreCategory.containerColorHex)
    )

    // =============================================================================
    // CATEGORY COLLECTION MAPPING
    // =============================================================================

    fun toCategoryCollection(firestoreCollection: FirestoreCategoryCollection): CategoryCollection = CategoryCollection(
        id = firestoreCollection.id,
        name = firestoreCollection.name,
        iconVector = iconFromName(firestoreCollection.iconName),
        bannerTag = firestoreCollection.bannerTag,
        bannerTitle = firestoreCollection.bannerTitle,
        bannerSubtitle = firestoreCollection.bannerSubtitle,
        discountText = firestoreCollection.discountText,
        subcategories = firestoreCollection.subcategories.map { toSubCategory(it) },
        products = firestoreCollection.products.map { toProduct(it) }
    )

    fun toSubCategory(firestoreSubCategory: FirestoreSubCategory): SubCategory = SubCategory(
        id = firestoreSubCategory.id,
        name = firestoreSubCategory.name,
        itemCount = firestoreSubCategory.itemCount,
        iconVector = iconFromName(firestoreSubCategory.iconName)
    )

    // =============================================================================
    // PROMO BANNER MAPPING
    // =============================================================================

    fun toPromoBanner(firestoreBanner: FirestorePromoBanner): PromoBanner = PromoBanner(
        id = firestoreBanner.id,
        tag = firestoreBanner.tag,
        title = firestoreBanner.title,
        promoCode = firestoreBanner.promoCode,
        remainingSeconds = firestoreBanner.remainingSeconds
    )

    // =============================================================================
    // TRUST BADGE MAPPING
    // =============================================================================

    fun toTrustBadge(firestoreBadge: FirestoreTrustBadge): TrustBadge = TrustBadge(
        id = firestoreBadge.id,
        label = firestoreBadge.label,
        iconVector = iconFromName(firestoreBadge.iconName),
        tint = colorFromHex(firestoreBadge.tintHex)
    )

    // =============================================================================
    // EDITORIAL BANNER MAPPING
    // =============================================================================

    fun toEditorialBanner(firestoreBanner: FirestoreEditorialBanner): EditorialBanner = EditorialBanner(
        id = firestoreBanner.id,
        tag = firestoreBanner.tag,
        title = firestoreBanner.title,
        subtitle = firestoreBanner.subtitle,
        imageRes = drawableResFromName(firestoreBanner.imageResName)
    )

    // =============================================================================
    // PRODUCT DETAIL MAPPING
    // =============================================================================

    fun toProductDetail(firestoreDetail: FirestoreProductDetail): ProductDetail = ProductDetail(
        id = firestoreDetail.id,
        brand = firestoreDetail.brand,
        sku = firestoreDetail.sku,
        title = firestoreDetail.title,
        subtitle = firestoreDetail.subtitle,
        originalPrice = firestoreDetail.originalPrice,
        currentPrice = firestoreDetail.currentPrice,
        discountPercent = firestoreDetail.discountPercent,
        rating = firestoreDetail.rating,
        reviewCount = firestoreDetail.reviewCount,
        satisfactionPercent = firestoreDetail.satisfactionPercent,
        galleryImages = firestoreDetail.galleryImageResNames.map { drawableResFromName(it) },
        colors = firestoreDetail.colors.map { toColorVariant(it) },
        highlights = firestoreDetail.highlights.map { toProductHighlight(it) },
        reviewsSummary = toReviewSummary(firestoreDetail.reviewsSummary),
        featuredReview = toUserReview(firestoreDetail.featuredReview)
    )

    fun toColorVariant(firestoreVariant: FirestoreColorVariant): ColorVariant = ColorVariant(
        id = firestoreVariant.id,
        name = firestoreVariant.name,
        color = colorFromHex(firestoreVariant.colorHex)
    )

    fun toProductHighlight(firestoreHighlight: FirestoreProductHighlight): ProductHighlight = ProductHighlight(
        iconVector = iconFromName(firestoreHighlight.iconName),
        title = firestoreHighlight.title,
        subtitle = firestoreHighlight.subtitle
    )

    fun toReviewSummary(firestoreSummary: FirestoreReviewSummary): ReviewSummary = ReviewSummary(
        averageRating = firestoreSummary.averageRating,
        totalReviews = firestoreSummary.totalReviews,
        satisfactionPercent = firestoreSummary.satisfactionPercent,
        star5Percent = firestoreSummary.star5Percent,
        star4Percent = firestoreSummary.star4Percent,
        star3Percent = firestoreSummary.star3Percent
    )

    fun toUserReview(firestoreReview: FirestoreUserReview): UserReview = UserReview(
        id = firestoreReview.id,
        reviewerName = firestoreReview.reviewerName,
        initials = firestoreReview.initials,
        isVerified = firestoreReview.isVerified,
        dateText = firestoreReview.dateText,
        comment = firestoreReview.comment,
        purchasedVariant = firestoreReview.purchasedVariant,
        upvotes = firestoreReview.upvotes,
        downvotes = firestoreReview.downvotes
    )

    // =============================================================================
    // CART ITEM MAPPING
    // =============================================================================

    fun toCartItem(firestoreItem: FirestoreCartItem): CartItem = CartItem(
        id = firestoreItem.id,
        productId = firestoreItem.productId,
        title = firestoreItem.title,
        imageRes = drawableResFromName(firestoreItem.imageResName),
        colorName = firestoreItem.colorName,
        colorHex = colorFromHex(firestoreItem.colorHex),
        tagText = firestoreItem.tagText,
        isTagPrimary = firestoreItem.isTagPrimary,
        originalPrice = firestoreItem.originalPrice,
        currentPrice = firestoreItem.currentPrice,
        quantity = firestoreItem.quantity
    )

    fun toFirestoreCartItem(cartItem: CartItem): FirestoreCartItem = FirestoreCartItem(
        id = cartItem.id,
        productId = cartItem.productId,
        title = cartItem.title,
        imageResName = getResourceNameFromRes(cartItem.imageRes),
        colorName = cartItem.colorName,
        colorHex = cartItem.colorHex.toString(),
        tagText = cartItem.tagText,
        isTagPrimary = cartItem.isTagPrimary,
        originalPrice = cartItem.originalPrice,
        currentPrice = cartItem.currentPrice,
        quantity = cartItem.quantity
    )

    // =============================================================================
    // USER PROFILE MAPPING
    // =============================================================================

    fun toUserProfile(firestoreProfile: FirestoreUserProfile): UserProfile = UserProfile(
        name = firestoreProfile.name,
        email = firestoreProfile.email,
        phone = firestoreProfile.phone,
        avatarRes = drawableResFromName(firestoreProfile.avatarResName),
        isVerified = firestoreProfile.isVerified,
        memberTier = firestoreProfile.memberTier,
        walletBalance = firestoreProfile.walletBalance,
        rewardPoints = firestoreProfile.rewardPoints,
        activeCouponsCount = firestoreProfile.activeCouponsCount
    )

    // =============================================================================
    // ORDER STATUS MAPPING
    // =============================================================================

    fun toOrderStatusCount(firestoreStatus: FirestoreOrderStatusCount): OrderStatusCount = OrderStatusCount(
        processingCount = firestoreStatus.processingCount,
        deliveredCount = firestoreStatus.deliveredCount,
        cancelledCount = firestoreStatus.cancelledCount,
        returnedCount = firestoreStatus.returnedCount
    )

    // =============================================================================
    // ACTIVE DELIVERY MAPPING
    // =============================================================================

    fun toActiveDelivery(firestoreDelivery: FirestoreActiveDelivery): ActiveDelivery = ActiveDelivery(
        orderId = firestoreDelivery.orderId,
        statusText = firestoreDelivery.statusText,
        subtext = firestoreDelivery.subtext
    )

    // =============================================================================
    // PROFILE MENU ITEM MAPPING
    // =============================================================================

    fun toProfileMenuItem(firestoreItem: FirestoreProfileMenuItem): ProfileMenuItem = ProfileMenuItem(
        id = firestoreItem.id,
        title = firestoreItem.title,
        subtitle = firestoreItem.subtitle,
        iconVector = iconFromName(firestoreItem.iconName),
        badgeText = firestoreItem.badgeText,
        isHighlightedBadge = firestoreItem.isHighlightedBadge
    )

    // =============================================================================
    // ADDRESS MAPPING
    // =============================================================================

    fun toAddress(firestoreAddress: FirestoreAddress): Address = Address(
        id = firestoreAddress.id,
        title = firestoreAddress.title,
        fullName = firestoreAddress.fullName,
        streetAddress = firestoreAddress.streetAddress,
        city = firestoreAddress.city,
        state = firestoreAddress.state,
        postalCode = firestoreAddress.postalCode,
        phone = firestoreAddress.phone,
        isDefault = firestoreAddress.isDefault
    )

    fun toFirestoreAddress(address: Address): FirestoreAddress = FirestoreAddress(
        id = address.id,
        title = address.title,
        fullName = address.fullName,
        streetAddress = address.streetAddress,
        city = address.city,
        state = address.state,
        postalCode = address.postalCode,
        phone = address.phone,
        isDefault = address.isDefault
    )

    // =============================================================================
    // ORDER MAPPING
    // =============================================================================

    fun toOrderItem(firestoreOrder: FirestoreOrder): OrderItem = OrderItem(
        id = firestoreOrder.id,
        orderId = firestoreOrder.orderId,
        dateText = firestoreOrder.dateText,
        totalAmount = firestoreOrder.totalAmount,
        status = firestoreOrder.status,
        itemsSummary = firestoreOrder.itemsSummary,
        shippingAddress = firestoreOrder.shippingAddress,
        paymentMethod = firestoreOrder.paymentMethod
    )

    fun toFirestoreOrder(orderItem: OrderItem): FirestoreOrder = FirestoreOrder(
        id = orderItem.id,
        orderId = orderItem.orderId,
        dateText = orderItem.dateText,
        totalAmount = orderItem.totalAmount,
        status = orderItem.status,
        itemsSummary = orderItem.itemsSummary,
        shippingAddress = orderItem.shippingAddress,
        paymentMethod = orderItem.paymentMethod
    )

    // =============================================================================
    // HELPER: Get resource name from drawable resource ID
    // =============================================================================
    
    private fun getResourceNameFromRes(@DrawableRes resId: Int): String {
        // This is a simplified mapping. In production, you might use reflection
        // or a more robust mapping system
        return when (resId) {
            R.drawable.product_earbuds -> "product_earbuds"
            R.drawable.product_smartwatch -> "product_smartwatch"
            R.drawable.product_headphones_main -> "product_headphones_main"
            R.drawable.product_keyboard -> "product_keyboard"
            R.drawable.product_serum -> "product_serum"
            R.drawable.product_tumbler -> "product_tumbler"
            R.drawable.product_lamp -> "product_lamp"
            R.drawable.product_case_peach -> "product_case_peach"
            R.drawable.banner_sneakers -> "banner_sneakers"
            R.drawable.headphones_lifestyle -> "headphones_lifestyle"
            R.drawable.headphones_detail -> "headphones_detail"
            R.drawable.headphones_case -> "headphones_case"
            R.drawable.user_avatar -> "user_avatar"
            else -> "product_earbuds"
        }
    }
}
