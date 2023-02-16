package com.violadin.debtorpit.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.databinding.RecyclerRowHistoryBinding

class HistoryAdapter : ListAdapter<History, HistoryAdapter.ViewHolder>(object : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerviewRowHistoryBinding = RecyclerRowHistoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerviewRowHistoryBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecyclerRowHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: History) {

        }
    }
}