package com.globant.data.mapper

import com.globant.data.database.entity.MarvelCharacterRealm
import com.globant.domain.entities.MarvelCharacter

class CharacterMapperLocal : BaseMapperRepository<MarvelCharacterRealm, MarvelCharacter> {

    override fun transform(type: MarvelCharacterRealm): MarvelCharacter = MarvelCharacter(
            type.id,
            type.name,
            type.description
    )

    override fun transformToRepository(type: MarvelCharacter): MarvelCharacterRealm = MarvelCharacterRealm(
            type.id,
            type.name,
            type.description
    )

}
