package com.inhouse.catastrophic.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.inhouse.catastrophic.R
import com.inhouse.catastrophic.databinding.GridItemBinding
import com.inhouse.catastrophic.ui.data.Cat

class PhotoAdapter(private val clickListener: OnClickListener) :
    ListAdapter<Cat, PhotoAdapter.PhotoViewHolder>(CatItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    inner class PhotoViewHolder(private val binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cat, clickListener: OnClickListener) {
            binding.cat = cat
            binding.clickListener = clickListener
            binding.ivPhoto.load(cat.url) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            binding.executePendingBindings()
        }
    }

    interface OnClickListener {
        fun onClick(cat: Cat)
    }

    class CatItemDiffCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean =
            oldItem.id == newItem.id

    }

}
