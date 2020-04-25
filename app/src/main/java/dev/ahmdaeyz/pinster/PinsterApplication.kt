package dev.ahmdaeyz.pinster

import android.app.Application
import dev.ahmdaeyz.pinster.di.launcheModule
import dev.ahmdaeyz.pinster.di.projectWideModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class PinsterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PinsterApplication)
            modules(listOf(projectWideModule, launcheModule))
        }
    }
}