package com.globant.domain.repositories

import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

interface MarvelCharacterRepository {
     suspend fun getCharacterById(id: Int, getFromRemote: Boolean): Result<MarvelCharacter>
}
