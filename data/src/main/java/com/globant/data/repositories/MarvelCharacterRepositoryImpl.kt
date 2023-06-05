package com.globant.data.repositories

import com.globant.data.database.CharacterDatabase
import com.globant.data.mapper.CharacterMapperLocal
import com.globant.data.service.CharacterService
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.utils.Result
import com.globant.domain.utils.Result.Success

class MarvelCharacterRepositoryImpl(
    private val characterService: CharacterService,
    private val characterDatabase: CharacterDatabase
) : MarvelCharacterRepository {

    private val mapper = CharacterMapperLocal()

    override fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter> =
        if (getFromRemote) {
            val marvelCharacterResult = characterService.getCharacterById(id)
            if (marvelCharacterResult is Success) {
                insertOrUpdateCharacter(marvelCharacterResult.data)
            }
            marvelCharacterResult
        } else {
            val entity = characterDatabase.characterDao().getById(id)
            Success(mapper.transform(entity))
        }

    private fun insertOrUpdateCharacter(character: MarvelCharacter) {
        characterDatabase.characterDao().insertOrUpdate(mapper.transformToEntity(character))
    }

}
