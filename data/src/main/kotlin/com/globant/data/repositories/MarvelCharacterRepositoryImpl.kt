package com.globant.data.repositories

import com.globant.data.MarvelResquestGenerator
import com.globant.data.ZERO
import com.globant.data.mapper.CharacterMapperService
import com.globant.data.service.api.MarvelApi
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepository
import com.globant.domain.utils.Result

class MarvelCharacterRepositoryImpl : MarvelCharacterRepository {

    private val api: MarvelResquestGenerator = MarvelResquestGenerator()
    private val mapper: CharacterMapperService = CharacterMapperService()

    override fun getCharacterById(id: Int): Result<MarvelCharacter> {
        val callResponse = api.createService(MarvelApi::class.java).getCharacterById(id)
        val response = callResponse.execute()
        if (response.isSuccessful) {
            response.body()?.data?.characters?.get(ZERO)?.let { mapper.transform(it) }
                ?.let { return Result.Success(it) }
        }
        return Result.Failure(Exception(response.message()))
    }
}
