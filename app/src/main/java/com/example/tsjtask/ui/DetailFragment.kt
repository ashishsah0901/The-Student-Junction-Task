package com.example.tsjtask.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tsjtask.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding!!

    private val navigationArguments: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = navigationArguments.navigationArgs
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showLandscape()
        } else if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showPortrait()
        }
        binding.detailPersonNameTV.text = "${result.name.title}. ${result.name.first} ${result.name.last}"
        binding.detailPersonAgeTV.text = "${result.dob.age} years old"
        binding.detailPersonGenderTV.text = result.gender
        binding.detailPersonEmailTV.text = result.email
        binding.detailPersonAddressTV.text = "${result.location.state}, ${result.location.country}"
        binding.detailPersonNameTV2.text = "${result.name.title}. ${result.name.first} ${result.name.last}"
        binding.detailPersonAgeTV2.text = "${result.dob.age} years old"
        binding.detailPersonGenderTV2.text = result.gender
        binding.detailPersonEmailTV2.text = result.email
        binding.detailPersonAddressTV2.text = "${result.location.state}, ${result.location.country}"
        Glide.with(this)
            .load(result.picture.large)
            .into(binding.detailPersonImageIV)
        Glide.with(this)
                .load(result.picture.large)
                .into(binding.detailPersonImageIV2)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            findNavController().navigateUp()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showPortrait()
        } else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showLandscape()
        }
    }

    private fun showLandscape() {
        binding.detailPersonNameTV.isVisible = false
        binding.detailPersonAgeTV.isVisible = false
        binding.detailPersonGenderTV.isVisible = false
        binding.detailPersonEmailTV.isVisible = false
        binding.detailPersonAddressTV.isVisible = false
        binding.detailPersonImageIV.isVisible = false
        binding.detailPersonNameTV2.isVisible = true
        binding.detailPersonAgeTV2.isVisible = true
        binding.detailPersonGenderTV2.isVisible = true
        binding.detailPersonEmailTV2.isVisible = true
        binding.detailPersonAddressTV2.isVisible = true
        binding.detailPersonImageIV2.isVisible = true
    }

    private fun showPortrait() {
        binding.detailPersonNameTV2.isVisible = false
        binding.detailPersonAgeTV2.isVisible = false
        binding.detailPersonGenderTV2.isVisible = false
        binding.detailPersonEmailTV2.isVisible = false
        binding.detailPersonAddressTV2.isVisible = false
        binding.detailPersonImageIV2.isVisible = false
        binding.detailPersonNameTV.isVisible = true
        binding.detailPersonAgeTV.isVisible = true
        binding.detailPersonGenderTV.isVisible = true
        binding.detailPersonEmailTV.isVisible = true
        binding.detailPersonAddressTV.isVisible = true
        binding.detailPersonImageIV.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}