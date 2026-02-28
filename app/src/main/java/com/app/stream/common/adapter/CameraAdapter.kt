package com.app.stream.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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

            // Status
//            if (item.isOnline) {
//                tvCameraStatus.text = "● Online  •  ${item.id}"
//                tvCameraStatus.setTextColor(Color.parseColor("#7CFF7C"))
//            } else {
//                tvCameraStatus.text = "● Offline  •  ${item.id}"
//                tvCameraStatus.setTextColor(Color.parseColor("#FF6B6B"))
//            }

            // Preview image
            imgPreview.setImageResource(R.drawable.img_camera_placeholder)

            // REC badge
            //tvRec.visibility = if (item.isRecording) View.VISIBLE else View.GONE

            root.setOnClickListener { item?.let { p1 -> onClick(p1) } }
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
