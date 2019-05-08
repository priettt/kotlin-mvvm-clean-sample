package com.globant.di

import com.globant.data.repositories.MarvelCharacterRepositoryImpl
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.mvvm.viewModels.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCasesModule = module {
    single<MarvelCharacterRepository> { MarvelCharacterRepositoryImpl() }
}

val viewModelsModule= module {
    viewModel { CharacterViewModel() }
}


