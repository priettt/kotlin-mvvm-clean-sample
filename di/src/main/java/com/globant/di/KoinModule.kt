package com.globant.di

import com.globant.data.repositories.MarvelCharacterRepository
import com.globant.domain.repositories.MarvelCharacterRepositoryContract
import org.koin.dsl.module

val useCaseModule = module {
    single<MarvelCharacterRepositoryContract> { MarvelCharacterRepository() }
}
