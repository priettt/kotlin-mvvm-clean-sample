package com.globant.data

import com.globant.data.api.MarvelApi
import com.globant.data.mapper.CharacterMapperService
import com.globant.domain.entities.MarvelCharacter
import io.reactivex.Observable

class FindCharacterImpl(
        private val api: MarvelResquestGenerator = MarvelResquestGenerator(),
        private val mapper: CharacterMapperService = CharacterMapperService()
) : MarvelRepoInterface {

    override fun getCharacterById(id: Int): Observable<MarvelCharacter> {
        return Observable.create { subscriber ->
            val callResponse = api.createService(MarvelApi::class.java).getCharacterById(id)
            val response = callResponse.execute()
            if (response.isSuccessful) {
                response.body()?.data?.characters?.get(ZERO)?.let { mapper.transform(it) }
                    ?.let { subscriber.onNext(it) }
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}

