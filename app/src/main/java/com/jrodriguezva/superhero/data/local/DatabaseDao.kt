package com.jrodriguezva.superhero.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SuperheroDao : BaseDao<SuperheroModel> {

    @Query("SELECT * FROM SUPERHEROES WHERE id = :id")
    fun getSuperhero(id: Long): SuperheroModel?

    @Query("SELECT * FROM SUPERHEROES")
    fun getSuperheroes(): List<SuperheroModel>?

}