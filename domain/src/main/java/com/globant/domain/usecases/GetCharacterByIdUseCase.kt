package com.globant.domain.usecases

import com.globant.domain.repositories.MarvelCharacterRepository
import org.koin.core.component.KoinComponent

class GetCharacterByIdUseCase(
    private val marvelCharacterRepository: MarvelCharacterRepository
): KoinComponent {

    operator fun invoke(id: Int, getFromRemote: Boolean) =
        marvelCharacterRepository.getCharacterById(id, getFromRemote)

}
