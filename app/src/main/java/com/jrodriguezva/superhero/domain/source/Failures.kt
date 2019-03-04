package com.jrodriguezva.superhero.domain.source

sealed class GetSuperheroesFailure : Failure() {
    object NetworkConnection : GetSuperheroesFailure()
    object SuperheroesNotFound : GetSuperheroesFailure()
}

sealed class GetSuperheroFailure : Failure() {
    object SuperheroNotFound : GetSuperheroFailure()
    object NotImplementedError : GetSuperheroFailure()
}

sealed class Failure