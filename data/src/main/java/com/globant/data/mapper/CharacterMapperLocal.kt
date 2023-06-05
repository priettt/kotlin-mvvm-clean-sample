package com.globant.data.mapper

import com.globant.data.database.entity.MarvelCharacterEntity
import com.globant.domain.entities.MarvelCharacter

class CharacterMapperLocal : BaseMapperRepository<MarvelCharacterEntity, MarvelCharacter> {

    override fun transform(type: MarvelCharacterEntity): MarvelCharacter {
        return MarvelCharacter(
            type.id,
            type.name,
            type.description
        )
    }

    override fun transformToEntity(type: MarvelCharacter): MarvelCharacterEntity {
        return MarvelCharacterEntity(
            type.id,
            type.name,
            type.description
        )
    }

}
