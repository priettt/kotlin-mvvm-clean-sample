package com.globant.data.service.response

class MarvelBaseResponse<T>(

        var code: Int,
        var status: String,
        var data: T?
)
