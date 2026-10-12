package com.dhruva.auratech.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhruva.auratech.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    uiState: AuthUiState,
    onAction: (AuthAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = DarkBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "AURA TECH",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (uiState.isSignUpMode) "Create your account" else "Sign in to continue",
                color = TextGray,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            OutlinedTextField(
                value = uiState.emailInput,
                onValueChange = { onAction(AuthAction.OnEmailChanged(it)) },
                label = { Text("Email Address") },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Mail, contentDescription = null, tint = Primary) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Color(0xFF282B3C)
                ),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.passwordInput,
                onValueChange = { onAction(AuthAction.OnPasswordChanged(it)) },
                label = { Text("Password") },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = null, tint = Primary) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Color(0xFF282B3C)
                ),
                shape = RoundedCornerShape(14.dp)
            )

            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = uiState.errorMessage,
                    color = Color(0xFFFF3B30),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onAction(AuthAction.OnSubmitAuth) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(14.dp),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black, strokeWidth = 2.dp)
                } else {
                    Text(
                        text = if (uiState.isSignUpMode) "Sign Up" else "Sign In",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val context = androidx.compose.ui.platform.LocalContext.current
            val coroutineScope = rememberCoroutineScope()

            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val credentialManager = androidx.credentials.CredentialManager.create(context)
                            val serverClientId = try {
                                context.getString(com.dhruva.auratech.R.string.default_web_client_id)
                            } catch (_: Exception) {
                                "YOUR_WEB_CLIENT_ID"
                            }

                            val googleIdOption = com.google.android.libraries.identity.googleid.GetGoogleIdOption.Builder()
                                .setFilterByAuthorizedAccounts(false)
                                .setServerClientId(serverClientId)
                                .setAutoSelectEnabled(false)
                                .build()

                            val request = androidx.credentials.GetCredentialRequest.Builder()
                                .addCredentialOption(googleIdOption)
                                .build()

                            val result = credentialManager.getCredential(context, request)
                            onAction(AuthAction.OnGoogleSignIn(result.credential))
                        } catch (e: androidx.credentials.exceptions.GetCredentialCancellationException) {
                            // User cancelled
                        } catch (e: Exception) {
                            // Handle error
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(
                    text = "Sign in with Google",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { onAction(AuthAction.OnToggleMode) }) {
                Text(
                    text = if (uiState.isSignUpMode) "Already have an account? Sign In" else "Don't have an account? Sign Up",
                    color = CyanAccent,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { onAction(AuthAction.OnGuestLogin) }) {
                Text(
                    text = "Continue as Guest / Demo Mode",
                    color = TextGray,
                    fontSize = 13.sp
                )
            }
        }
    }
}
