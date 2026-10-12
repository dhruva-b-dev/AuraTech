package com.dhruva.auratech.ui.orders

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.model.OrderItem
import com.dhruva.auratech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    uiState: OrderUiState,
    onAction: (OrderAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("Order History", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onAction(OrderAction.OnBackClick) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.orders.isEmpty() && !uiState.isLoading) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, tint = TextGray, modifier = Modifier.size(56.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "No Orders Yet", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Your completed orders will appear here.", color = TextGray, fontSize = 14.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(uiState.orders) { order ->
                        OrderCard(order = order)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: OrderItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF282B3C), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = order.orderId, color = Primary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CyanAccent.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = order.status,
                        color = CyanAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Text(text = "Placed on ${order.dateText}", color = TextGray, fontSize = 12.sp)
            HorizontalDivider(color = Color(0xFF282B3C))

            Text(text = order.itemsSummary, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(text = "Shipping to: ${order.shippingAddress}", color = TextGray, fontSize = 12.sp)
            Text(text = "Payment: ${order.paymentMethod}", color = TextGray, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Total Amount", color = TextGray, fontSize = 13.sp)
                Text(text = "$${String.format(java.util.Locale.US, "%.2f", order.totalAmount)}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
