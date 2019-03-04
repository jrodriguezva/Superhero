package com.jrodriguezva.superhero.domain.model

import com.jrodriguezva.superhero.testing.OpenForTesting

@OpenForTesting
data class Superhero(
    var id: Long? = null,
    var name: String,
    var photo: String,
    var realName: String,
    var height: String,
    var power: String,
    var abilities: String,
    var groups: String
)