package com.globant.di

import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<com.globant.domain.repositories.MarvelCharacterRepository> { MarvelCharacterRepositoryImpl() }
}