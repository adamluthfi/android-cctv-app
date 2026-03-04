package com.app.stream.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.app.stream.R
import com.app.stream.databinding.ItemCameraBinding
import com.app.stream.remote.model.Camera

class CameraAdapter(
    private val items: List<Camera>?,
    private val onClick: (Camera) -> Unit
) : RecyclerView.Adapter<CameraAdapter.CameraViewHolder>() {

    inner class CameraViewHolder(
        val binding: ItemCameraBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Camera?) = with(binding) {
            tvCameraName.text = item?.name
            tvCameraLocation.text = ""
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraViewHolder {
        val binding = ItemCameraBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CameraViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CameraViewHolder, position: Int) {
        holder.bind(items?.get(position))
    }

    override fun getItemCount(): Int = items?.size ?: 0
}
