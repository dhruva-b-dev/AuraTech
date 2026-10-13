package com.dhruva.auratech.data.firestore

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FirestoreModelMapperTest {

    @Test
    fun `toCartItem maps FirestoreCartItem correctly`() {
        val firestoreCartItem = FirestoreCartItem(
            id = "cart_1",
            productId = "p1",
            title = "AURA Headphones",
            imageResName = "product_headphones_main",
            colorName = "Matte Black",
            colorHex = "#1E293B",
            tagText = "Best Seller",
            isTagPrimary = true,
            originalPrice = 100.0,
            currentPrice = 80.0,
            quantity = 2
        )

        val cartItem = FirestoreModelMapper.toCartItem(firestoreCartItem)

        assertEquals("cart_1", cartItem.id)
        assertEquals("p1", cartItem.productId)
        assertEquals("AURA Headphones", cartItem.title)
        assertEquals(80.0, cartItem.currentPrice, 0.001)
        assertEquals(100.0, cartItem.originalPrice, 0.001)
        assertEquals(2, cartItem.quantity)
        assertTrue(cartItem.isTagPrimary)
    }

    @Test
    fun `toAddress maps FirestoreAddress correctly`() {
        val firestoreAddress = FirestoreAddress(
            id = "addr_1",
            title = "Home",
            fullName = "John Doe",
            streetAddress = "123 Tech Lane",
            city = "San Francisco",
            state = "CA",
            postalCode = "94105",
            phone = "+1 555-0199",
            isDefault = true
        )

        val address = FirestoreModelMapper.toAddress(firestoreAddress)

        assertEquals("addr_1", address.id)
        assertEquals("Home", address.title)
        assertEquals("John Doe", address.fullName)
        assertEquals("123 Tech Lane", address.streetAddress)
        assertEquals("San Francisco", address.city)
        assertEquals("CA", address.state)
        assertEquals("94105", address.postalCode)
        assertTrue(address.isDefault)
    }

    @Test
    fun `toProduct maps FirestoreProduct correctly`() {
        val firestoreProduct = FirestoreProduct(
            id = "prod_100",
            title = "Cyber Keyboard",
            badgeText = "NEW",
            imageResName = "product_keyboard",
            rating = 4.9f,
            reviewCount = 128,
            currentPrice = 149.0,
            originalPrice = 179.0,
            isFavorite = true
        )

        val product = FirestoreModelMapper.toProduct(firestoreProduct)

        assertEquals("prod_100", product.id)
        assertEquals("Cyber Keyboard", product.title)
        assertEquals(4.9f, product.rating, 0.001f)
        assertEquals(149.0, product.currentPrice, 0.001)
        assertTrue(product.isFavorite)
    }
}
