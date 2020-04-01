package com.globant

import android.app.Application
import com.globant.di.viewModelsModule
import io.realm.Realm
import org.koin.core.context.startKoin

class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        startKoin {
            modules(listOf(repositoriesModule, viewModelsModule, useCasesModule))
        }
    }
}
