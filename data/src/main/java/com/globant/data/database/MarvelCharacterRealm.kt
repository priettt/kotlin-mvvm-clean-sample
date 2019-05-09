package com.globant.data.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

const val EMPTY_STRING = ""
const val DEFAULT_ID = 0

open class MarvelCharacterRealm(
        @PrimaryKey
        var id: Int = DEFAULT_ID,
        var name: String = EMPTY_STRING,
        var description: String = EMPTY_STRING
) : RealmObject()
