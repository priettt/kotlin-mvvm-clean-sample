package com.globant.data.repositories.service.api

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.DataBaseResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.MarvelBaseResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface MarvelApiService {
    @GET("/v1/public/characters")
    fun getCharacter(): Deferred<MarvelBaseResponse<DataBaseResponse<List<CharacterResponse>>>>
}