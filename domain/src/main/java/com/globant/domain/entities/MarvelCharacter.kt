package com.globant.domain.entities

const val NOT_FOUND = "NOT FOUND"
const val DEFAULT_ID = 0

class MarvelCharacter(
    val id: Int = DEFAULT_ID,
    val name: String = NOT_FOUND,
    val description: String = NOT_FOUND
)
