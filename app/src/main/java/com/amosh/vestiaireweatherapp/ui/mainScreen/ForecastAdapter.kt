package com.amosh.vestiaireweatherapp.ui.mainScreen

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amosh.vestiaireweatherapp.R
import com.amosh.vestiaireweatherapp.databinding.ItemForecastBinding
import com.amosh.vestiaireweatherapp.models.response.ForecastData
import com.amosh.vestiaireweatherapp.utils.Constants
import com.amosh.vestiaireweatherapp.utils.isTomorrow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class ForecastAdapter(val listener: ActionsListener) :
    ListAdapter<ForecastData, ForecastAdapter.ViewHolder>(DIFF_CALLBACK) {

    val sdf by lazy {
        SimpleDateFormat(Constants.SIMPLE_DATE, Locale.US)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bindTo(item)
    }

    inner class ViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindTo(item: ForecastData) {
            with(binding) {
                tvDate.text = when {
                    DateUtils.isToday(item.dt?.times(1000) ?: Date().time)
                            || (item.dt?.times(1000)
                        ?: Date().time).isTomorrow() -> DateUtils.getRelativeTimeSpanString(
                        item.dt?.times(1000) ?: Date().time,
                        Date().time,
                        DateUtils.DAY_IN_MILLIS
                    )
                    else -> sdf.format(item.dt?.times(1000) ?: Date().time)
                }

                ivStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        root.context,
                        when {
                            item.temp?.avgTemp == null -> R.drawable.ic_day
                            item.temp.avgTemp > 25 -> R.drawable.ic_hot
                            item.temp.avgTemp < 10 -> R.drawable.ic_cold
                            else -> R.drawable.ic_day
                        }
                    )
                )

                tvHigherLower.text =
                    "${item.temp?.max?.roundToInt()}°/${item.temp?.min?.roundToInt()}°"

                root.setOnClickListener {
                    listener.onItemClicked(item)
                }
            }
        }
    }

    interface ActionsListener {
        fun onItemClicked(item: ForecastData)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ForecastData> =
            object : DiffUtil.ItemCallback<ForecastData>() {
                override fun areItemsTheSame(
                    oldItem: ForecastData,
                    newItem: ForecastData
                ): Boolean =
                    oldItem.pressure == newItem.pressure
                            && oldItem.clouds == oldItem.clouds

                override fun areContentsTheSame(
                    oldItem: ForecastData,
                    newItem: ForecastData
                ): Boolean =
                    oldItem.pressure == newItem.pressure
                            && oldItem.clouds == oldItem.clouds
            }
    }
}
