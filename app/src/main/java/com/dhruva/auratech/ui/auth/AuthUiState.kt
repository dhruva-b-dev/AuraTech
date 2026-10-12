package com.dhruva.auratech.ui.auth

data class AuthUiState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val isSignUpMode: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface AuthAction {
    data class OnEmailChanged(val email: String) : AuthAction
    data class OnPasswordChanged(val password: String) : AuthAction
    data object OnToggleMode : AuthAction
    data object OnSubmitAuth : AuthAction
    data object OnGuestLogin : AuthAction
    data class OnGoogleSignIn(val credential: androidx.credentials.Credential) : AuthAction
    data object OnDismissError : AuthAction
}
