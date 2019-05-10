package com.globant.di

import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.domain.repositories.MarvelCharacterRepository
import org.koin.dsl.module

val useCasesModule = module {
    single<MarvelCharacterRepository> { MarvelCharacterRepositoryImpl() }
}


