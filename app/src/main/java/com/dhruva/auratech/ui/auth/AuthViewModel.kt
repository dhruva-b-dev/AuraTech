package com.dhruva.auratech.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onAction(action: AuthAction, onSuccess: () -> Unit) {
        when (action) {
            is AuthAction.OnEmailChanged -> {
                _uiState.update { it.copy(emailInput = action.email) }
            }
            is AuthAction.OnPasswordChanged -> {
                _uiState.update { it.copy(passwordInput = action.password) }
            }
            is AuthAction.OnToggleMode -> {
                _uiState.update { it.copy(isSignUpMode = !it.isSignUpMode) }
            }
            is AuthAction.OnSubmitAuth -> {
                val state = _uiState.value
                if (state.emailInput.isBlank() || state.passwordInput.isBlank()) {
                    _uiState.update { it.copy(errorMessage = "Please enter email and password.") }
                    return
                }
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                viewModelScope.launch {
                    try {
                        if (state.isSignUpMode) {
                            firebaseAuth.createUserWithEmailAndPassword(state.emailInput.trim(), state.passwordInput)
                                .addOnCompleteListener { task ->
                                    _uiState.update { it.copy(isLoading = false) }
                                    if (task.isSuccessful) {
                                        onSuccess()
                                    } else {
                                        _uiState.update { it.copy(errorMessage = task.exception?.message ?: "Sign up failed") }
                                    }
                                }
                        } else {
                            firebaseAuth.signInWithEmailAndPassword(state.emailInput.trim(), state.passwordInput)
                                .addOnCompleteListener { task ->
                                    _uiState.update { it.copy(isLoading = false) }
                                    if (task.isSuccessful) {
                                        onSuccess()
                                    } else {
                                        _uiState.update { it.copy(errorMessage = task.exception?.message ?: "Sign in failed") }
                                    }
                                }
                        }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Authentication error") }
                    }
                }
            }
            is AuthAction.OnGuestLogin -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true) }
                    firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
                        _uiState.update { it.copy(isLoading = false) }
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            // If anonymous auth is disabled in firebase console, allow guest bypass
                            onSuccess()
                        }
                    }
                }
            }
            is AuthAction.OnGoogleSignIn -> {
                signInWithGoogle(action.credential, onSuccess)
            }
            is AuthAction.OnDismissError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
        }
    }

    fun signInWithGoogle(credential: androidx.credentials.Credential, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                if (credential is androidx.credentials.CustomCredential &&
                    credential.type == com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleIdTokenCredential = com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.createFrom(credential.data)
                    val googleIdToken = googleIdTokenCredential.idToken
                    val firebaseCredential = com.google.firebase.auth.GoogleAuthProvider.getCredential(googleIdToken, null)

                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                    firebaseAuth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->
                            _uiState.update { it.copy(isLoading = false) }
                            if (task.isSuccessful) {
                                onSuccess()
                            } else {
                                _uiState.update { it.copy(errorMessage = task.exception?.message ?: "Google Sign-In failed") }
                            }
                        }
                } else {
                    _uiState.update { it.copy(errorMessage = "Invalid Google Credential type") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Google Sign-In error") }
            }
        }
    }
}
