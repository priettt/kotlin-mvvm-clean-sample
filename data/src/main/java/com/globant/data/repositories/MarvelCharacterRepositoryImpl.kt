package com.globant.data.repositories

import com.globant.data.database.CharacterDatabase
import com.globant.data.mapper.CharacterMapperLocal
import com.globant.data.service.CharacterService
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.utils.Result
import io.realm.Realm

class MarvelCharacterRepositoryImpl : MarvelCharacterRepository {

    private val mapperLocal: CharacterMapperLocal = CharacterMapperLocal()

    override fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter> {
        if (getFromRemote) {
            val marvelCharacterResult: Result<MarvelCharacter> = CharacterService.getCharacterById(id)

            when (marvelCharacterResult) {
                is Result.Failure -> {
                    return marvelCharacterResult
                }
                is Result.Success -> {
                    insertOrUpdateCharacter(marvelCharacterResult.data)
                    return marvelCharacterResult
                }
            }
        } else {
            return CharacterDatabase.getCharacterById(id)
        }
    }

    private fun insertOrUpdateCharacter(character: MarvelCharacter) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.insertOrUpdate( mapperLocal.transformToRepository(character))
        }
    }
}
