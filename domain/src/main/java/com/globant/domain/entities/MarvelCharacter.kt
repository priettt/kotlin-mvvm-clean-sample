package com.globant.domain.entities

val NOT_FOUND = "NOT FOUND"
val DEFAULT_ID = 0

open class MarvelCharacter(
        val id: Int = DEFAULT_ID,
        val name: String = NOT_FOUND,
        val description: String = NOT_FOUND
)
