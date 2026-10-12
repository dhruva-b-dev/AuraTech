package com.dhruva.auratech.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.auratech.data.firestore.FirestoreModelMapper
import com.dhruva.auratech.data.firestore.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    firebaseAuth: com.google.firebase.auth.FirebaseAuth
) : ViewModel() {

    private val userId: String = firebaseAuth.currentUser?.uid ?: "test_user_123"

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            firestoreRepository.getUserOrders(userId).collect { firestoreOrders ->
                val orders = firestoreOrders.map { FirestoreModelMapper.toOrderItem(it) }
                _uiState.update {
                    it.copy(
                        orders = orders.sortedByDescending { order -> order.id },
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onAction(action: OrderAction) {
        when (action) {
            is OrderAction.OnDismissToast -> {
                _uiState.update { it.copy(toastMessage = null) }
            }
            else -> {}
        }
    }
}
