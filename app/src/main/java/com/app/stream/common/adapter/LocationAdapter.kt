package com.app.stream.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.stream.databinding.ItemNameLocationBinding
import com.app.stream.remote.model.ChannelCameraResponse

class LocationAdapter (private val items: List<ChannelCameraResponse>?,
                       private val onClick: (ChannelCameraResponse) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(
        val binding: ItemNameLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChannelCameraResponse?) = with(binding) {
            tvLocationName.text = item?.name
            tvCameraCount.text = "${item?.cameras?.count()} Kamera"
            root.setOnClickListener { item?.let { p1 -> onClick(p1) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemNameLocationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(items?.get(position))
    }

    override fun getItemCount(): Int = items?.size ?: 0
}