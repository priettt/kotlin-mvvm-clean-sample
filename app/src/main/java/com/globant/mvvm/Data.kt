package com.globant.mvvm

/**
 * A generic wrapper class around data request
 */
data class Data<RequestData>(var responseType: Status, var data: RequestData? = null, var error: Throwable? = null)

enum class Status { SUCCESSFUL, ERROR, LOADING }
