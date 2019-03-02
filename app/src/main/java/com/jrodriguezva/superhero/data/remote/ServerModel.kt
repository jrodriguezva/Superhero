package com.jrodriguezva.superhero.data.remote

import com.squareup.moshi.Json

data class SuperheroResponse(
    @Json(name = "name")
    var name: String,
    @Json(name = "photo")
    var photo: String,
    @Json(name = "realName")
    var realName: String,
    @Json(name = "height")
    var height: String,
    @Json(name = "power")
    var power: String,
    @Json(name = "abilities")
    var abilities: String,
    @Json(name = "groups")
    var groups: String
)

data class SuperheroesResponse(
    @Json(name = "superheroes")
    var superheroes: List<SuperheroResponse>
)