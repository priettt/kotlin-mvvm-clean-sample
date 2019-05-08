package com.globant.data

import com.globant.domain.entities.MarvelCharacter
import io.reactivex.Observable

interface MarvelRepoInterface {
    fun getCharacterById(id: Int): Observable<MarvelCharacter>
}
