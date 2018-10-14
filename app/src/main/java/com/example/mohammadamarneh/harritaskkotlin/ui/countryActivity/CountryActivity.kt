package com.example.mohammadamarneh.harritaskkotlin.ui.countryActivity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.example.mohammadamarneh.harritaskkotlin.R
import com.example.mohammadamarneh.harritaskkotlin.databinding.ActivityCountryBinding
import com.example.mohammadamarneh.harritaskkotlin.model.Country
import com.example.mohammadamarneh.harritaskkotlin.ui.common.CountriesListAdapter
import com.example.mohammadamarneh.harritaskkotlin.ui.common.RetryCallback
import com.example.mohammadamarneh.harritaskkotlin.ui.countryDetailsFragment.CountryDetailsFragment
import com.example.mohammadamarneh.harritaskkotlin.utils.AppExecutors
import com.example.mohammadamarneh.harritaskkotlin.viewModel.ViewModelFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_country.*
import timber.log.Timber

import javax.inject.Inject

class CountryActivity : AppCompatActivity(), HasSupportFragmentInjector {

    private var countriesListAdapter : CountriesListAdapter? = null
    private var viewPager: androidx.viewpager.widget.ViewPager? = null
    private var weatherPagerAdapter: WeatherPagerAdapter? = null

    private var countryDetailsFragment: CountryDetailsFragment? = null

    @Inject
    lateinit var factory: ViewModelFactory
    private var viewModel: CountryActivityViewModel? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    @Inject
    lateinit var appExecutors: AppExecutors

    private lateinit var binding : ActivityCountryBinding

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_country)
        binding.retryCallback = object : RetryCallback{ override fun retry() = viewModel!!.retry() }
        binding.weatherCallback = object : RetryCallback{ override fun retry() = viewModel!!.retryWeather() }

        viewPager = findViewById(R.id.container)

        countryDetailsFragment = supportFragmentManager.findFragmentById(R.id.countryDetailFragment) as CountryDetailsFragment

        setupActionBar()

        setupTabsAndViewPager()

        initViewModel()
    }

    private fun setupActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupTabsAndViewPager() {
        weatherPagerAdapter = WeatherPagerAdapter(supportFragmentManager)
        viewPager!!.adapter = weatherPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        tabLayout.getTabAt(0)!!.text = getString(R.string.today)
        tabLayout.getTabAt(1)!!.text = getString(R.string.tomorrow)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(CountryActivityViewModel::class.java)
        viewModel!!.init()

        viewModel!!.resultLive!!.observe(this, Observer { countriesResource ->
            if (countriesResource != null) {
                if (countriesListAdapter == null) {
                    countriesListAdapter = CountriesListAdapter(appExecutors) { country -> onCountryClicked(country) }
                    countriesListAdapter!!.submitList(countriesResource.data ?: emptyList())
                    recyclerView.layoutManager = LinearLayoutManager(this@CountryActivity)
                    recyclerView.adapter = countriesListAdapter
                } else {
                    countriesListAdapter!!.submitList(countriesResource.data ?: emptyList())
                    //countriesListAdapter!!.notifyDataSetChanged()
                }

                //show details of first country
                if (countriesResource.data != null) {
                    val country = viewModel!!.selectedCountry ?: countriesResource.data[0]
                    onCountryClicked(country)
                }
            }

            binding.countriesResource = countriesResource
        })

        viewModel!!.weatherLive!!.observe(this, Observer{ weathersResource ->
            if (weathersResource?.data != null && weathersResource.data.isNotEmpty()) {
                weatherPagerAdapter!!.updateData(weathersResource.data)
            }
            binding.weatherResource = weathersResource
        })
    }

    override fun onBackPressed() {
        if (!hideDrawer())
            super.onBackPressed()
    }

    private fun hideDrawer(): Boolean {
        val drawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
        return if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            true
        } else {
            false
        }
    }


    private fun onCountryClicked(country: Country) {
        Timber.d("Amarneh, onCountryClicked, lat=${country.latlng!![0]}, long=${country.latlng!![1]}")
        countryDetailsFragment!!.showCountry(country)
        viewModel!!.getWeatherForTodayAndTomorrow(country.latlng!![0], country.latlng!![1])
        hideDrawer()
        viewModel!!.selectedCountry = country
    }
}
