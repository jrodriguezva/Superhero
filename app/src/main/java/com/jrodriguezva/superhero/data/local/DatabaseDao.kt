package com.jrodriguezva.superhero.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SuperheroDao : BaseDao<SuperheroModel> {

    @Query("SELECT * FROM SUPERHEROES WHERE id = :id")
    suspend fun getSuperhero(id: Long): SuperheroModel?

    @Query("SELECT * FROM SUPERHEROES")
    suspend fun getSuperheroes(): List<SuperheroModel>?

}