package com.globant.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.globant.data.database.dao.MarvelCharacterDao
import com.globant.data.database.entity.MarvelCharacterEntity

@Database(entities = [MarvelCharacterEntity::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "character-db"
    }

    abstract fun characterDao(): MarvelCharacterDao

}
