package com.jrodriguezva.superhero.data.remote

import com.jrodriguezva.superhero.domain.model.Superhero

fun SuperheroResponse.convertToDomain() = run {
     Superhero(null, name, photo, realName, height, power, abilities, groups)
}