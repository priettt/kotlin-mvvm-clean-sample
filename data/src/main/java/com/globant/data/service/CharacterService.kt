package com.globant.data.service

import android.content.Context
import com.globant.data.MarvelRequestGenerator
import com.globant.data.ZERO
import com.globant.data.mapper.CharacterMapperService
import com.globant.data.service.api.MarvelApi
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.utils.Result

class CharacterService(context: Context) {

    private val api: MarvelRequestGenerator = MarvelRequestGenerator(context)
    private val mapper: CharacterMapperService = CharacterMapperService()

    fun getCharacterById(id: Int): Result<MarvelCharacter> {
        val callResponse = api.createService(MarvelApi::class.java).getCharacterById(id)
        val response = callResponse.execute()
        if (response.isSuccessful) {
            response.body()?.data?.characters?.get(ZERO)?.let { mapper.transform(it) }?.let { return Result.Success(it) }
        }
        return Result.Failure(Exception(response.message()))
    }

}
