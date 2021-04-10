package com.amit.kotlinrecyclercarousel.network

import com.amit.kotlinrecyclercarousel.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "ac4f9dccb02a4335bc7b1e3fda12e7aa"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadLines(@Query("country") country: String,
                     @Query("page") page: String): Call<News>
}