package com.globant.data.api

import com.globant.data.response.CharacterResponse
import com.globant.data.response.DataBaseResponse
import com.globant.data.response.MarvelBaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {
    @GET("/v1/public/characters/{characterId}")
    fun getCharacterById(@Path("characterId")id: Int): Call<MarvelBaseResponse<DataBaseResponse<ArrayList<CharacterResponse>>>>
}
