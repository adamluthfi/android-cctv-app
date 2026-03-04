package com.app.stream.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.app.stream.databinding.ItemNameLocationBinding
import com.app.stream.remote.model.ChannelCameraResponse
import com.app.stream.remote.model.UserListResponse

class UsersAdapter (val items: MutableList<UserListResponse>?,
                    private val onClick: (UserListResponse) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    inner class UsersViewHolder(
        val binding: ItemNameLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserListResponse?) = with(binding) {
            tvLocationName.text = item?.username
            tvCameraCount.text = item?.role?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemNameLocationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsersViewHolder(binding)
    }


    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.itemView.scaleX = 1f
        holder.itemView.scaleY = 1f
        holder.bind(items?.get(position))
    }

    override fun getItemCount(): Int = items?.size ?: 0

    fun deleteItem(position: Int) {
        items?.removeAt(position)
        notifyItemRemoved(position)
    }
}