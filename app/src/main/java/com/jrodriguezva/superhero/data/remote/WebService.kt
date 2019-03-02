package com.jrodriguezva.superhero.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface WebService {

    @GET("bins/bvyob")
    suspend fun getHeroes(): Response<SuperheroesResponse>

}