package com.globant.domain.usecases

import com.globant.domain.repositories.MarvelCharacterRepositoryContract
import org.koin.core.KoinComponent
import org.koin.core.inject

class getCharacterById : KoinComponent {
    private val marvelCharacterRepository: MarvelCharacterRepositoryContract by inject()

    suspend fun invoke(id: Int) = marvelCharacterRepository.getCharacterById(id)
}