package com.jrodriguezva.superhero.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface WebService {

    @GET("bins/bvyob")
    fun getHeroes(): Call<SuperheroesResponse>

}