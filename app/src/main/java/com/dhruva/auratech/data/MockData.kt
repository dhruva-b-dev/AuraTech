package com.dhruva.auratech.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.NoiseAware
import androidx.compose.material.icons.outlined.Security
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
        tag = "Autumn Festival",
        title = "30% Mega Sale",
        promoCode = "FALL1403",
        remainingSeconds = 12 * 3600 + 44 * 60 + 18 // 12h 44m 18s
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
            title = "Pro Sound Wireless Headphones 2024",
            originalPrice = 109.99,
            currentPrice = 59.99,
            discountPercent = 45,
            imageRes = R.drawable.product_earbuds,
            badgeText = "45% OFF",
            stockProgress = 0.80f,
            stockRemainingText = "Only 4 left",
            stockClaimedText = "80% claimed"
        ),
        Product(
            id = "deal_2",
            title = "Ultra Leather Smartwatch Pro",
            originalPrice = 165.00,
            currentPrice = 118.80,
            discountPercent = 28,
            imageRes = R.drawable.product_smartwatch,
            badgeText = "28% OFF",
            stockProgress = 0.60f,
            stockRemainingText = "7 remaining",
            stockClaimedText = "60% claimed"
        ),
        Product(
            id = "deal_3",
            title = "Pro Sound Max ANC Headphones",
            originalPrice = 249.99,
            currentPrice = 149.99,
            discountPercent = 40,
            imageRes = R.drawable.product_headphones_main,
            badgeText = "40% OFF",
            stockProgress = 0.75f,
            stockRemainingText = "Only 3 left",
            stockClaimedText = "75% claimed"
        )
    )

    val editorialBanners = listOf(
        EditorialBanner(
            id = "ed_fashion",
            tag = "Autumn Style",
            title = "Daily Fashion",
            subtitle = "Up to 40% Off",
            imageRes = R.drawable.headphones_lifestyle
        ),
        EditorialBanner(
            id = "ed_shoes",
            tag = "Comfort Shoes",
            title = "Comfort Sneakers",
            subtitle = "Global Brands",
            imageRes = R.drawable.banner_sneakers
        )
    )

    val bestSellers = listOf(
        Product(
            id = "prod_mug",
            title = "Stainless Steel Travel Mug Alpha",
            originalPrice = 24.00,
            currentPrice = 18.50,
            rating = 4.8f,
            reviewCount = 124,
            imageRes = R.drawable.product_tumbler,
            deliveryBadge = "Free Shipping",
            deliveryBadgeIconType = BadgeIconType.SHIPPING
        ),
        Product(
            id = "prod_keyboard",
            title = "Retro Mechanical Wireless Keyboard",
            originalPrice = 89.00,
            currentPrice = 74.99,
            rating = 4.9f,
            reviewCount = 340,
            imageRes = R.drawable.product_keyboard,
            deliveryBadge = "18-Month Warranty",
            deliveryBadgeIconType = BadgeIconType.WARRANTY
        ),
        Product(
            id = "prod_serum",
            title = "Deep Hydrating Hyaluronic Serum",
            originalPrice = 19.99,
            currentPrice = 15.20,
            rating = 4.7f,
            reviewCount = 88,
            imageRes = R.drawable.product_serum,
            deliveryBadge = "Free Shipping",
            deliveryBadgeIconType = BadgeIconType.SHIPPING
        ),
        Product(
            id = "prod_lamp",
            title = "Modern Touch Rechargeable Bedside Lamp",
            originalPrice = 36.00,
            currentPrice = 27.99,
            rating = 4.9f,
            reviewCount = 210,
            imageRes = R.drawable.product_lamp,
            deliveryBadge = "Original Guarantee",
            deliveryBadgeIconType = BadgeIconType.VERIFIED
        )
    )

    val sampleHeadphoneDetail = ProductDetail(
        id = "deal_3",
        brand = "Sony (Sony Sound Series)",
        sku = "DK-99402",
        title = "Pro Sound Max Wireless Over-Ear ANC Headphones",
        subtitle = "Sony Pro Sound Max Flagship Wireless Audio",
        originalPrice = 295.00,
        currentPrice = 249.99,
        discountPercent = 15,
        rating = 4.8f,
        reviewCount = 124,
        satisfactionPercent = 89,
        galleryImages = listOf(
            R.drawable.product_headphones_main,
            R.drawable.headphones_detail,
            R.drawable.headphones_case,
            R.drawable.headphones_lifestyle
        ),
        colors = listOf(
            ColorVariant(
                id = "color_black",
                name = "Matte Carbon Black",
                color = Color(0xFF171717)
            ),
            ColorVariant(
                id = "color_silver",
                name = "Titanium Silver",
                color = Color(0xFFCBD5E1)
            ),
            ColorVariant(
                id = "color_navy",
                name = "Midnight Navy",
                color = Color(0xFF1E1B4B)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "40hr Battery",
                subtitle = "Fast Charging"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.NoiseAware,
                title = "Active Noise Cancelling",
                subtitle = "Smart ANC"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "Water Resistant",
                subtitle = "IPX5 Standard"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.8f,
            totalReviews = 124,
            satisfactionPercent = 89,
            star5Percent = 85,
            star4Percent = 10,
            star3Percent = 5
        ),
        featuredReview = UserReview(
            id = "rev_1",
            reviewerName = "Saman M.",
            initials = "SM",
            isVerified = true,
            dateText = "3 days ago",
            comment = "The sound separation and clarity on these headphones are phenomenal. The active noise cancellation completely cuts out subway noise and open-office chatter. The ear pads are plush enough for 3+ hours of non-fatiguing wear, and the battery life easily matches claims.",
            purchasedVariant = "Matte Carbon Black",
            upvotes = 22,
            downvotes = 1
        )
    )

    val sampleSmartwatchDetail = ProductDetail(
        id = "deal_2",
        brand = "FitPro Smart Tech",
        sku = "SW-8820",
        title = "Ultra Leather Smartwatch Pro",
        subtitle = "Precision AMOLED Health Tracker & Real-time GPS",
        originalPrice = 165.00,
        currentPrice = 118.80,
        discountPercent = 28,
        rating = 4.7f,
        reviewCount = 89,
        satisfactionPercent = 92,
        galleryImages = listOf(
            R.drawable.product_smartwatch
        ),
        colors = listOf(
            ColorVariant(
                id = "color_graphite",
                name = "Graphite Silver",
                color = Color(0xFFCBD5E1)
            ),
            ColorVariant(
                id = "color_obsidian",
                name = "Obsidian Black",
                color = Color(0xFF1E293B)
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
                title = "14-Day Battery",
                subtitle = "Ultra Low Power"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.NoiseAware,
                title = "Health Sensors",
                subtitle = "SpO2 & Heart Rate"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "5 ATM Waterproof",
                subtitle = "Swim-proof 50m"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.7f,
            totalReviews = 89,
            satisfactionPercent = 92,
            star5Percent = 82,
            star4Percent = 12,
            star3Percent = 6
        ),
        featuredReview = UserReview(
            id = "rev_sw",
            reviewerName = "Amir Reza K.",
            initials = "AK",
            isVerified = true,
            dateText = "1 week ago",
            comment = "The display is vibrant and crystal clear under direct sunlight. Battery lasts well over 10 days on a single charge with full heart-rate and sleep tracking enabled.",
            purchasedVariant = "Graphite Silver",
            upvotes = 18,
            downvotes = 0
        )
    )

    val sampleEarbudsDetail = ProductDetail(
        id = "deal_1",
        brand = "Pro Sound Audio",
        sku = "EB-3021",
        title = "Pro Sound Wireless Headphones 2024",
        subtitle = "True Wireless Stereo Earbuds with Dynamic Bass Boost",
        originalPrice = 109.99,
        currentPrice = 59.99,
        discountPercent = 45,
        rating = 4.6f,
        reviewCount = 92,
        satisfactionPercent = 88,
        galleryImages = listOf(
            R.drawable.product_earbuds
        ),
        colors = listOf(
            ColorVariant(
                id = "color_eb_black",
                name = "Matte Carbon Black",
                color = Color(0xFF1E293B)
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
                title = "32hr Battery",
                subtitle = "Fast USB-C Case"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.NoiseAware,
                title = "Dual-Mic ENC",
                subtitle = "Crystal Clear Calls"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "IPX5 Splashproof",
                subtitle = "Gym & Workout Safe"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.6f,
            totalReviews = 92,
            satisfactionPercent = 88,
            star5Percent = 78,
            star4Percent = 14,
            star3Percent = 8
        ),
        featuredReview = UserReview(
            id = "rev_eb",
            reviewerName = "Nima T.",
            initials = "NT",
            isVerified = true,
            dateText = "5 days ago",
            comment = "Super lightweight and snug fit. The bass response is punchy without muddying the vocals. Best value ANC earbuds in this price range.",
            purchasedVariant = "Matte Carbon Black",
            upvotes = 15,
            downvotes = 1
        )
    )

    val sampleKeyboardDetail = ProductDetail(
        id = "prod_keyboard",
        brand = "RetroCraft Labs",
        sku = "KB-770",
        title = "Retro Mechanical Wireless Keyboard",
        subtitle = "Hot-Swappable Linear Gateron Switches & PBT Keycaps",
        originalPrice = 89.00,
        currentPrice = 74.99,
        discountPercent = 16,
        rating = 4.9f,
        reviewCount = 340,
        satisfactionPercent = 96,
        galleryImages = listOf(
            R.drawable.product_keyboard
        ),
        colors = listOf(
            ColorVariant(
                id = "color_kb_mint",
                name = "Pastel Mint",
                color = Color(0xFFD1FAE5)
            ),
            ColorVariant(
                id = "color_kb_beige",
                name = "Vintage Beige",
                color = Color(0xFFFEF3C7)
            ),
            ColorVariant(
                id = "color_kb_slate",
                name = "Slate Charcoal",
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
                title = "4000mAh Battery",
                subtitle = "Up to 200 Hours"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Security,
                title = "Hot-Swappable",
                subtitle = "3-pin & 5-pin Compatible"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.9f,
            totalReviews = 340,
            satisfactionPercent = 96,
            star5Percent = 90,
            star4Percent = 7,
            star3Percent = 3
        ),
        featuredReview = UserReview(
            id = "rev_kb",
            reviewerName = "Darya S.",
            initials = "DS",
            isVerified = true,
            dateText = "2 days ago",
            comment = "The typing acoustics are pure creamy thock right out of the box! Zero stabilizer rattle and the retro pastel aesthetic elevates my desk setup.",
            purchasedVariant = "Pastel Mint",
            upvotes = 34,
            downvotes = 0
        )
    )

    val sampleMugDetail = ProductDetail(
        id = "prod_mug",
        brand = "HydroLife Co.",
        sku = "TM-450",
        title = "Stainless Steel Travel Mug Alpha",
        subtitle = "Double-Wall Vacuum Insulated Commuter Tumbler",
        originalPrice = 24.00,
        currentPrice = 18.50,
        discountPercent = 23,
        rating = 4.8f,
        reviewCount = 124,
        satisfactionPercent = 94,
        galleryImages = listOf(
            R.drawable.product_tumbler
        ),
        colors = listOf(
            ColorVariant(
                id = "color_mug_rose",
                name = "Dusty Rose",
                color = Color(0xFFFDA4AF)
            ),
            ColorVariant(
                id = "color_mug_cream",
                name = "Matte Sand",
                color = Color(0xFFF5F5F4)
            ),
            ColorVariant(
                id = "color_mug_green",
                name = "Sage Green",
                color = Color(0xFF6EE7B7)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.LocalShipping,
                title = "Thermal Lock",
                subtitle = "12h Hot / 24h Cold"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "18/8 Pro Steel",
                subtitle = "BPA Free & Food Grade"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "100% Leakproof",
                subtitle = "Twist Lock Travel Cap"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.8f,
            totalReviews = 124,
            satisfactionPercent = 94,
            star5Percent = 86,
            star4Percent = 10,
            star3Percent = 4
        ),
        featuredReview = UserReview(
            id = "rev_mug",
            reviewerName = "Maryam Z.",
            initials = "MZ",
            isVerified = true,
            dateText = "4 days ago",
            comment = "My coffee stays piping hot from 8 AM to late afternoon. The matte dusty rose finish doesn't scratch and fits standard cup holders effortlessly.",
            purchasedVariant = "Dusty Rose",
            upvotes = 19,
            downvotes = 0
        )
    )

    val sampleSerumDetail = ProductDetail(
        id = "prod_serum",
        brand = "PureGlow Derma Labs",
        sku = "SR-901",
        title = "Deep Hydrating Hyaluronic Serum",
        subtitle = "Multi-Molecular Hyaluronic Acid Moisture Complex",
        originalPrice = 19.99,
        currentPrice = 15.20,
        discountPercent = 24,
        rating = 4.7f,
        reviewCount = 88,
        satisfactionPercent = 91,
        galleryImages = listOf(
            R.drawable.product_serum
        ),
        colors = listOf(
            ColorVariant(
                id = "color_dew",
                name = "Pure Dew",
                color = Color(0xFFE0F2FE)
            ),
            ColorVariant(
                id = "color_glow",
                name = "Rose Essence",
                color = Color(0xFFFFE4E6)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "2% Multi-HA",
                subtitle = "Instant Plumping Effect"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "Derm Tested",
                subtitle = "Non-Comedogenic"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Spa,
                title = "Clean Formula",
                subtitle = "100% Fragrance Free"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.7f,
            totalReviews = 88,
            satisfactionPercent = 91,
            star5Percent = 80,
            star4Percent = 14,
            star3Percent = 6
        ),
        featuredReview = UserReview(
            id = "rev_serum",
            reviewerName = "Niloufar B.",
            initials = "NB",
            isVerified = true,
            dateText = "1 week ago",
            comment = "Leaves my skin remarkably plump and refreshed without any tacky residue. Essential part of my daily routine now.",
            purchasedVariant = "Pure Dew",
            upvotes = 21,
            downvotes = 1
        )
    )

    val sampleLampDetail = ProductDetail(
        id = "prod_lamp",
        brand = "Lumina Living",
        sku = "LP-102",
        title = "Modern Touch Rechargeable Bedside Lamp",
        subtitle = "Stepless Dimmable Warm Ambient Touch Light with USB-C",
        originalPrice = 36.00,
        currentPrice = 27.99,
        discountPercent = 22,
        rating = 4.9f,
        reviewCount = 210,
        satisfactionPercent = 95,
        galleryImages = listOf(
            R.drawable.product_lamp
        ),
        colors = listOf(
            ColorVariant(
                id = "color_walnut",
                name = "Warm Walnut",
                color = Color(0xFFD97706)
            ),
            ColorVariant(
                id = "color_slate",
                name = "Nordic Slate",
                color = Color(0xFF334155)
            ),
            ColorVariant(
                id = "color_ivory",
                name = "Frosted Ivory",
                color = Color(0xFFF1F5F9)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "Type-C Battery",
                subtitle = "Up to 24h Playtime"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Devices,
                title = "Touch Sensitive",
                subtitle = "Smooth Stepless Dimmable"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "2700K Warm CRI90",
                subtitle = "Gentle on Eyes"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.9f,
            totalReviews = 210,
            satisfactionPercent = 95,
            star5Percent = 91,
            star4Percent = 6,
            star3Percent = 3
        ),
        featuredReview = UserReview(
            id = "rev_lamp",
            reviewerName = "Farhad P.",
            initials = "FP",
            isVerified = true,
            dateText = "3 days ago",
            comment = "The ambient light is warm, soothing, and easily dims with a gentle touch. Looks like a high-end designer piece on my nightstand.",
            purchasedVariant = "Warm Walnut",
            upvotes = 26,
            downvotes = 0
        )
    )

    val sampleCaseDetail = ProductDetail(
        id = "prod_case",
        brand = "ShieldGear Pro",
        sku = "CS-504",
        title = "Silicone Protective Case",
        subtitle = "Shock-Absorbing Soft-Touch Earbud Protective Case",
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
                name = "Pastel Peach",
                color = Color(0xFFFFB3B6)
            ),
            ColorVariant(
                id = "color_case_lilac",
                name = "Lilac Cloud",
                color = Color(0xFFE9D5FF)
            ),
            ColorVariant(
                id = "color_case_navy",
                name = "Midnight Navy",
                color = Color(0xFF1E1B4B)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.Security,
                title = "Military Drop Tested",
                subtitle = "360° Impact Protection"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "Anti-Fingerprint",
                subtitle = "Soft Matte Touch"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "Wireless Safe",
                subtitle = "Visible Front LED"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.5f,
            totalReviews = 54,
            satisfactionPercent = 90,
            star5Percent = 75,
            star4Percent = 18,
            star3Percent = 7
        ),
        featuredReview = UserReview(
            id = "rev_case",
            reviewerName = "Hasti R.",
            initials = "HR",
            isVerified = true,
            dateText = "6 days ago",
            comment = "Fits snugly, provides great corner protection, and the peach color looks pastel and classy.",
            purchasedVariant = "Pastel Peach",
            upvotes = 11,
            downvotes = 0
        )
    )

    val sampleSneakersDetail = ProductDetail(
        id = "prod_sneakers",
        brand = "UrbanStride",
        sku = "SN-601",
        title = "Comfort Sneakers Global Brands",
        subtitle = "Lightweight Engineered Knit Walk Trainers",
        originalPrice = 99.00,
        currentPrice = 64.00,
        discountPercent = 35,
        rating = 4.8f,
        reviewCount = 156,
        satisfactionPercent = 93,
        galleryImages = listOf(
            R.drawable.banner_sneakers
        ),
        colors = listOf(
            ColorVariant(
                id = "color_white_mesh",
                name = "Pure White",
                color = Color(0xFFF8FAFC)
            ),
            ColorVariant(
                id = "color_black_mesh",
                name = "Midnight Black",
                color = Color(0xFF0F172A)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.FitnessCenter,
                title = "CloudCushion Sole",
                subtitle = "Maximum Shock Absorption"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.Verified,
                title = "Breathable Knit",
                subtitle = "All-Day Freshness"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.LocalShipping,
                title = "Featherlight",
                subtitle = "Only 220g per Shoe"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.8f,
            totalReviews = 156,
            satisfactionPercent = 93,
            star5Percent = 85,
            star4Percent = 10,
            star3Percent = 5
        ),
        featuredReview = UserReview(
            id = "rev_snk",
            reviewerName = "Kamyar M.",
            initials = "KM",
            isVerified = true,
            dateText = "1 week ago",
            comment = "Walked miles in these around the city without the slightest foot fatigue. The insole cushioning is next level.",
            purchasedVariant = "Pure White",
            upvotes = 23,
            downvotes = 1
        )
    )

    val sampleLifestyleDetail = ProductDetail(
        id = "prod_lifestyle",
        brand = "Sony (Sony Sound Series)",
        sku = "LS-402",
        title = "Daily Lifestyle Active Edition",
        subtitle = "Premium Wireless Audio Tuned for Remote Work & Commute",
        originalPrice = 120.00,
        currentPrice = 89.00,
        discountPercent = 26,
        rating = 4.9f,
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
                name = "Titanium Silver",
                color = Color(0xFFCBD5E1)
            )
        ),
        highlights = listOf(
            ProductHighlight(
                iconVector = Icons.Outlined.BatteryChargingFull,
                title = "35hr Battery",
                subtitle = "Rapid USB-C Charge"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.NoiseAware,
                title = "Voice Pickup",
                subtitle = "Beamforming Mic Array"
            ),
            ProductHighlight(
                iconVector = Icons.Outlined.WaterDrop,
                title = "Ultra Plush Fit",
                subtitle = "Memory Foam Earcups"
            )
        ),
        reviewsSummary = ReviewSummary(
            averageRating = 4.9f,
            totalReviews = 74,
            satisfactionPercent = 95,
            star5Percent = 89,
            star4Percent = 8,
            star3Percent = 3
        ),
        featuredReview = UserReview(
            id = "rev_ls",
            reviewerName = "Soodabeh N.",
            initials = "SN",
            isVerified = true,
            dateText = "4 days ago",
            comment = "The sound signature is crisp and balanced. Super comfy headband that doesn't put pressure on glasses during long meetings.",
            purchasedVariant = "Matte Carbon",
            upvotes = 17,
            downvotes = 0
        )
    )

    fun getProductDetail(productId: String): ProductDetail {
        return when (productId) {
            "deal_3" -> sampleHeadphoneDetail
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
        name = "Sara Mohammadi",
        email = "sara@example.com",
        phone = "+1 (555) 345-6789",
        avatarRes = R.drawable.user_avatar,
        isVerified = true,
        memberTier = "Gold Member ★",
        walletBalance = 380.00,
        rewardPoints = 1450,
        activeCouponsCount = 3
    )

    val sampleOrderStatus = OrderStatusCount(
        processingCount = 1,
        deliveredCount = 24,
        cancelledCount = 2,
        returnedCount = 0
    )

    val sampleActiveDelivery = ActiveDelivery(
        orderId = "348902",
        statusText = "Arriving Tomorrow",
        subtext = "In transit from sorting facility"
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
            badgeText = "12 items"
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
            badgeText = "2 new",
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
