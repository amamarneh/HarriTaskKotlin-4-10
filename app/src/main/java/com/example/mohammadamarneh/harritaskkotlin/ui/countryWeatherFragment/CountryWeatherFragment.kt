package com.example.mohammadamarneh.harritaskkotlin.ui.countryWeatherFragment

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mohammadamarneh.harritaskkotlin.R
import com.example.mohammadamarneh.harritaskkotlin.databinding.FragmentCountryWeatherBinding
import com.example.mohammadamarneh.harritaskkotlin.di.Injectable
import com.example.mohammadamarneh.harritaskkotlin.model.Weather
import com.example.mohammadamarneh.harritaskkotlin.viewModel.ViewModelFactory
import timber.log.Timber
import javax.inject.Inject

class CountryWeatherFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding : FragmentCountryWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("fragment created")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_weather, container, false)
        return binding.root
    }

    fun showWeather(weather: Weather) {
        binding.weather = weather
    }

    companion object {

        fun newInstance(): CountryWeatherFragment {
            val fragment = CountryWeatherFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
