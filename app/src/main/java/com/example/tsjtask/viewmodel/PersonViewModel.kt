package com.example.tsjtask.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tsjtask.PersonApplication
import com.example.tsjtask.repository.PersonRepository
import com.example.tsjtask.data.Person
import com.example.tsjtask.data.Result
import com.example.tsjtask.util.Resource
import kotlinx.coroutines.launch

class PersonViewModel(private val repository: PersonRepository, application: Application) : AndroidViewModel(application){
    private val _personLiveData: MutableLiveData<Resource<Person>> = MutableLiveData()
    val person: LiveData<Resource<Person>> = _personLiveData
    var result: List<Result>? = null

    fun getRandomPerson(count: Int) = viewModelScope.launch {
        _personLiveData.postValue(Resource.Loading())
        if(hasInternetConnection()) {
            val response = repository.getPersonFromApi(count)
            if(response.isSuccessful){
                response.body()?.let {
                    result = it.results
                    _personLiveData.postValue(Resource.Success(it))
                }
            } else {
                _personLiveData.postValue(Resource.Error(response.message()))
            }
        } else {
            _personLiveData.postValue(Resource.Error("No Internet Connection"))
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<PersonApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}