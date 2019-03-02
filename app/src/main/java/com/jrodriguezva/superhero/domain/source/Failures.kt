package com.jrodriguezva.superhero.domain.source

sealed class GetSuperheroesFailure {
    object NetworkConnection : GetSuperheroesFailure()
    object SuperheroesNotFound : GetSuperheroesFailure()
}

sealed class GetSuperheroFailure {
    object SuperheroNotFound : GetSuperheroFailure()
    object NotImplementedError : GetSuperheroFailure()
}