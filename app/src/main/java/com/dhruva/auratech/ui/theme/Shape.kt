package com.dhruva.auratech.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp), // For buttons and smaller components
    medium = RoundedCornerShape(12.dp), // For the large cards and containers
    large = RoundedCornerShape(16.dp) // For possible dialogues or bottom sheets
)