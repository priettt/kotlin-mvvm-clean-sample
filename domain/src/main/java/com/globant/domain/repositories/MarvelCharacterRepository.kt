package com.globant.domain.repositories

import com.globant.domain.entities.MarvelCharacter

interface MarvelCharacterRepository {
    fun getCharacterById(id: Int): MarvelCharacter
}