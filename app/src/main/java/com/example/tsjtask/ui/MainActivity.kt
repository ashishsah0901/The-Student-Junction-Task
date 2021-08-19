package com.example.tsjtask.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tsjtask.R
import com.example.tsjtask.databinding.ActivityMainBinding
import com.example.tsjtask.repository.PersonRepository
import com.example.tsjtask.util.Constants.COUNT
import com.example.tsjtask.viewmodel.PersonViewModel
import com.example.tsjtask.viewmodel.PersonViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var viewModel: PersonViewModel
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_TSJTask)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F1B234")))
        val repository = PersonRepository()
        val viewModelProviderFactory = PersonViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(PersonViewModel::class.java)
        if(viewModel.result.isNullOrEmpty()) {
            viewModel.getRandomPerson(COUNT)
        }
    }
}