package com.globant

import androidx.room.Room
import com.globant.data.database.CharacterDatabase
import com.globant.data.database.CharacterDatabase.Companion.DATABASE_NAME
import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.data.service.CharacterService
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.usecases.GetCharacterByIdUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            databaseModule,
            serviceModule,
            repositoryModule,
            useCaseModule
        )
    )
}
val databaseModule = module {
    single { Room.databaseBuilder(get(), CharacterDatabase::class.java, DATABASE_NAME).build() }
    single { get<CharacterDatabase>().characterDao() }
}

val serviceModule = module {
    single { CharacterService(androidContext()) }
}

val repositoryModule = module {
    single<MarvelCharacterRepository> { MarvelCharacterRepositoryImpl(get(), get()) }
}

val useCaseModule = module {
    factory { GetCharacterByIdUseCase(get()) }
}

