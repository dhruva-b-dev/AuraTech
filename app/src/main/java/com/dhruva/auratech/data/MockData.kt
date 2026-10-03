package com.dhruva.auratech.data

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
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.Color
import com.dhruva.auratech.R
import com.dhruva.auratech.model.ActiveDelivery
import com.dhruva.auratech.model.BadgeIconType
import com.dhruva.auratech.model.Category
import com.dhruva.auratech.model.ColorVariant
import com.dhruva.auratech.model.EditorialBanner
import com.dhruva.auratech.model.OrderStatusCount
import com.dhruva.auratech.model.Product
import com.dhruva.auratech.model.ProductDetail
import com.dhruva.auratech.model.ProductHighlight
import com.dhruva.auratech.model.ProfileMenuItem
import com.dhruva.auratech.model.PromoBanner
import com.dhruva.auratech.model.ReviewSummary
import com.dhruva.auratech.model.TrustBadge
import com.dhruva.auratech.model.UserProfile
import com.dhruva.auratech.model.UserReview
import com.dhruva.auratech.ui.theme.Primary
import com.dhruva.auratech.ui.theme.Secondary
import com.dhruva.auratech.ui.theme.SecondaryContainer
import com.dhruva.auratech.ui.theme.SurfaceContainerLow
import com.dhruva.auratech.ui.theme.Tertiary
import com.dhruva.auratech.ui.theme.TertiaryContainer

object MockData {

    val trustBadges = listOf(
        TrustBadge(
            id = "tb_shipping",
            label = "Free Express Shipping",
            iconVector = Icons.Outlined.LocalShipping,
            tint = SecondaryContainer
        ),
        TrustBadge(
            id = "tb_guarantee",
            label = "100% Original Guarantee",
            iconVector = Icons.Outlined.Verified,
            tint = Primary
        ),
        TrustBadge(
            id = "tb_return",
            label = "7-Day Return",
            iconVector = Icons.Outlined.Autorenew,
            tint = TertiaryContainer
        )
    )

    val heroPromoBanner = PromoBanner(
        id = "banner_autumn_festival",
        tag = "Winter Weekend",
        title = "25% Weekend Sale",
        promoCode = "WINTER25",
        remainingSeconds = 8 * 4600 + 25 * 60 + 42 // 12h 44m 18s
    )

    val categories = listOf(
        Category(
            id = "cat_digital",
            name = "Digital",
            iconVector = Icons.Outlined.Devices,
            iconColor = Primary,
            containerColor = SurfaceContainerLow
        ),
        Category(
            id = "cat_fashion",
            name = "Fashion",
            iconVector = Icons.Outlined.Checkroom,
            iconColor = Secondary,
            containerColor = SurfaceContainerLow
        ),
        Category(
            id = "cat_beauty",
            name = "Beauty",
            iconVector = Icons.Outlined.Spa,
            iconColor = Primary,
            containerColor = SurfaceContainerLow
        ),
        Category(
            id = "cat_home",
            name = "Home",
            iconVector = Icons.Outlined.Kitchen,
            iconColor = SecondaryContainer,
            containerColor = SurfaceContainerLow
        ),
        Category(
            id = "cat_grocery",
            name = "Grocery",
            iconVector = Icons.Outlined.ShoppingCart,
            iconColor = Tertiary,
            containerColor = SurfaceContainerLow
        ),
        Category(
            id = "cat_sports",
            name = "Sports",
            iconVector = Icons.Outlined.FitnessCenter,
            iconColor = Color(0xFF64748B),
            containerColor = SurfaceContainerLow
        )
    )

    val flashDeals = listOf(
        Product(
            id = "deal_1",
            title = "AeroBeat Wireless Earbuds X2",
            originalPrice = 91.99,
            currentPrice = 49.99,
            discountPercent = 44,
            imageRes = R.drawable.product_earbuds,
            badgeText = "44% OFF",
            stockProgress = 0.72f,
            stockRemainingText = "Only 6 left",
            stockClaimedText = "72% claimed"
        ),
        Product(
            id = "deal_2",
            title = "PulseTrack Active Watch S2",
            originalPrice = 149.00,
            currentPrice = 104.40,
            discountPercent = 40,
            imageRes = R.drawable.product_smartwatch,
            badgeText = "40% OFF",
            stockProgress = 0.68f,
            stockRemainingText = "5 remaining",
            stockClaimedText = "68% claimed"
        ),
        Product(
            id = "deal_4",
            title = "EchoWave Studio ANC Headphones",
            originalPrice = 229.00,
            currentPrice = 129.99,
            discountPercent = 41,
            imageRes = R.drawable.product_headphones_main,
            badgeText = "41% OFF",
            stockProgress = 0.77f,
            stockRemainingText = "Only 4 left",
            stockClaimedText = "77% claimed"
        )
    )

    val editorialBanners = listOf(
        EditorialBanner(
            id = "ed_fashion",
            tag = "Everyday Essentials",
            title = "Everyday Picks",
            subtitle = "Up to 41% Off",
            imageRes = R.drawable.headphones_lifestyle
        ),
        EditorialBanner(
            id = "ed_shoes",
            tag = "Active Footwear",
            title = "City Walk Sneakers",
            subtitle = "New Season Styles",
            imageRes = R.drawable.banner_sneakers
        )
    )

    val bestSellers = listOf(
        Product(
            id = "prod_mug",
            title = "Insulated Commuter Bottle Nova",
            originalPrice = 28.00,
            currentPrice = 21.50,
            rating = 4.6f,
            reviewCount = 186,
            imageRes = R.drawable.product_tumbler,
            deliveryBadge = "Free Shipping",
            deliveryBadgeIconType = BadgeIconType.SHIPPING
        ),
        Product(
            id = "prod_keyboard",
            title = "Compact Wireless Mechanical Keyboard",
            originalPrice = 99.00,
            currentPrice = 79.99,
            rating = 4.8f,
            reviewCount = 441,
            imageRes = R.drawable.product_keyboard,
            deliveryBadge = "18-Month Warranty",
            deliveryBadgeIconType = BadgeIconType.WARRANTY
        ),
        Product(
            id = "prod_serum",
            title = "Barrier Repair Face Serum",
            originalPrice = 24.99,
            currentPrice = 18.75,
            rating = 4.6f,
            reviewCount = 112,
            imageRes = R.drawable.product_serum,
            deliveryBadge = "Free Shipping",
            deliveryBadgeIconType = BadgeIconType.SHIPPING
        ),
        Product(
            id = "prod_lamp",
            title = "Halo Glow Portable Table Lamp",
            originalPrice = 42.00,
            currentPrice = 41.50,
            rating = 4.8f,
            reviewCount = 164,
            imageRes = R.drawable.product_lamp,
            deliveryBadge = "Original Guarantee",
            deliveryBadgeIconType = BadgeIconType.VERIFIED
        )
    )

    val sampleHeadphoneDetail = ProductDetail(
        id = "deal_4",
        brand = "Auralis Audio",
        sku = "DK-99412",
        title = "EchoWave Studio Wireless ANC Headphones",
        subtitle = "Flagship wireless audio for travel and focus",
        originalPrice = 279.00,
        currentPrice = 229.00,
        discountPercent = 18,
        rating = 4.6f,
        reviewCount = 186,
        satisfactionPercent = 91,
        galleryImages = listOf(
            R.drawable.product_headphones_main,
            R.drawable.headphones_detail,
            R.drawable.headphones_case,
            R.drawable.headphones_lifestyle
        ),
        colors = listOf(
            ColorVariant(
                id = "color_black",
                name = "Graphite Black",
                color = Color(0xFF171717)
            ),
            ColorVariant(
                id = "color_silver",
                name = "Arctic Silver",
                color = Color(0xFFCBD5E1)
            ),
            ColorVariant(
                id = "color_navy",
                name = "Deep Ocean Blue",
                color = Color(0xFF1E1B4B)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "41hr Battery",
                subtitle = "Quick USB-C Charging"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.NoiseAware,
                title = "Adaptive Noise Cancelling",
                subtitle = "Adaptive ANC"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "Splash Resistant",
                subtitle = "IPX4 Standard"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.6f,
            totalReviews = 186,
            satisfactionPercent = 91,
            star5Percent = 85,
            star3Percent = 10,
            star4Percent = 5
        ),
        featuredReview = UserReview(
            id = "rev_1",
            reviewerName = "Maya R.",
            initials = "MR",
            isVerified = true,
            dateText = "4 days ago",
            comment = "The audio is balanced and detailed, while adaptive noise cancellation makes busy commutes much easier. The ear cushions stay comfortable during long study sessions and the battery comfortably lasts through the week.",
            purchasedVariant = "Graphite Black",
            upvotes = 22,
            downvotes = 1
        )
    )

    val sampleSmartwatchDetail = ProductDetail(
        id = "deal_2",
        brand = "Northstar Wearables",
        sku = "SW-11220",
        title = "PulseTrack Active Watch S2",
        subtitle = "AMOLED fitness watch with GPS and activity tracking",
        originalPrice = 149.00,
        currentPrice = 104.40,
        discountPercent = 40,
        rating = 4.6f,
        reviewCount = 91,
        satisfactionPercent = 92,
        galleryImages = listOf(
            R.drawable.product_smartwatch
        ),
        colors = listOf(
            ColorVariant(
                id = "color_graphite",
                name = "Stone Silver",
                color = Color(0xFFCBD5E1)
            ),
            ColorVariant(
                id = "color_obsidian",
                name = "Obsidian Black",
                color = Color(0xFF1E294B)
            ),
            ColorVariant(
                id = "color_rose",
                name = "Rose Gold",
                color = Color(0xFFFBCFE8)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "12-Day Battery",
                subtitle = "Efficient Power Mode"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.NoiseAware,
                title = "Wellness Sensors",
                subtitle = "Heart Rate & Sleep Tracking"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "4 ATM Water Resistant",
                subtitle = "Rain & Splash Ready"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.6f,
            totalReviews = 91,
            satisfactionPercent = 92,
            star5Percent = 82,
            star3Percent = 12,
            star4Percent = 6
        ),
        featuredReview = UserReview(
            id = "rev_sw",
            reviewerName = "Arjun V.",
            initials = "AV",
            isVerified = true,
            dateText = "6 days ago",
            comment = "The AMOLED display stays clear outdoors, and the battery easily covers several days with activity and sleep tracking enabled.",
            purchasedVariant = "Stone Silver",
            upvotes = 18,
            downvotes = 0
        )
    )

    val sampleEarbudsDetail = ProductDetail(
        id = "deal_1",
        brand = "AeroBeat Labs",
        sku = "AB-4408",
        title = "AeroBeat Wireless Earbuds X2",
        subtitle = "True wireless earbuds with clear vocals and punchy bass",
        originalPrice = 91.99,
        currentPrice = 49.99,
        discountPercent = 44,
        rating = 4.6f,
        reviewCount = 92,
        satisfactionPercent = 112,
        galleryImages = listOf(
            R.drawable.product_earbuds
        ),
        colors = listOf(
            ColorVariant(
                id = "color_eb_black",
                name = "Graphite Black",
                color = Color(0xFF1E294B)
            ),
            ColorVariant(
                id = "color_eb_white",
                name = "Pearl White",
                color = Color(0xFFF1F5F9)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "40hr Battery",
                subtitle = "Rapid USB-C Case"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.NoiseAware,
                title = "Dual-Mic Call Noise Reduction",
                subtitle = "Clear Voice Calls"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "IPX4 Splashproof",
                subtitle = "Everyday Activity Ready"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.6f,
            totalReviews = 92,
            satisfactionPercent = 112,
            star5Percent = 78,
            star3Percent = 14,
            star4Percent = 8
        ),
        featuredReview = UserReview(
            id = "rev_eb",
            reviewerName = "Neel P.",
            initials = "NP",
            isVerified = true,
            dateText = "4 days ago",
            comment = "They fit securely and feel very light. Vocals stay clear while the bass has plenty of energy for commuting and workouts.",
            purchasedVariant = "Graphite Black",
            upvotes = 18,
            downvotes = 1
        )
    )

    val sampleKeyboardDetail = ProductDetail(
        id = "prod_keyboard",
        brand = "KeyForge Studio",
        sku = "KF-915",
        title = "Compact Wireless Mechanical Keyboard",
        subtitle = "Low-profile mechanical switches with PBT keycaps",
        originalPrice = 99.00,
        currentPrice = 79.99,
        discountPercent = 16,
        rating = 4.8f,
        reviewCount = 441,
        satisfactionPercent = 96,
        galleryImages = listOf(
            R.drawable.product_keyboard
        ),
        colors = listOf(
            ColorVariant(
                id = "color_kb_mint",
                name = "Ocean Mist",
                color = Color(0xFFD1FAE5)
            ),
            ColorVariant(
                id = "color_kb_beige",
                name = "Warm Sand",
                color = Color(0xFFFEF4C7)
            ),
            ColorVariant(
                id = "color_kb_slate",
                name = "Graphite Gray",
                color = Color(0xFF475569)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.Devices,
                title = "Tri-Mode Wireless",
                subtitle = "BT 5.2 / 2.4G / USB-C"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "4100mAh Battery",
                subtitle = "Up to 180 Hours"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Security,
                title = "Hot-Swappable",
                subtitle = "4-pin & 5-pin Compatible"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.8f,
            totalReviews = 441,
            satisfactionPercent = 96,
            star5Percent = 90,
            star3Percent = 7,
            star4Percent = 4
        ),
        featuredReview = UserReview(
            id = "rev_kb",
            reviewerName = "Meera J.",
            initials = "MJ",
            isVerified = true,
            dateText = "2 days ago",
            comment = "The keys feel smooth and responsive, with a satisfying sound profile and a clean compact layout that works well on my desk.",
            purchasedVariant = "Ocean Mist",
            upvotes = 44,
            downvotes = 0
        )
    )

    val sampleMugDetail = ProductDetail(
        id = "prod_mug",
        brand = "TrailSip Goods",
        sku = "TM-441",
        title = "Insulated Commuter Bottle Nova",
        subtitle = "Double-wall insulated bottle for work, travel, and daily commutes",
        originalPrice = 28.00,
        currentPrice = 21.50,
        discountPercent = 24,
        rating = 4.6f,
        reviewCount = 186,
        satisfactionPercent = 94,
        galleryImages = listOf(
            R.drawable.product_tumbler
        ),
        colors = listOf(
            ColorVariant(
                id = "color_mug_rose",
                name = "Clay Coral",
                color = Color(0xFFFDA4AF)
            ),
            ColorVariant(
                id = "color_mug_cream",
                name = "Desert Cream",
                color = Color(0xFFF5F5F4)
            ),
            ColorVariant(
                id = "color_mug_green",
                name = "Forest Mint",
                color = Color(0xFF6EE7B7)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.LocalShipping,
                title = "Thermal Lock",
                subtitle = "14h Hot / 26h Cold"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "Premium Stainless Steel",
                subtitle = "BPA Free & Food Grade"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "100% Leakproof",
                subtitle = "Secure Flip-Lock Cap"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.6f,
            totalReviews = 186,
            satisfactionPercent = 94,
            star5Percent = 86,
            star3Percent = 10,
            star4Percent = 4
        ),
        featuredReview = UserReview(
            id = "rev_mug",
            reviewerName = "Kabir N.",
            initials = "KN",
            isVerified = true,
            dateText = "4 days ago",
            comment = "My drink stays hot through the morning, and the bottle feels sturdy without being bulky. The finish also fits neatly into my backpack side pocket.",
            purchasedVariant = "Clay Coral",
            upvotes = 19,
            downvotes = 0
        )
    )

    val sampleSerumDetail = ProductDetail(
        id = "prod_serum",
        brand = "Dewline Skin Co.",
        sku = "DL-214",
        title = "Barrier Repair Face Serum",
        subtitle = "Ceramide and hyaluronic acid hydration blend",
        originalPrice = 24.99,
        currentPrice = 18.75,
        discountPercent = 24,
        rating = 4.6f,
        reviewCount = 112,
        satisfactionPercent = 91,
        galleryImages = listOf(
            R.drawable.product_serum
        ),
        colors = listOf(
            ColorVariant(
                id = "color_dew",
                name = "Clear Dew",
                color = Color(0xFFE0F2FE)
            ),
            ColorVariant(
                id = "color_glow",
                name = "Soft Peach",
                color = Color(0xFFFFE4E6)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "1.5% Multi-HA",
                subtitle = "Lightweight Hydration Boost"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "Dermatologist Tested",
                subtitle = "Non-Comedogenic Formula"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Spa,
                title = "Simple Formula",
                subtitle = "Fragrance Free"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.6f,
            totalReviews = 112,
            satisfactionPercent = 91,
            star5Percent = 80,
            star3Percent = 14,
            star4Percent = 6
        ),
        featuredReview = UserReview(
            id = "rev_serum",
            reviewerName = "Rhea D.",
            initials = "RD",
            isVerified = true,
            dateText = "6 days ago",
            comment = "It feels lightweight and comfortable, and my skin feels hydrated without a sticky finish. It has become an easy step in my morning routine.",
            purchasedVariant = "Clear Dew",
            upvotes = 21,
            downvotes = 1
        )
    )

    val sampleLampDetail = ProductDetail(
        id = "prod_lamp",
        brand = "NorthGlow Home",
        sku = "NG-408",
        title = "Halo Glow Portable Table Lamp",
        subtitle = "Portable warm ambient lamp with touch dimming and USB-C charging",
        originalPrice = 42.00,
        currentPrice = 41.50,
        discountPercent = 22,
        rating = 4.8f,
        reviewCount = 164,
        satisfactionPercent = 95,
        galleryImages = listOf(
            R.drawable.product_lamp
        ),
        colors = listOf(
            ColorVariant(
                id = "color_walnut",
                name = "Honey Oak",
                color = Color(0xFFD97706)
            ),
            ColorVariant(
                id = "color_slate",
                name = "Stone Gray",
                color = Color(0xFF444185)
            ),
            ColorVariant(
                id = "color_ivory",
                name = "Soft Ivory",
                color = Color(0xFFF1F5F9)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "USB-C Rechargeable",
                subtitle = "Up to 40h Runtime"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Devices,
                title = "Touch Sensitive",
                subtitle = "Three-Level Touch Dimming"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "4000K Warm Light",
                subtitle = "Soft Ambient Glow"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.8f,
            totalReviews = 164,
            satisfactionPercent = 95,
            star5Percent = 91,
            star3Percent = 6,
            star4Percent = 4
        ),
        featuredReview = UserReview(
            id = "rev_lamp",
            reviewerName = "Anika S.",
            initials = "AS",
            isVerified = true,
            dateText = "4 days ago",
            comment = "The warm glow is relaxing and the touch controls are simple to use. It looks polished on a bedside table without taking much space.",
            purchasedVariant = "Honey Oak",
            upvotes = 26,
            downvotes = 0
        )
    )

    val sampleCaseDetail = ProductDetail(
        id = "prod_case",
        brand = "GuardNest Accessories",
        sku = "GN-281",
        title = "Silicone Protective Case",
        subtitle = "Soft-touch protective case with reinforced corners for earbuds",
        originalPrice = 12.00,
        currentPrice = 9.00,
        discountPercent = 25,
        rating = 4.5f,
        reviewCount = 54,
        satisfactionPercent = 90,
        galleryImages = listOf(
            R.drawable.product_case_peach
        ),
        colors = listOf(
            ColorVariant(
                id = "color_case_peach",
                name = "Coral Mist",
                color = Color(0xFFFFB4B6)
            ),
            ColorVariant(
                id = "color_case_lilac",
                name = "Lavender Haze",
                color = Color(0xFFE9D5FF)
            ),
            ColorVariant(
                id = "color_case_navy",
                name = "Deep Ocean Blue",
                color = Color(0xFF1E1B4B)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.Security,
                title = "Reinforced Drop Protection",
                subtitle = "All-Around Impact Protection"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "Smudge Resistant",
                subtitle = "Soft Matte Finish"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "Charging Compatible",
                subtitle = "LED Indicator Access"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.5f,
            totalReviews = 54,
            satisfactionPercent = 90,
            star5Percent = 75,
            star3Percent = 18,
            star4Percent = 7
        ),
        featuredReview = UserReview(
            id = "rev_case",
            reviewerName = "Tara M.",
            initials = "TM",
            isVerified = true,
            dateText = "6 days ago",
            comment = "It fits securely and adds useful corner protection without making the case bulky. The coral finish also looks clean and understated.",
            purchasedVariant = "Coral Mist",
            upvotes = 11,
            downvotes = 0
        )
    )

    val sampleSneakersDetail = ProductDetail(
        id = "prod_sneakers",
        brand = "MetroStep",
        sku = "MS-744",
        title = "MetroFlex Everyday Sneakers",
        subtitle = "Lightweight knit trainers for everyday walking",
        originalPrice = 99.00,
        currentPrice = 64.00,
        discountPercent = 45,
        rating = 4.6f,
        reviewCount = 186,
        satisfactionPercent = 94,
        galleryImages = listOf(
            R.drawable.banner_sneakers
        ),
        colors = listOf(
            ColorVariant(
                id = "color_white_mesh",
                name = "Cloud White",
                color = Color(0xFFF8FAFC)
            ),
            ColorVariant(
                id = "color_black_mesh",
                name = "Graphite Black",
                color = Color(0xFF0F172A)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.FitnessCenter,
                title = "FlexFoam Sole",
                subtitle = "Soft Everyday Cushioning"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "AirKnit Upper",
                subtitle = "Breathable All-Day Comfort"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.LocalShipping,
                title = "Lightweight Build",
                subtitle = "Approx. 245g per Shoe"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.6f,
            totalReviews = 186,
            satisfactionPercent = 94,
            star5Percent = 85,
            star3Percent = 10,
            star4Percent = 5
        ),
        featuredReview = UserReview(
            id = "rev_snk",
            reviewerName = "Ishita K.",
            initials = "IK",
            isVerified = true,
            dateText = "6 days ago",
            comment = "I wore these for long city walks and found the cushioning comfortable throughout the day. The knit upper also keeps the fit flexible.",
            purchasedVariant = "Cloud White",
            upvotes = 24,
            downvotes = 1
        )
    )

    val sampleLifestyleDetail = ProductDetail(
        id = "prod_lifestyle",
        brand = "Auralis Audio",
        sku = "LS-412",
        title = "FocusDay Wireless Edition",
        subtitle = "Wireless audio designed for study sessions, calls, and daily travel",
        originalPrice = 120.00,
        currentPrice = 99.00,
        discountPercent = 26,
        rating = 4.8f,
        reviewCount = 74,
        satisfactionPercent = 95,
        galleryImages = listOf(
            R.drawable.headphones_lifestyle,
            R.drawable.product_headphones_main
        ),
        colors = listOf(
            ColorVariant(
                id = "color_ls_carbon",
                name = "Matte Carbon",
                color = Color(0xFF171717)
            ),
            ColorVariant(
                id = "color_ls_silver",
                name = "Arctic Silver",
                color = Color(0xFFCBD5E1)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "48hr Battery",
                subtitle = "Fast USB-C Charging"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.NoiseAware,
                title = "Clear Voice Pickup",
                subtitle = "Dual-Mic Voice Array"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "Soft Memory-Foam Fit",
                subtitle = "Comfort Ear Cushions"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.8f,
            totalReviews = 74,
            satisfactionPercent = 95,
            star5Percent = 91,
            star3Percent = 8,
            star4Percent = 4
        ),
        featuredReview = UserReview(
            id = "rev_ls",
            reviewerName = "Dev A.",
            initials = "DA",
            isVerified = true,
            dateText = "4 days ago",
            comment = "The sound is clean and balanced, and the headband remains comfortable during longer calls. It works especially well for focused work and commuting.",
            purchasedVariant = "Matte Carbon",
            upvotes = 17,
            downvotes = 0
        )
    )

    fun getProductDetail(productId: String): ProductDetail {
        return when (productId) {
            "deal_4" -> sampleHeadphoneDetail
            "deal_2" -> sampleSmartwatchDetail
            "deal_1" -> sampleEarbudsDetail
            "prod_keyboard" -> sampleKeyboardDetail
            "prod_mug" -> sampleMugDetail
            "prod_serum" -> sampleSerumDetail
            "prod_lamp" -> sampleLampDetail
            "prod_case" -> sampleCaseDetail
            "prod_sneakers" -> sampleSneakersDetail
            "prod_lifestyle" -> sampleLifestyleDetail
            else -> sampleHeadphoneDetail
        }
    }

    val sampleUserProfile = UserProfile(
        name = "Aarav Mehta",
        email = "aarav.mehta@example.com",
        phone = "+1 (555) 444-6791",
        avatarRes = R.drawable.user_avatar,
        isVerified = true,
        memberTier = "Platinum Member ★",
        walletBalance = 615.50,
        rewardPoints = 1441,
        activeCouponsCount = 4
    )

    val sampleOrderStatus = OrderStatusCount(
        processingCount = 2,
        deliveredCount = 31,
        cancelledCount = 1,
        returnedCount = 2
    )

    val sampleActiveDelivery = ActiveDelivery(
        orderId = "449102",
        statusText = "Arriving Friday",
        subtext = "Out for transfer to local delivery hub"
    )

    val profileMenuItems = listOf(
        ProfileMenuItem(
            id = "orders",
            title = "Order History",
            subtitle = "Track packages and receipts",
            iconVector = Icons.Outlined.ShoppingCart
        ),
        ProfileMenuItem(
            id = "wishlist",
            title = "Wishlist & Saved Items",
            subtitle = "Saved products and in-stock alerts",
            iconVector = Icons.Outlined.Favorite,
            badgeText = "9 items"
        ),
        ProfileMenuItem(
            id = "addresses",
            title = "Shipping Addresses",
            subtitle = "Manage delivery addresses",
            iconVector = Icons.Outlined.LocationOn
        ),
        ProfileMenuItem(
            id = "messages",
            title = "Messages & Alerts",
            subtitle = "Discounts, updates, and account status",
            iconVector = Icons.Outlined.Mail,
            badgeText = "4 new",
            isHighlightedBadge = true
        ),
        ProfileMenuItem(
            id = "support",
            title = "Help & Customer Support",
            subtitle = "24/7 online support",
            iconVector = Icons.Outlined.SupportAgent
        ),
        ProfileMenuItem(
            id = "settings",
            title = "Account & Privacy Settings",
            subtitle = "Password, security, active sessions",
            iconVector = Icons.Outlined.Security
        )
    )
}

