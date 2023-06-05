package com.globant.data.service.api

import com.globant.data.service.response.CharacterResponse
import com.globant.data.database.response.DataBaseResponse
import com.globant.data.service.response.MarvelBaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {

    @GET("/v1/public/characters/{characterId}")
    fun getCharacterById(@Path("characterId") id: Int):
            Call<MarvelBaseResponse<DataBaseResponse<ArrayList<CharacterResponse>>>>

}
