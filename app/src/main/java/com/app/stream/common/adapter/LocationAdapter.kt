package com.app.stream.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.stream.databinding.ItemNameLocationBinding
import com.app.stream.remote.model.ChannelCameraResponse
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class LocationAdapter (private val items: List<ChannelCameraResponse>?,
                       private val onClick: (ChannelCameraResponse) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(
        val binding: ItemNameLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChannelCameraResponse?) = with(binding) {
            tvLocationName.text = item?.name
            if (item?.cameras == null) {
                tvCameraCount.text = "6 Kamera"
            } else {
                tvCameraCount.text = "${item?.cameras?.count()} Kamera"
            }

            root.setOnClickListener { view ->
                // 🔥 Animation
                view.animate()
                    .scaleX(0.96f)
                    .scaleY(0.96f)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .setDuration(120)
                    .withEndAction {
                        view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(120)
                            .start()

                        // 🔥 Trigger click AFTER animation
                        item?.let { onClick(it) }
                    }.start()
            }
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
        holder.itemView.scaleX = 1f
        holder.itemView.scaleY = 1f
        holder.bind(items?.get(position))
    }

    override fun getItemCount(): Int = items?.size ?: 0
}