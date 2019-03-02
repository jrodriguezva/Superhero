package com.jrodriguezva.superhero.domain.source

sealed class GetSuperheroesFailure {
    class NetworkConnection : GetSuperheroesFailure()
    class SuperheroesNotFound : GetSuperheroesFailure()
}

sealed class GetSuperheroFailure {
    class SuperheroNotFound : GetSuperheroFailure()
    class NotImplementedError : GetSuperheroFailure()
}