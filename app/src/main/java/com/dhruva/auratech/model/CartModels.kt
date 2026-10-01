package com.dhruva.auratech.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

/**
 * Data model representing an item in the user's shopping cart.
 */
data class CartItem(
    val id: String,
    val productId: String,
    val title: String,
    @param:DrawableRes val imageRes: Int,
    val colorName: String,
    val colorHex: Color,
    val tagText: String,
    val isTagPrimary: Boolean = false,
    val originalPrice: Double,
    val currentPrice: Double,
    val quantity: Int = 1
)
