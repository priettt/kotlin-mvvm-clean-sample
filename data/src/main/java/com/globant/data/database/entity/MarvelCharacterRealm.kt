package com.globant.data.database.entity

import com.globant.data.EMPTY_STRING
import com.globant.data.DEFAULT_ID
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MarvelCharacterRealm(
        @PrimaryKey
        var id: Int = DEFAULT_ID,
        var name: String = EMPTY_STRING,
        var description: String = EMPTY_STRING
) : RealmObject()
