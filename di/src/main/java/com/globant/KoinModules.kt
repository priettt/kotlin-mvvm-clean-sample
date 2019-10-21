package com.globant

import com.globant.data.database.CharacterDatabase
import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.data.service.CharacterService
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.usecases.GetCharacterByIdUseCase
import org.koin.dsl.module

val repositoriesModule = module {
    single { CharacterService() }
    single { CharacterDatabase() }
    single<MarvelCharacterRepository> { MarvelCharacterRepositoryImpl(get(), get()) }
}


val useCasesModule = module {
    single { GetCharacterByIdUseCase() }
}