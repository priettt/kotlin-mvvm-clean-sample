package com.globant.di

import com.globant.viewmodels.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { CharacterViewModel() }
}


