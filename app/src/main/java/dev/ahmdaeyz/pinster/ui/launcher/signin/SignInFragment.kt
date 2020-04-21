package dev.ahmdaeyz.pinster.ui.launcher.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dev.ahmdaeyz.pinster.R
import dev.ahmdaeyz.pinster.domain.common.exceptions.CanNotSignInException
import kotlinx.android.synthetic.main.sign_in_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SignInFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory by instance<SignInViewModelFactory>()
    private val gso by instance<GoogleSignInOptions>()
    private lateinit var viewModel: SignInViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleSignInClient = GoogleSignIn.getClient(this.activity as Activity, gso)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignInViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1999) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.signInWithGoogle(task.getResult(CanNotSignInException::class.java)!!)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_in_button.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 1999)
        }
        viewModel.isUserSignedIn.observe(viewLifecycleOwner, Observer {
            it.let {
                if (it) {
                    sign_in_button.visibility = View.GONE
                    viewModel.navigateToPreferredCategories()
                }
            }
        })
        viewModel.navigatedToPreferredCategories.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToPrefferedCategoriesFragment())
            }
        })
    }

}