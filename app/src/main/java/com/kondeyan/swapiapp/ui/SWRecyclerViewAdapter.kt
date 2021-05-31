package com.kondeyan.swapiapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kondeyan.swapiapp.databinding.CellBinding
import com.kondeyan.swapiapp.ui.model.ViewData

interface IListItemClickListener {
    fun onItemClicked(data: ViewData, position: Int)
}

class SWRecyclerViewAdapter :
    PagingDataAdapter<ViewData, SWRecyclerViewAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ViewData>() {

            override fun areItemsTheSame(oldItem: ViewData, newItem: ViewData) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ViewData, newItem: ViewData) =
                oldItem == newItem
        }
    }

    private lateinit var binding: CellBinding

    var itemClickListenerInstance: IListItemClickListener? = null

    class ViewHolder(val binding: CellBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewData?) {
            item?.apply {
                binding.title.text = this.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.bind(item = this)
            holder.binding.title.text = this.title
            holder.itemView.setOnClickListener {
                itemClickListenerInstance?.onItemClicked(this, position)
            }
        }

    }
}