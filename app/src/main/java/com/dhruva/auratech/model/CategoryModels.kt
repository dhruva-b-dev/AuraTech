package com.dhruva.auratech.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Subcategory item with count and icon.
 */
data class SubCategory(
    val id: String,
    val name: String,
    val itemCount: Int,
    val iconVector: ImageVector
)

/**
 * Category collection linking master category info, promo banner details,
 * subcategories, and associated product catalog.
 */
data class CategoryCollection(
    val id: String,
    val name: String,
    val iconVector: ImageVector,
    val bannerTag: String,
    val bannerTitle: String,
    val bannerSubtitle: String,
    val discountText: String,
    val subcategories: List<SubCategory>,
    val products: List<Product>
)
