package dev.ahmdaeyz.pinster

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dev.ahmdaeyz.pinster.data.authentication.AuthService
import dev.ahmdaeyz.pinster.data.authentication.FirebaseAuthService
import dev.ahmdaeyz.pinster.data.db.PinsterDatabase
import dev.ahmdaeyz.pinster.data.network.*
import dev.ahmdaeyz.pinster.data.repositories.NewsSourcesRepositoryImpl
import dev.ahmdaeyz.pinster.domain.repositories.NewsSourcesRepository
import dev.ahmdaeyz.pinster.ui.launcher.signin.SignInViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class PinsterApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        bind<FirebaseAuth>() with singleton { FirebaseAuth.getInstance() }
        bind<AuthService>() with singleton { FirebaseAuthService(instance(), instance()) }
        bind<GoogleSignInOptions>() with singleton {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestProfile()
                    .build()
        }
        bind<SignInViewModelFactory>() with provider { SignInViewModelFactory(instance()) }
        bind() from singleton { PinsterDatabase(instance()) }
        bind() from singleton { instance<PinsterDatabase>().newsSourcesDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { NewsAPIApiService(instance()) }
        bind<NewsSourcesNetworkDataSource>() with singleton { NewsSourcesNetworkDataSourceImpl(instance()) }
        bind<NewsSourcesRepository>() with singleton { NewsSourcesRepositoryImpl(instance(), instance()) }
    }
}