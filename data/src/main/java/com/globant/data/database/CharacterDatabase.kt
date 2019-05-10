package com.globant.data.database

import com.globant.data.database.entity.MarvelCharacterRealm
import com.globant.data.mapper.CharacterMapperLocal
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result
import io.realm.Realm

class CharacterDatabase {

    companion object {

        fun getCharacterById(id: Int): Result<MarvelCharacter> {
            val mapper = CharacterMapperLocal()
            val realm = Realm.getDefaultInstance()
            val character = realm.where(MarvelCharacterRealm::class.java).equalTo("id", id).findFirst()
            character?.let { return Result.Success(mapper.transform(character)) }
            return Result.Failure(Exception("Character not found"))
        }
    }
}
