package com.puzzlebench.clean_marvel_kotlin.data.service.response

/**
 * Created by andres.serrano on 8/01/2018.
 */
class MarvelBaseResponse<T>(

        var code: Int,
        var status: String,
        var data: T?
)