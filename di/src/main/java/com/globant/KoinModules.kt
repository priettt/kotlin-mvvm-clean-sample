package com.globant

import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.domain.repositories.MarvelCharacterRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<MarvelCharacterRepository> { MarvelCharacterRepositoryImpl() }
}
