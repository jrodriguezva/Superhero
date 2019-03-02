package com.jrodriguezva.superhero.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "superheroes")
data class SuperheroModel(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var name: String,
    var photo: String,
    var realName: String,
    var height: String,
    var power: String,
    var abilities: String,
    var groups: String
)