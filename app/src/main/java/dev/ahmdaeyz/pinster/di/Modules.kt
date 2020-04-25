package dev.ahmdaeyz.pinster.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import dev.ahmdaeyz.pinster.data.authentication.AuthService
import dev.ahmdaeyz.pinster.data.authentication.FirebaseAuthService
import dev.ahmdaeyz.pinster.data.db.PinsterDatabase
import dev.ahmdaeyz.pinster.data.network.*
import dev.ahmdaeyz.pinster.data.repositories.NewsSourcesRepositoryImpl
import dev.ahmdaeyz.pinster.domain.repositories.NewsSourcesRepository
import dev.ahmdaeyz.pinster.ui.launcher.prefrerredcategories.PreferredCategoriesViewModelFactory
import dev.ahmdaeyz.pinster.ui.launcher.signin.SignInViewModelFactory
import org.koin.dsl.module



val projectWideModule = module {
    // network
    single{FirebaseAuth.getInstance()}
    single<ConnectivityInterceptor> { ConnectivityInterceptorImpl(get<Context>())}
    single<NewsAPIApiService> { NewsAPIApiService(get<ConnectivityInterceptor>()) }
    // news sources
    single<NewsSourcesNetworkDataSource> { NewsSourcesNetworkDataSourceImpl(get()) }
    single<NewsSourcesRepository> { NewsSourcesRepositoryImpl(get(),get()) }
}


val launcheModule = module{
    single<AuthService> { FirebaseAuthService(get<Context>(), get())}
    // database
    single { PinsterDatabase(get()) }
    single { get<PinsterDatabase>().newsSourcesDao() }
    //sign in
    factory{SignInViewModelFactory(get())}
    // preferred categories
    factory { PreferredCategoriesViewModelFactory(get()) }
}
