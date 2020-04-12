package dev.ahmdaeyz.pinster.ui.launcher.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dev.ahmdaeyz.pinster.data.authentication.AuthService

class SignInViewModel(val authService: AuthService) : ViewModel() {
    val isUserSignedIn: LiveData<Boolean> = authService.isUserSignedIn
    var user = authService.user
    private val _navigatedToPreferredCategories = MutableLiveData<Boolean>()
    val navigatedToPreferredCategories: LiveData<Boolean>
        get() = _navigatedToPreferredCategories

    fun signInWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        authService.handleSignIn(googleSignInAccount)
    }

    fun signOut() {
        authService.signOut()
    }

    fun navigateToPreferredCategories() {
        _navigatedToPreferredCategories.postValue(true)
    }
}