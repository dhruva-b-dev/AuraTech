package com.dhruva.auratech.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.model.Address
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.theme.CardBackground
import com.dhruva.auratech.ui.theme.CyanAccent
import com.dhruva.auratech.ui.theme.DarkBackground
import com.dhruva.auratech.ui.theme.Primary
import com.dhruva.auratech.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    uiState: CheckoutUiState,
    onAction: (CheckoutAction) -> Unit,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (uiState.currentStep) {
                            CheckoutStep.ADDRESS -> "Select Shipping Address"
                            CheckoutStep.PAYMENT -> "Payment Method"
                            CheckoutStep.REVIEW -> "Review & Place Order"
                            CheckoutStep.SUCCESS -> "Order Confirmed"
                        },
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    if (uiState.currentStep != CheckoutStep.SUCCESS) {
                        IconButton(onClick = {
                            if (uiState.currentStep == CheckoutStep.ADDRESS) {
                                onAction(CheckoutAction.OnBackClick)
                            } else {
                                onAction(CheckoutAction.OnPreviousStep)
                            }
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
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
            when (uiState.currentStep) {
                CheckoutStep.ADDRESS -> AddressSelectionStep(uiState = uiState, onAction = onAction)
                CheckoutStep.PAYMENT -> PaymentSelectionStep(uiState = uiState, onAction = onAction)
                CheckoutStep.REVIEW -> OrderReviewStep(uiState = uiState, onAction = onAction)
                CheckoutStep.SUCCESS -> OrderSuccessStep(orderId = uiState.orderSuccessId ?: "AURA-000", onFinish = onNavigateHome)
            }

            CartFeedbackToast(
                message = uiState.toastMessage,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun AddressSelectionStep(uiState: CheckoutUiState, onAction: (CheckoutAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Choose your delivery address", color = TextGray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))

            if (uiState.addresses.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No addresses found. Please add an address in your Profile first.", color = Color.White, textAlign = TextAlign.Center)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.addresses) { address ->
                        val isSelected = address.id == uiState.selectedAddressId
                        AddressSelectionCard(
                            address = address,
                            isSelected = isSelected,
                            onSelect = { onAction(CheckoutAction.OnSelectAddress(address.id)) }
                        )
                    }
                }
            }
        }

        Button(
            onClick = { onAction(CheckoutAction.OnNextStep) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Proceed to Payment", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun AddressSelectionCard(address: Address, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Primary else Color(0xFF282B3C),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onSelect() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = address.title, color = Primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    if (address.isDefault) {
                        Surface(shape = RoundedCornerShape(6.dp), color = CyanAccent.copy(alpha = 0.2f)) {
                            Text(text = "DEFAULT", color = CyanAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = address.fullName, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "${address.streetAddress}, ${address.city}, ${address.state} ${address.postalCode}", color = TextGray, fontSize = 13.sp)
            }

            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(selectedColor = Primary)
            )
        }
    }
}

@Composable
fun PaymentSelectionStep(uiState: CheckoutUiState, onAction: (CheckoutAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Select payment method", color = TextGray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))

            uiState.paymentMethods.forEach { method ->
                val isSelected = method == uiState.selectedPaymentMethod
                PaymentMethodCard(
                    method = method,
                    isSelected = isSelected,
                    onSelect = { onAction(CheckoutAction.OnSelectPayment(method)) }
                )
            }
        }

        Button(
            onClick = { onAction(CheckoutAction.OnNextStep) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Review Order", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun PaymentMethodCard(method: String, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Primary else Color(0xFF282B3C),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onSelect() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    imageVector = when {
                        method.contains("Card") -> Icons.Default.CreditCard
                        else -> Icons.Default.Payment
                    },
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = method, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(selectedColor = Primary)
            )
        }
    }
}

@Composable
fun OrderReviewStep(uiState: CheckoutUiState, onAction: (CheckoutAction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(androidx.compose.foundation.rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Shipping Address", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            uiState.selectedAddress?.let { address ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(text = address.fullName, color = Color.White, fontWeight = FontWeight.Bold)
                        Text(text = "${address.streetAddress}, ${address.city}, ${address.state} ${address.postalCode}", color = TextGray, fontSize = 13.sp)
                    }
                }
            }

            Text(text = "Payment Method", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.CreditCard, contentDescription = null, tint = Primary)
                    Text(text = uiState.selectedPaymentMethod, color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }

            Text(text = "Order Summary (${uiState.cartItems.sumOf { it.quantity }} items)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Subtotal", color = TextGray)
                        Text(text = "$${String.format(java.util.Locale.US, "%.2f", uiState.subtotal)}", color = Color.White)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Discounts", color = CyanAccent)
                        Text(text = "-$${String.format(java.util.Locale.US, "%.2f", uiState.discountSavings)}", color = CyanAccent)
                    }
                    HorizontalDivider(color = Color(0xFF282B3C))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Total Payable", color = Color.White, fontWeight = FontWeight.Bold)
                        Text(text = "$${String.format(java.util.Locale.US, "%.2f", uiState.totalPayable)}", color = Primary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }

        Button(
            onClick = { onAction(CheckoutAction.OnPlaceOrder) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            shape = RoundedCornerShape(12.dp),
            enabled = !uiState.isProcessing
        ) {
            if (uiState.isProcessing) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black, strokeWidth = 2.dp)
            } else {
                Text("Place Secure Order ($${String.format(java.util.Locale.US, "%.2f", uiState.totalPayable)})", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun OrderSuccessStep(orderId: String, onFinish: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Order Placed Successfully!", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Reference ID: $orderId", color = CyanAccent, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Thank you for shopping with Aura Tech. We are preparing your shipment.", color = TextGray, fontSize = 14.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Store", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
