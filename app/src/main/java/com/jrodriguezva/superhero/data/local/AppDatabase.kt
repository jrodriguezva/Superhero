package com.jrodriguezva.superhero.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SuperheroModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun superheroDao(): SuperheroDao
}