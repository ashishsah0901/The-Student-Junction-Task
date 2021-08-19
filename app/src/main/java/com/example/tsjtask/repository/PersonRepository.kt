package com.example.tsjtask.repository

import com.example.tsjtask.api.RetrofitInstance
import com.example.tsjtask.data.Person
import retrofit2.Response

class PersonRepository {
    suspend fun getPersonFromApi(count: Int) : Response<Person> {
        return RetrofitInstance.api.getRandomPerson(count)
    }
}