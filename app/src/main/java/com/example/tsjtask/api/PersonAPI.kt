package com.example.tsjtask.api

import com.example.tsjtask.data.Person
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonAPI {
    @GET("api/?")
    suspend fun getRandomPerson(
        @Query("results")
        result: Int
    ) : Response<Person>
}