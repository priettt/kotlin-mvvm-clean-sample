package com.globant.di

import com.globant.mvvm.viewModels.CharacterViewModel
import com.globant.data.FindCharacterImpl
import com.globant.data.repositories.MarvelCharacterRepository
import com.globant.domain.repositories.MarvelCharacterRepositoryContract
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single<MarvelCharacterRepositoryContract> { MarvelCharacterRepository() }
    single { FindCharacterImpl() }
    viewModel { CharacterViewModel(get()) }
}
