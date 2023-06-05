package com.globant.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.globant.data.database.entity.MarvelCharacterEntity

@Dao
interface MarvelCharacterDao {

    @Query("SELECT * FROM character WHERE id IN (:id)")
    fun getById(id: Int): MarvelCharacterEntity

    @Insert
    fun insertOrUpdate(character: MarvelCharacterEntity)

}