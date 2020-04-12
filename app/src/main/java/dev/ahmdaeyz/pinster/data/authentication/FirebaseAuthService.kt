package dev.ahmdaeyz.pinster.data.authentication

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dev.ahmdaeyz.pinster.domain.common.exceptions.CanNotSignInException

class FirebaseAuthService(
        context: Context,
        val auth: FirebaseAuth
) : AuthService {
    private val appContext = context.applicationContext
    override var user: FirebaseUser? = auth.currentUser
    private val _isUserSignedIn = MutableLiveData<Boolean>()
    override val isUserSignedIn: LiveData<Boolean>
        get() = _isUserSignedIn

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val aFirebaseUser = firebaseAuth.currentUser
            if (aFirebaseUser != null) {
                _isUserSignedIn.postValue(true)
                user = aFirebaseUser
            } else {
                _isUserSignedIn.postValue(false)
            }
        }
    }

    override fun handleSignIn(googleSignInAccount: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        try {
            auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("FirebaseAuthService", "signInWithCredential:success")
                            val aFirebaseUser = auth.currentUser
                            user = aFirebaseUser
                        } else {
                            throw CanNotSignInException()
                        }
                    }
        } catch (e: Exception) {
            Log.e("FirebaseAuthentication", "Couldn't authenticate: $e")
        }

    }

    override fun signOut() {
        auth.signOut()
    }
}