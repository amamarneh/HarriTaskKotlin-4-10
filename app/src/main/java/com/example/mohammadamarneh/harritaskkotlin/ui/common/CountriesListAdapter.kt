package com.example.mohammadamarneh.harritaskkotlin.ui.common

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.mohammadamarneh.harritaskkotlin.R
import com.example.mohammadamarneh.harritaskkotlin.databinding.ItemCountryBinding
import com.example.mohammadamarneh.harritaskkotlin.model.Country
import com.example.mohammadamarneh.harritaskkotlin.utils.AppExecutors

/**
 * A RecyclerView adapter for [Country] class.
 */

class CountriesListAdapter (
        appExecutors: AppExecutors,
        private val countryClickCallback: ((Country) -> Unit)?
) : DataBoundListAdapter<Country, ItemCountryBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem.alpha2Code == newItem.alpha2Code
                        && oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem.name == newItem.name
                        && oldItem.region == newItem.region
                        && oldItem.alpha2Code == newItem.alpha2Code
            }
        })
{

    override fun createBinding(parent: ViewGroup): ItemCountryBinding {
        val binding = DataBindingUtil.inflate<ItemCountryBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_country,
                parent,
                false
        )
        binding.root.setOnClickListener {
            binding.bean?.let {
                countryClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemCountryBinding, item: Country) {
        binding.bean = item
    }
}
