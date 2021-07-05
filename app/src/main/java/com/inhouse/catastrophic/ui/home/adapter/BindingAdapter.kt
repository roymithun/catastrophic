package com.inhouse.catastrophic.ui.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.catastrophic.ui.data.Cat

@BindingAdapter("photoList")
fun bindDataToRecyclerView(recyclerView: RecyclerView, list: List<Cat>?) {
    list?.let {
        (recyclerView.adapter as PhotoAdapter).apply {
            submitList(currentList + it)
        }
    }
}