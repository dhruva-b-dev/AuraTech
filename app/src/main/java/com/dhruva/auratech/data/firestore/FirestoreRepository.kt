package com.dhruva.auratech.data.firestore

import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_CATEGORIES
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_PRODUCTS
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_USERS
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_CART
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_PROMO_BANNERS
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_TRUST_BADGES
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_EDITORIAL_BANNERS
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_PRODUCT_DETAILS
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_ADDRESSES
import com.dhruva.auratech.data.firestore.FirestoreRepository.COLLECTIONS.COLLECTION_ORDERS
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Firestore repository for all data operations.
 * Provides reactive streams using Flow for real-time updates.
 */
@Singleton
class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    // Collection names
    private object COLLECTIONS{
        const val COLLECTION_PRODUCTS = "products"
        const val COLLECTION_CATEGORIES = "categories"
        const val COLLECTION_USERS = "users"
        const val COLLECTION_CART = "cart"
        const val COLLECTION_ADDRESSES = "addresses"
        const val COLLECTION_ORDERS = "orders"
        const val COLLECTION_PROMO_BANNERS = "promoBanners"
        const val COLLECTION_TRUST_BADGES = "trustBadges"
        const val COLLECTION_EDITORIAL_BANNERS = "editorialBanners"
        const val COLLECTION_PRODUCT_DETAILS = "productDetails"
    }

    // =============================================================================
    // PRODUCTS
    // =============================================================================

    fun getAllProducts(): Flow<List<FirestoreProduct>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_PRODUCTS)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val products = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreProduct::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(products)
            }
        awaitClose { listener.remove() }
    }

    fun getProductById(productId: String): Flow<FirestoreProduct?> = callbackFlow {
        val listener = firestore.collection(COLLECTION_PRODUCTS)
            .document(productId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val product = snapshot?.toObject(FirestoreProduct::class.java)?.copy(id = snapshot.id)
                trySend(product)
            }
        awaitClose { listener.remove() }
    }

    suspend fun addProduct(product: FirestoreProduct): String {
        val docRef = if (product.id.isEmpty()) {
            firestore.collection(COLLECTION_PRODUCTS).document()
        } else {
            firestore.collection(COLLECTION_PRODUCTS).document(product.id)
        }
        docRef.set(product).await()
        return docRef.id
    }

    suspend fun updateProduct(productId: String, updates: Map<String, Any>): Boolean {
        return try {
            firestore.collection(COLLECTION_PRODUCTS)
                .document(productId)
                .update(updates)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteProduct(productId: String): Boolean {
        return try {
            firestore.collection(COLLECTION_PRODUCTS)
                .document(productId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // =============================================================================
    // CATEGORIES
    // =============================================================================

    fun getAllCategories(): Flow<List<FirestoreCategory>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_CATEGORIES)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val categories = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreCategory::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(categories)
            }
        awaitClose { listener.remove() }
    }

    fun getCategoryCollection(categoryId: String): Flow<FirestoreCategoryCollection?> = callbackFlow {
        val listener = firestore.collection(COLLECTION_CATEGORIES)
            .document(categoryId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val category = snapshot?.toObject(FirestoreCategoryCollection::class.java)?.copy(id = snapshot.id)
                trySend(category)
            }
        awaitClose { listener.remove() }
    }

    fun getAllCategoryCollections(): Flow<List<FirestoreCategoryCollection>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_CATEGORIES)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val categories = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreCategoryCollection::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(categories)
            }
        awaitClose { listener.remove() }
    }

    // =============================================================================
    // PRODUCT DETAILS
    // =============================================================================

    fun getProductDetail(productId: String): Flow<FirestoreProductDetail?> = callbackFlow {
        val listener = firestore.collection(COLLECTION_PRODUCT_DETAILS)
            .document(productId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val detail = snapshot?.toObject(FirestoreProductDetail::class.java)?.copy(id = snapshot.id)
                trySend(detail)
            }
        awaitClose { listener.remove() }
    }

    suspend fun addProductDetail(detail: FirestoreProductDetail): String {
        val docRef = if (detail.id.isEmpty()) {
            firestore.collection(COLLECTION_PRODUCT_DETAILS).document()
        } else {
            firestore.collection(COLLECTION_PRODUCT_DETAILS).document(detail.id)
        }
        docRef.set(detail).await()
        return docRef.id
    }

    // =============================================================================
    // PROMO BANNERS
    // =============================================================================

    fun getPromoBanners(): Flow<List<FirestorePromoBanner>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_PROMO_BANNERS)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val banners = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestorePromoBanner::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(banners)
            }
        awaitClose { listener.remove() }
    }

    fun getHeroPromoBanner(): Flow<FirestorePromoBanner?> = callbackFlow {
        val listener = firestore.collection(COLLECTION_PROMO_BANNERS)
            .whereEqualTo("id", "banner_autumn_festival")
            .limit(1)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val banner = snapshot?.documents?.firstOrNull()?.let { doc ->
                    doc.toObject(FirestorePromoBanner::class.java)?.copy(id = doc.id)
                }
                trySend(banner)
            }
        awaitClose { listener.remove() }
    }

    // =============================================================================
    // TRUST BADGES
    // =============================================================================

    fun getTrustBadges(): Flow<List<FirestoreTrustBadge>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_TRUST_BADGES)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val badges = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreTrustBadge::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(badges)
            }
        awaitClose { listener.remove() }
    }

    // =============================================================================
    // EDITORIAL BANNERS
    // =============================================================================

    fun getEditorialBanners(): Flow<List<FirestoreEditorialBanner>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_EDITORIAL_BANNERS)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val banners = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreEditorialBanner::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(banners)
            }
        awaitClose { listener.remove() }
    }

    // =============================================================================
    // USER PROFILE
    // =============================================================================

    fun getUserProfile(userId: String): Flow<FirestoreUserProfile?> = callbackFlow {
        val listener = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val profile = snapshot?.toObject(FirestoreUserProfile::class.java)?.copy(userId = snapshot.id)
                trySend(profile)
            }
        awaitClose { listener.remove() }
    }

    suspend fun createUserProfile(profile: FirestoreUserProfile): Boolean {
        return try {
            firestore.collection(COLLECTION_USERS)
                .document(profile.userId)
                .set(profile)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUserProfile(userId: String, updates: Map<String, Any>): Boolean {
        return try {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .update(updates)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // =============================================================================
    // CART (User-specific)
    // =============================================================================

    fun getUserCartItems(userId: String): Flow<List<FirestoreCartItem>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .collection(COLLECTION_CART)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val items = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreCartItem::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(items)
            }
        awaitClose { listener.remove() }
    }

    suspend fun addCartItem(userId: String, item: FirestoreCartItem): String {
        val docRef = if (item.id.isEmpty()) {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_CART)
                .document()
        } else {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_CART)
                .document(item.id)
        }
        docRef.set(item).await()
        return docRef.id
    }

    suspend fun updateCartItem(userId: String, itemId: String, updates: Map<String, Any>): Boolean {
        return try {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_CART)
                .document(itemId)
                .update(updates)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun removeCartItem(userId: String, itemId: String): Boolean {
        return try {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_CART)
                .document(itemId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun clearUserCart(userId: String): Boolean {
        return try {
            val snapshot = firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_CART)
                .get()
                .await()
            
            val batch = firestore.batch()
            snapshot.documents.forEach { doc ->
                batch.delete(doc.reference)
            }
            batch.commit().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // =============================================================================
    // ORDER STATUS & ACTIVE DELIVERY (User-specific)
    // =============================================================================

    fun getOrderStatusCount(userId: String): Flow<FirestoreOrderStatusCount?> = callbackFlow {
        val listener = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .collection("orderStatus")
            .document("counts")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val status = snapshot?.toObject(FirestoreOrderStatusCount::class.java)?.copy(userId = userId)
                trySend(status)
            }
        awaitClose { listener.remove() }
    }

    fun getActiveDelivery(userId: String): Flow<FirestoreActiveDelivery?> {
        // Query for the most recent active delivery
        return callbackFlow {
            val listener = firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection("deliveries")
                .whereEqualTo("isActive", true)
                .limit(1)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val delivery = snapshot?.documents?.firstOrNull()
                        ?.toObject(FirestoreActiveDelivery::class.java)?.copy(userId = userId)
                    trySend(delivery)
                }
            awaitClose { listener.remove() }
        }
    }

    // =============================================================================
    // PROFILE MENU ITEMS
    // =============================================================================

    fun getProfileMenuItems(): Flow<List<FirestoreProfileMenuItem>> = callbackFlow {
        val listener = firestore.collection("profileMenuItems")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val items = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreProfileMenuItem::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(items)
            }
        awaitClose { listener.remove() }
    }

    suspend fun addProfileMenuItem(item: FirestoreProfileMenuItem): String {
        val docRef = if (item.id.isEmpty()) {
            firestore.collection("profileMenuItems").document()
        } else {
            firestore.collection("profileMenuItems").document(item.id)
        }
        docRef.set(item).await()
        return docRef.id
    }

    // =============================================================================
    // SEEDER HELPER METHODS
    // =============================================================================

    suspend fun addTrustBadge(badge: FirestoreTrustBadge): String {
        val docRef = firestore.collection(COLLECTION_TRUST_BADGES).document(badge.id)
        docRef.set(badge).await()
        return docRef.id
    }

    suspend fun addPromoBanner(banner: FirestorePromoBanner): String {
        val docRef = firestore.collection(COLLECTION_PROMO_BANNERS).document(banner.id)
        docRef.set(banner).await()
        return docRef.id
    }

    suspend fun addEditorialBanner(banner: FirestoreEditorialBanner): String {
        val docRef = firestore.collection(COLLECTION_EDITORIAL_BANNERS).document(banner.id)
        docRef.set(banner).await()
        return docRef.id
    }

    suspend fun addCategory(category: FirestoreCategory): String {
        val docRef = firestore.collection(COLLECTION_CATEGORIES).document(category.id)
        docRef.set(category).await()
        return docRef.id
    }

    suspend fun addOrderStatus(userId: String, status: FirestoreOrderStatusCount): Boolean {
        return try {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection("orderStatus")
                .document("counts")
                .set(status)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun addActiveDelivery(userId: String, delivery: FirestoreActiveDelivery): String {
        val docRef = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .collection("deliveries")
            .document()
        docRef.set(delivery).await()
        return docRef.id
    }

    // =============================================================================
    // ADDRESSES (User-specific)
    // =============================================================================

    fun getUserAddresses(userId: String): Flow<List<FirestoreAddress>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .collection(COLLECTION_ADDRESSES)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val addresses = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreAddress::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(addresses)
            }
        awaitClose { listener.remove() }
    }

    suspend fun addAddress(userId: String, address: FirestoreAddress): String {
        val addressesRef = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .collection(COLLECTION_ADDRESSES)
        
        val docRef = if (address.id.isEmpty()) addressesRef.document() else addressesRef.document(address.id)
        
        if (address.isDefault) {
            val snapshot = addressesRef.get().await()
            for (doc in snapshot.documents) {
                if (doc.id != docRef.id) {
                    doc.reference.update("isDefault", false).await()
                }
            }
        }
        
        docRef.set(address).await()
        return docRef.id
    }

    suspend fun deleteAddress(userId: String, addressId: String): Boolean {
        return try {
            firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_ADDRESSES)
                .document(addressId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun setDefaultAddress(userId: String, addressId: String): Boolean {
        return try {
            val addressesRef = firestore.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_ADDRESSES)
            
            val snapshot = addressesRef.get().await()
            for (doc in snapshot.documents) {
                doc.reference.update("isDefault", doc.id == addressId).await()
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    // =============================================================================
    // ORDERS (User-specific)
    // =============================================================================

    fun getUserOrders(userId: String): Flow<List<FirestoreOrder>> = callbackFlow {
        val listener = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .collection(COLLECTION_ORDERS)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val orders = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreOrder::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(orders)
            }
        awaitClose { listener.remove() }
    }

    suspend fun addOrder(userId: String, order: FirestoreOrder): String {
        val docRef = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .collection(COLLECTION_ORDERS)
            .document()
        docRef.set(order).await()
        return docRef.id
    }

    suspend fun ensureUserProfileExists(userId: String) {
        try {
            val docRef = firestore.collection(COLLECTION_USERS).document(userId)
            val snapshot = docRef.get().await()
            if (!snapshot.exists()) {
                val defaultProfile = FirestoreUserProfile(
                    userId = userId,
                    name = "Aura Tech User",
                    email = "user@auratech.com",
                    phone = "+1 (555) 019-2834",
                    avatarResName = "user_avatar",
                    isVerified = true,
                    memberTier = "Bronze Member ★",
                    walletBalance = 150.00,
                    rewardPoints = 250,
                    activeCouponsCount = 3
                )
                docRef.set(defaultProfile).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
