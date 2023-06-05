package com.globant.data.database.response

import com.google.gson.annotations.SerializedName

class DataBaseResponse<T>(
    @SerializedName("results")
    val results: T,
    val offset: Int,
    val limit: Int,
    val total: Int
)
