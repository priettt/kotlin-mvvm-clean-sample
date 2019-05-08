package com.globant.data.response

class MarvelBaseResponse<T>(

        var code: Int,
        var status: String,
        var data: T?
)
