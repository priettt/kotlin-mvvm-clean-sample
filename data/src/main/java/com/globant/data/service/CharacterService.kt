package com.globant.data.service

import com.globant.data.MarvelRequestGenerator
import com.globant.data.ZERO
import com.globant.data.mapper.CharacterMapperService
import com.globant.data.service.api.MarvelApi
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

class CharacterService {

    private val api: MarvelRequestGenerator = MarvelRequestGenerator()
    private val mapper: CharacterMapperService = CharacterMapperService()

    fun getCharacterById(id: Int): Result<MarvelCharacter> {
        val callResponse = api.createService(MarvelApi::class.java).getCharacterById(id)
        val response = callResponse.execute()
        if (response != null) {
            if (response.isSuccessful) {
                response.body()?.data?.characters?.get(ZERO)?.let { mapper.transform(it) }?.let { return Result.Success(it) }
            }
            return Result.Failure(Exception(response.message()))
        }
        return Result.Failure(Exception("Bad request/response"))
    }

}
