package com.globant.data.service

import com.globant.data.MarvelRequestGenerator
import com.globant.data.mapper.CharacterMapperService
import com.globant.data.service.api.MarvelApiService
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

class CharacterService {

    private val api: MarvelRequestGenerator = MarvelRequestGenerator()
    private val mapper: CharacterMapperService = CharacterMapperService()

    suspend fun getCharacterById(id: Int): Result<MarvelCharacter> {
        val callResponse = api.createService(MarvelApiService::class.java).getCharacterByIdAsync(id).await()
        return Result.Success(mapper.transform(callResponse))
    }
}
