package com.app.stream.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.stream.databinding.ItemCameraBinding
import com.app.stream.model.CameraModel

class CameraAdapter(
    private val items: List<CameraModel>,
    private val onClick: (CameraModel) -> Unit
) : RecyclerView.Adapter<CameraAdapter.CameraViewHolder>() {

    inner class CameraViewHolder(
        val binding: ItemCameraBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CameraModel) = with(binding) {
            tvCameraName.text = item.name
            tvCameraLocation.text = item.location

            // Status
            if (item.isOnline) {
                tvCameraStatus.text = "● Online  •  ${item.id}"
                tvCameraStatus.setTextColor(Color.parseColor("#7CFF7C"))
            } else {
                tvCameraStatus.text = "● Offline  •  ${item.id}"
                tvCameraStatus.setTextColor(Color.parseColor("#FF6B6B"))
            }

            // Preview image
            imgPreview.setImageResource(item.previewRes)

            // REC badge
            tvRec.visibility = if (item.isRecording) View.VISIBLE else View.GONE

            root.setOnClickListener { onClick(item) }
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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
