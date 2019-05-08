package com.globant.domain.repositories

import com.globant.domain.entities.MarvelCharacter

interface MarvelCharacterRepositoryContract {
    fun getCharacterById(id: Int): MarvelCharacter
}