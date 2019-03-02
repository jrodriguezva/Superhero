package com.jrodriguezva.superhero.data.local

import com.jrodriguezva.superhero.domain.model.Superhero

fun Superhero.convertFromDomain(): SuperheroModel = run {
    SuperheroModel(id, name, photo, realName, height, power, abilities, groups)
}

fun SuperheroModel.convertToDomain() = run {
    Superhero(id, name, photo, realName, height, power, abilities, groups)
}