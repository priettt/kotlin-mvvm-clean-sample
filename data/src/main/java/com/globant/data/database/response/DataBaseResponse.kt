package com.globant.data.database.response

import com.globant.data.service.response.CharacterResponse
import com.google.gson.annotations.SerializedName

class DataBaseResponse<T>(
        @SerializedName("results") val characters: List<CharacterResponse>,
        val offset: Int,
        val limit: Int,
        val total: Int
)
