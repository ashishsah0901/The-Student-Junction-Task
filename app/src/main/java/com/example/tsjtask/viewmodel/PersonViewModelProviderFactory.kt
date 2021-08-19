package com.example.tsjtask.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tsjtask.repository.PersonRepository

class PersonViewModelProviderFactory(private val application: Application, private val repository: PersonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonViewModel(repository, application) as T
    }
}