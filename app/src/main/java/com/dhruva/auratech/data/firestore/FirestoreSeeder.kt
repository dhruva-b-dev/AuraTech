package com.dhruva.auratech.data.firestore

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
import com.dhruva.auratech.data.MockData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.text.format

/**
 * Utility class to seed Firestore with mock data.
 * Call this once to populate your Firestore database with initial data.
 */
/*
object FirestoreSeeder {

    */
/**
     * Seeds all Firestore collections with mock data.
     * Call this from your app (e.g., in a debug screen or on first launch).
     *//*

    suspend fun seedAllData() = coroutineScope {
        try {
            println("🌱 Starting Firestore seeding...")

            // Seed all collections in parallel
            awaitAll(
                async { seedTrustBadges() },
                async { seedPromoBanners() },
                async { seedEditorialBanners() },
                async { seedCategories() },
                async { seedProducts() },
                async { seedProductDetails() },
                async { seedProfileMenuItems() }
            )

            println("✅ Firestore seeding completed successfully!")
        } catch (e: Exception) {
            println("❌ Firestore seeding failed: ${e.message}")
            e.printStackTrace()
        }
    }

    */
/**
     * Seeds trust badges collection
     *//*

    private suspend fun seedTrustBadges() {
        println("Seeding trust badges...")
        MockData.trustBadges.forEach { badge ->
            val firestoreBadge = com.dhruva.auratech.data.firestore.FirestoreTrustBadge(
                id = badge.id,
                label = badge.label,
                iconName = getIconName(badge.iconVector),
                tintHex = colorToHex(badge.tint)
            )
            FirestoreRepository.addTrustBadge(firestoreBadge)
        }
        println("✅ Trust badges seeded: ${MockData.trustBadges.size} items")
    }

    */
/**
     * Seeds promo banners collection
     *//*

    private suspend fun seedPromoBanners() {
        println("Seeding promo banners...")
        val heroBanner = com.dhruva.auratech.data.firestore.FirestorePromoBanner(
            id = MockData.heroPromoBanner.id,
            tag = MockData.heroPromoBanner.tag,
            title = MockData.heroPromoBanner.title,
            promoCode = MockData.heroPromoBanner.promoCode,
            remainingSeconds = MockData.heroPromoBanner.remainingSeconds
        )
        FirestoreRepository.addPromoBanner(heroBanner)
        println("✅ Promo banners seeded: 1 item")
    }

    */
/**
     * Seeds editorial banners collection
     *//*

    private suspend fun seedEditorialBanners() {
        println("Seeding editorial banners...")
        MockData.editorialBanners.forEach { banner ->
            val firestoreBanner = com.dhruva.auratech.data.firestore.FirestoreEditorialBanner(
                id = banner.id,
                tag = banner.tag,
                title = banner.title,
                subtitle = banner.subtitle,
                imageResName = getResourceNameFromRes(banner.imageRes)
            )
            FirestoreRepository.addEditorialBanner(firestoreBanner)
        }
        println("✅ Editorial banners seeded: ${MockData.editorialBanners.size} items")
    }

    */
/**
     * Seeds categories collection
     *//*

    private suspend fun seedCategories() {
        println("Seeding categories...")
        MockData.categories.forEach { category ->
            val firestoreCategory = com.dhruva.auratech.data.firestore.FirestoreCategory(
                id = category.id,
                name = category.name,
                iconName = getIconName(category.iconVector),
                iconColorHex = colorToHex(category.iconColor),
                containerColorHex = colorToHex(category.containerColor)
            )
            FirestoreRepository.addCategory(firestoreCategory)
        }
        println("✅ Categories seeded: ${MockData.categories.size} items")
    }

    */
/**
     * Seeds products collection
     *//*

    private suspend fun seedProducts() {
        println("Seeding products...")
        val allProducts = MockData.flashDeals + MockData.bestSellers
        allProducts.forEach { product ->
            val firestoreProduct = FirestoreModelMapper.toFirestoreProduct(product)
            FirestoreRepository.addProduct(firestoreProduct)
        }
        println("✅ Products seeded: ${allProducts.size} items")
    }

    */
/**
     * Seeds product details collection
     *//*

    private suspend fun seedProductDetails() {
        println("Seeding product details...")
        val productDetails = listOf(
            MockData.sampleHeadphoneDetail,
            MockData.sampleSmartwatchDetail,
            MockData.sampleEarbudsDetail,
            MockData.sampleKeyboardDetail,
            MockData.sampleMugDetail,
            MockData.sampleSerumDetail,
            MockData.sampleLampDetail,
            MockData.sampleCaseDetail,
            MockData.sampleSneakersDetail,
            MockData.sampleLifestyleDetail
        )
        
        productDetails.forEach { detail ->
            val firestoreDetail = com.dhruva.auratech.data.firestore.FirestoreProductDetail(
                id = detail.id,
                brand = detail.brand,
                sku = detail.sku,
                title = detail.title,
                subtitle = detail.subtitle,
                originalPrice = detail.originalPrice,
                currentPrice = detail.currentPrice,
                discountPercent = detail.discountPercent,
                rating = detail.rating,
                reviewCount = detail.reviewCount,
                satisfactionPercent = detail.satisfactionPercent,
                galleryImageResNames = detail.galleryImages.map { getResourceNameFromRes(it) },
                colors = detail.colors.map { 
                    com.dhruva.auratech.data.firestore.FirestoreColorVariant(
                        id = it.id,
                        name = it.name,
                        colorHex = colorToHex(it.color)
                    )
                },
                highlights = detail.highlights.map {
                    com.dhruva.auratech.data.firestore.FirestoreProductHighlight(
                        iconName = getIconName(it.iconVector),
                        title = it.title,
                        subtitle = it.subtitle
                    )
                },
                reviewsSummary = com.dhruva.auratech.data.firestore.FirestoreReviewSummary(
                    averageRating = detail.reviewsSummary.averageRating,
                    totalReviews = detail.reviewsSummary.totalReviews,
                    satisfactionPercent = detail.reviewsSummary.satisfactionPercent,
                    star5Percent = detail.reviewsSummary.star5Percent,
                    star4Percent = detail.reviewsSummary.star4Percent,
                    star3Percent = detail.reviewsSummary.star3Percent
                ),
                featuredReview = com.dhruva.auratech.data.firestore.FirestoreUserReview(
                    id = detail.featuredReview.id,
                    reviewerName = detail.featuredReview.reviewerName,
                    initials = detail.featuredReview.initials,
                    isVerified = detail.featuredReview.isVerified,
                    dateText = detail.featuredReview.dateText,
                    comment = detail.featuredReview.comment,
                    purchasedVariant = detail.featuredReview.purchasedVariant,
                    upvotes = detail.featuredReview.upvotes,
                    downvotes = detail.featuredReview.downvotes
                )
            )
            FirestoreRepository.addProductDetail(firestoreDetail)
        }
        println("✅ Product details seeded: ${productDetails.size} items")
    }

    */
/**
     * Seeds profile menu items collection
     *//*

    private suspend fun seedProfileMenuItems() {
        println("Seeding profile menu items...")
        MockData.profileMenuItems.forEach { item ->
            val firestoreItem = com.dhruva.auratech.data.firestore.FirestoreProfileMenuItem(
                id = item.id,
                title = item.title,
                subtitle = item.subtitle,
                iconName = getIconName(item.iconVector),
                badgeText = item.badgeText,
                isHighlightedBadge = item.isHighlightedBadge
            )
            FirestoreRepository.addProfileMenuItem(firestoreItem)
        }
        println("✅ Profile menu items seeded: ${MockData.profileMenuItems.size} items")
    }

    */
/**
     * Seeds user-specific data (profile, cart, orders) for a test user
     *//*

    suspend fun seedUserData(userId: String = "test_user_123") = coroutineScope {
        try {
            println("🌱 Seeding user data for: $userId")

            awaitAll(
                async { seedUserProfile(userId) },
                async { seedUserCart(userId) },
                async { seedUserOrderStatus(userId) },
                async { seedUserActiveDelivery(userId) }
            )

            println("✅ User data seeded successfully!")
        } catch (e: Exception) {
            println("❌ User data seeding failed: ${e.message}")
            e.printStackTrace()
        }
    }

    private suspend fun seedUserProfile(userId: String) {
        println("Seeding user profile...")
        val firestoreProfile = com.dhruva.auratech.data.firestore.FirestoreUserProfile(
            userId = userId,
            name = MockData.sampleUserProfile.name,
            email = MockData.sampleUserProfile.email,
            phone = MockData.sampleUserProfile.phone,
            avatarResName = getResourceNameFromRes(MockData.sampleUserProfile.avatarRes),
            isVerified = MockData.sampleUserProfile.isVerified,
            memberTier = MockData.sampleUserProfile.memberTier,
            walletBalance = MockData.sampleUserProfile.walletBalance,
            rewardPoints = MockData.sampleUserProfile.rewardPoints,
            activeCouponsCount = MockData.sampleUserProfile.activeCouponsCount
        )
        FirestoreRepository.createUserProfile(firestoreProfile)
        println("✅ User profile seeded")
    }

    private suspend fun seedUserCart(userId: String) {
        println("Seeding user cart...")
        val cartItems = listOf(
            com.dhruva.auratech.data.firestore.FirestoreCartItem(
                id = "item-1",
                productId = "deal_3",
                title = "Pro Sound Wireless Headphones",
                imageResName = "product_headphones_main",
                colorName = "Color: Matte Black",
                colorHex = "#1E293B",
                tagText = "18-month warranty",
                isTagPrimary = false,
                originalPrice = 70.00,
                currentPrice = 59.00,
                quantity = 1
            ),
            com.dhruva.auratech.data.firestore.FirestoreCartItem(
                id = "item-2",
                productId = "deal_2",
                title = "Smart Watch Fit Pro",
                imageResName = "product_smartwatch",
                colorName = "Strap: Graphite Silver",
                colorHex = "#CBD5E1",
                tagText = "Best Seller",
                isTagPrimary = true,
                originalPrice = 55.00,
                currentPrice = 45.00,
                quantity = 2
            ),
            com.dhruva.auratech.data.firestore.FirestoreCartItem(
                id = "item-3",
                productId = "prod_case",
                title = "Silicone Protective Case",
                imageResName = "product_case_peach",
                colorName = "Color: Pastel Peach",
                colorHex = "#FFB3B6",
                tagText = "Shockproof",
                isTagPrimary = false,
                originalPrice = 12.00,
                currentPrice = 9.00,
                quantity = 1
            )
        )
        cartItems.forEach { item ->
            FirestoreRepository.addCartItem(userId, item)
        }
        println("✅ User cart seeded: ${cartItems.size} items")
    }

    private suspend fun seedUserOrderStatus(userId: String) {
        println("Seeding user order status...")
        val firestoreStatus = com.dhruva.auratech.data.firestore.FirestoreOrderStatusCount(
            userId = userId,
            processingCount = MockData.sampleOrderStatus.processingCount,
            deliveredCount = MockData.sampleOrderStatus.deliveredCount,
            cancelledCount = MockData.sampleOrderStatus.cancelledCount,
            returnedCount = MockData.sampleOrderStatus.returnedCount
        )
        FirestoreRepository.addOrderStatus(userId, firestoreStatus)
        println("✅ User order status seeded")
    }

    private suspend fun seedUserActiveDelivery(userId: String) {
        println("Seeding user active delivery...")
        val firestoreDelivery = com.dhruva.auratech.data.firestore.FirestoreActiveDelivery(
            userId = userId,
            orderId = MockData.sampleActiveDelivery.orderId,
            statusText = MockData.sampleActiveDelivery.statusText,
            subtext = MockData.sampleActiveDelivery.subtext
        )
        FirestoreRepository.addActiveDelivery(userId, firestoreDelivery)
        println("✅ User active delivery seeded")
    }

    // =============================================================================
    // HELPER METHODS
    // =============================================================================

    private fun getIconName(iconVector: androidx.compose.ui.graphics.vector.ImageVector): String {
        // Simplified mapping - in production, use a more robust system
        return when (iconVector) {
            androidx.compose.material.icons.Icons.Outlined.Devices -> "Devices"
            androidx.compose.material.icons.Icons.Outlined.Checkroom -> "Checkroom"
            androidx.compose.material.icons.Icons.Outlined.Spa -> "Spa"
            androidx.compose.material.icons.Icons.Outlined.Kitchen -> "Kitchen"
            androidx.compose.material.icons.Icons.Outlined.ShoppingCart -> "ShoppingCart"
            androidx.compose.material.icons.Icons.Outlined.FitnessCenter -> "FitnessCenter"
            androidx.compose.material.icons.Icons.Outlined.LocalShipping -> "LocalShipping"
            androidx.compose.material.icons.Icons.Outlined.Verified -> "Verified"
            androidx.compose.material.icons.Icons.Outlined.Autorenew -> "Autorenew"
            androidx.compose.material.icons.Icons.Outlined.BatteryChargingFull -> "BatteryChargingFull"
            androidx.compose.material.icons.Icons.Outlined.NoiseAware -> "NoiseAware"
            androidx.compose.material.icons.Icons.Outlined.WaterDrop -> "WaterDrop"
            androidx.compose.material.icons.Icons.Outlined.Security -> "Security"
            androidx.compose.material.icons.Icons.Outlined.LocationOn -> "LocationOn"
            androidx.compose.material.icons.Icons.Outlined.Mail -> "Mail"
            androidx.compose.material.icons.Icons.Outlined.SupportAgent -> "SupportAgent"
            androidx.compose.material.icons.Icons.Outlined.Favorite -> "Favorite"
            androidx.compose.material.icons.Icons.Outlined.Tune -> "Tune"
            androidx.compose.material.icons.Icons.Outlined.ShoppingBag -> "ShoppingBag"
            else -> "ShoppingCart"
        }
    }

    private fun colorToHex(color: androidx.compose.ui.graphics.Color): String {
        return String.format("#%06X", (0xFFFFFF and color.value.toInt()))
    }

    private fun getResourceNameFromRes(@androidx.annotation.DrawableRes resId: Int): String {
        return when (resId) {
            com.dhruva.auratech.R.drawable.product_earbuds -> "product_earbuds"
            com.dhruva.auratech.R.drawable.product_smartwatch -> "product_smartwatch"
            com.dhruva.auratech.R.drawable.product_headphones_main -> "product_headphones_main"
            com.dhruva.auratech.R.drawable.product_keyboard -> "product_keyboard"
            com.dhruva.auratech.R.drawable.product_serum -> "product_serum"
            com.dhruva.auratech.R.drawable.product_tumbler -> "product_tumbler"
            com.dhruva.auratech.R.drawable.product_lamp -> "product_lamp"
            com.dhruva.auratech.R.drawable.product_case_peach -> "product_case_peach"
            com.dhruva.auratech.R.drawable.banner_sneakers -> "banner_sneakers"
            com.dhruva.auratech.R.drawable.headphones_lifestyle -> "headphones_lifestyle"
            com.dhruva.auratech.R.drawable.headphones_detail -> "headphones_detail"
            com.dhruva.auratech.R.drawable.headphones_case -> "headphones_case"
            com.dhruva.auratech.R.drawable.user_avatar -> "user_avatar"
            else -> "product_earbuds"
        }
    }
}
*/
