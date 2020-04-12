package dev.ahmdaeyz.pinster.ui.launcher.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ahmdaeyz.pinster.data.authentication.AuthService

@Suppress("UNCHECKED_CAST")
class SignInViewModelFactory(
        private val authService: AuthService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignInViewModel(authService) as T
    }
}