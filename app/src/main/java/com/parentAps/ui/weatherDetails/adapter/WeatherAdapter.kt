package com.parentAps.ui.weatherDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parentAps.databinding.ListItemThemeBinding
import com.parentAps.ui.data.model.WeatherInfo

class WeatherAdapter(private val weatherInfoList: List<WeatherInfo>) :
    ListAdapter<WeatherInfo, WeatherAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherInfo = getItem(position)
        holder.apply {
            bind(weatherInfo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemThemeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItem(position: Int): WeatherInfo {
        return weatherInfoList[position]
    }

    override fun getItemCount(): Int {
        return weatherInfoList.size
    }

    class ViewHolder(
        private val binding: ListItemThemeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherInfo) {
            binding.apply {
                weatherInfo = item
                executePendingBindings()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<WeatherInfo>() {

    override fun areItemsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
        return oldItem.dtTxt == newItem.dtTxt
    }

    override fun areContentsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
        return oldItem.dtTxt == newItem.dtTxt
    }
}