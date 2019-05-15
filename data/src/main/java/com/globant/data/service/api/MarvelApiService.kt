package com.globant.data.service.api

import com.globant.data.service.response.CharacterResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiService {
    @GET("/v1/public/characters/{characterId}")
    fun getCharacterByIdAsync(@Path("characterId") id: Int): Deferred<CharacterResponse>
}
