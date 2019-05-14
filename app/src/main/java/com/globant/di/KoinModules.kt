package com.globant.di

import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.viewmodels.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { CharacterViewModel(get()) }
}

val useCasesModule = module {
    single { GetCharacterByIdUseCase() }
}


