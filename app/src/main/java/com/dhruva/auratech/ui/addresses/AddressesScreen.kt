package com.dhruva.auratech.ui.addresses

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.model.Address
import com.dhruva.auratech.ui.home.components.CartFeedbackToast
import com.dhruva.auratech.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressesScreen(
    uiState: AddressUiState,
    onAction: (AddressAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("Shipping Addresses", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onAction(AddressAction.OnBackClick) }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(AddressAction.OnAddAddressClick) },
                containerColor = Primary,
                contentColor = Color.Black
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Address")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.addresses.isEmpty() && !uiState.isLoading) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = TextGray, modifier = Modifier.size(56.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "No Saved Addresses", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Add a shipping address for faster checkout.", color = TextGray, fontSize = 14.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(uiState.addresses) { address ->
                        AddressCard(
                            address = address,
                            onDelete = { onAction(AddressAction.OnDeleteAddress(address.id)) },
                            onSetDefault = { onAction(AddressAction.OnSetDefaultAddress(address.id)) }
                        )
                    }
                }
            }

            CartFeedbackToast(
                message = uiState.toastMessage,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
            )

            if (uiState.showAddDialog) {
                AddAddressDialog(
                    uiState = uiState,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun AddressCard(
    address: Address,
    onDelete: () -> Unit,
    onSetDefault: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (address.isDefault) 2.dp else 1.dp,
                color = if (address.isDefault) Primary else Color(0xFF282B3C),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CodeBg
                    ) {
                        Text(
                            text = address.title,
                            color = Primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    if (address.isDefault) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = CyanAccent.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "DEFAULT",
                                color = CyanAccent,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = TextGray)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = address.fullName, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = address.streetAddress, color = TextGray, fontSize = 13.sp)
            Text(text = "${address.city}, ${address.state} ${address.postalCode}", color = TextGray, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Phone: ${address.phone}", color = TextGray, fontSize = 12.sp)

            if (!address.isDefault) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onSetDefault,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Set as Default", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun AddAddressDialog(
    uiState: AddressUiState,
    onAction: (AddressAction) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onAction(AddressAction.OnDismissDialog) },
        containerColor = CardBackground,
        title = { Text("Add New Address", color = Color.White) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = uiState.titleInput,
                    onValueChange = { onAction(AddressAction.OnTitleChanged(it)) },
                    label = { Text("Label (e.g. Home, Work)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
                OutlinedTextField(
                    value = uiState.fullNameInput,
                    onValueChange = { onAction(AddressAction.OnFullNameChanged(it)) },
                    label = { Text("Full Name") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
                OutlinedTextField(
                    value = uiState.streetAddressInput,
                    onValueChange = { onAction(AddressAction.OnStreetChanged(it)) },
                    label = { Text("Street Address") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
                OutlinedTextField(
                    value = uiState.cityInput,
                    onValueChange = { onAction(AddressAction.OnCityChanged(it)) },
                    label = { Text("City") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = uiState.stateInput,
                        onValueChange = { onAction(AddressAction.OnStateChanged(it)) },
                        label = { Text("State") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                    OutlinedTextField(
                        value = uiState.postalCodeInput,
                        onValueChange = { onAction(AddressAction.OnPostalCodeChanged(it)) },
                        label = { Text("Postal Code") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }
                OutlinedTextField(
                    value = uiState.phoneInput,
                    onValueChange = { onAction(AddressAction.OnPhoneChanged(it)) },
                    label = { Text("Phone Number") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onAction(AddressAction.OnSaveAddress) },
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text("Save Address", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = { onAction(AddressAction.OnDismissDialog) }) {
                Text("Cancel", color = TextGray)
            }
        }
    )
}
