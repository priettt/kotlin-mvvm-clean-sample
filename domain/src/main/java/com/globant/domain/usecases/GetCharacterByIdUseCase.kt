package com.globant.domain.usecases

import com.globant.domain.repositories.MarvelCharacterRepositoryContract
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetCharacterByIdUseCase: KoinComponent {
    private val marvelCharacterRepository: MarvelCharacterRepositoryContract by inject()

    operator fun invoke(id: Int) = marvelCharacterRepository.getCharacterById(id)
}