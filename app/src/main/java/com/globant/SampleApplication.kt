package com.globant

import android.app.Application
import com.globant.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.loadKoinModules

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SampleApplication)
            injectFeature()
            loadKoinModules(viewModelsModule)
        }
    }
}
