package com.globant.data.repositories

import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.repositories.MarvelCharacterRepositoryContract

class MarvelCharacterRepository : MarvelCharacterRepositoryContract {
    override fun getCharacterById(id: Int): MarvelCharacter {
        return MarvelCharacter(1, "hola", "hola")
    }
}