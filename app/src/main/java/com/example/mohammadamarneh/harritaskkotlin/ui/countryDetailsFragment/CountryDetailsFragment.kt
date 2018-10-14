package com.example.mohammadamarneh.harritaskkotlin.ui.countryDetailsFragment

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mohammadamarneh.harritaskkotlin.R
import com.example.mohammadamarneh.harritaskkotlin.databinding.FragmentCountryDetailsBinding
import com.example.mohammadamarneh.harritaskkotlin.di.Injectable
import com.example.mohammadamarneh.harritaskkotlin.model.Country
import com.example.mohammadamarneh.harritaskkotlin.ui.utils.Resource
import com.example.mohammadamarneh.harritaskkotlin.viewModel.ViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class CountryDetailsFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding : FragmentCountryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("fragment created")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_details, container, false)
        return binding.root
    }

    fun showCountry(country: Country){
        binding.country = country
    }

}