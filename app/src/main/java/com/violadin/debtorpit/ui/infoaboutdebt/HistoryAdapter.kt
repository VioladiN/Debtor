package com.violadin.debtorpit.ui.infoaboutdebt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.databinding.RecyclerviewRowHistoryProfileBinding
import com.violadin.debtorpit.enums.HistoryType
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HistoryAdapter :
    ListAdapter<History, HistoryAdapter.ViewHolder>(object : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerviewRowHistoryProfileBinding =
            RecyclerviewRowHistoryProfileBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerviewRowHistoryProfileBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecyclerviewRowHistoryProfileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            with(binding) {
                debtDescriptionText.text = history.description
                createdDateText.text = Instant.ofEpochMilli(history.createdTime!!).atZone(ZoneId.systemDefault())
                    .toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                when (history.debtType) {
                    HistoryType.INCREASE.type -> {
                        imageAmountType.setBackgroundResource(R.drawable.ic_plus_green)
                    }
                    HistoryType.DECREASE.type -> {
                        imageAmountType.setBackgroundResource(R.drawable.ic_minus_green)
                    }
                    else -> throw IllegalStateException("Illegal state of HistoryAdapter")
                }
                debtAmountText.text = history.amount.toString()
            }
        }
    }
}