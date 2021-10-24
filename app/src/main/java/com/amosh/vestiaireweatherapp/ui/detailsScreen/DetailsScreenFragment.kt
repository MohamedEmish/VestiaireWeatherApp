package com.amosh.vestiaireweatherapp.ui.detailsScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.amosh.vestiaireweatherapp.R
import com.amosh.vestiaireweatherapp.databinding.FragmentDetailsScreenBinding
import com.amosh.vestiaireweatherapp.models.response.ForecastData
import com.amosh.vestiaireweatherapp.ui.BaseFragment
import com.amosh.vestiaireweatherapp.utils.Constants
import com.amosh.vestiaireweatherapp.utils.Constants.DATA
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailsScreenFragment : BaseFragment() {
    private var _binding: FragmentDetailsScreenBinding? = null
    private val binding get() = _binding!!
    private var data: ForecastData? = null

    private val sdf by lazy {
        SimpleDateFormat(Constants.DETAILS_DATE, Locale.US)
    }

    override fun getLayoutRoot(inflater: LayoutInflater): View {
        _binding = FragmentDetailsScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onBindingDestroy() {
        _binding = null
    }

    override fun afterCreation(bundle: Bundle?) {
        arguments?.let {
            data = it[DATA] as ForecastData?
        }

        if (data == null) requireActivity().onBackPressed()
        else setData()
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        with(binding) {
            tvDate.text = sdf.format(data?.dt?.times(1000) ?: Date().time)
            tvDegree.text = "${data?.temp?.avgTemp}°"
            tvStatus.text = data?.weather?.get(0)?.description
            tvHigherLower.text =
                "${data?.temp?.max?.roundToInt()}°/${data?.temp?.min?.roundToInt()}°"
            tvState.apply {
                text = getString(
                    when {
                        data?.temp?.avgTemp == null -> R.string.cool
                        data!!.temp!!.avgTemp > 25 -> R.string.hot
                        data!!.temp!!.avgTemp < 10 -> R.string.cold
                        else -> R.string.cool
                    }
                )
                setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    when {
                        data?.temp?.avgTemp == null -> R.drawable.ic_day
                        data!!.temp!!.avgTemp > 25 -> R.drawable.ic_hot
                        data!!.temp!!.avgTemp < 10 -> R.drawable.ic_cold
                        else -> R.drawable.ic_day
                    },
                    0,
                    0
                )

            }
            tvFeelsLike.text = data?.feels_like?.avgFeel?.toString()
            tvHumidity.text = data?.humidity?.toString()
            tvPressure.text = data?.pressure?.toString()
            tvSpeed.text = data?.speed?.toString()
        }
    }

}