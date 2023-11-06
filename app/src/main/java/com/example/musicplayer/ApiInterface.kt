package com.example.musicplayer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("X-RapidAPI-Key: 9b6e96ce4fmsh7d620cc50432e0ep13826cjsnc02f30a44139",
    "X-RapidAPI-Host': 'deezerdevs-deezer.p.rapidapi.com")

    @GET("search")
    fun getData(@Query("q") query: String): Call<MusicSearch>



}