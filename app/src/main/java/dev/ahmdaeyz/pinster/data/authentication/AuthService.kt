package dev.ahmdaeyz.pinster.data.authentication

import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface AuthService {
    val isUserSignedIn: LiveData<Boolean>
    var user: FirebaseUser?
    fun handleSignIn(googleSignInAccount: GoogleSignInAccount)
    fun signOut()
}