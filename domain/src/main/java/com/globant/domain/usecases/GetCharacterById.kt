package com.globant.domain.usecases

import com.globant.domain.repositories.MarvelCharacterRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetCharacterByIdUseCase : KoinComponent {
    private val marvelCharacterRepository: MarvelCharacterRepository by inject()

    suspend fun invoke(id: Int) = marvelCharacterRepository.getCharacterById(id)
}