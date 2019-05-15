package com.globant.data.repositories

import com.globant.data.database.CharacterDatabase
import com.globant.data.service.CharacterService
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.utils.Result

class MarvelCharacterRepositoryImpl(
        private val characterService: CharacterService,
        private val characterDatabase: CharacterDatabase
) : MarvelCharacterRepository {

    override suspend fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter> {
        return if (getFromRemote) {
            val marvelCharacterResult = characterService.getCharacterById(id)

            if (marvelCharacterResult is Result.Success) {
                insertOrUpdateCharacter(marvelCharacterResult.data)
                marvelCharacterResult
            } else {
                Result.Failure(Exception("Failure"))
            }

        } else {
            characterDatabase.getCharacterById(id)
        }
    }

    private fun insertOrUpdateCharacter(character: MarvelCharacter) {
        characterDatabase.insertOrUpdateCharacter(character)
    }
}
