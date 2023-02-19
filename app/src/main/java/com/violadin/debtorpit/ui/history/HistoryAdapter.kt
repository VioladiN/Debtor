package com.violadin.debtorpit.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.databinding.RecyclerRowHistoryBinding
import com.violadin.debtorpit.enums.HistoryType
import java.time.Instant
import java.time.LocalDate
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
        val recyclerviewRowHistoryBinding =
            RecyclerRowHistoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerviewRowHistoryBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecyclerRowHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: History) {
            with(binding) {
                nameDebtText.text = item.personName
                when (item.debtType) {
                    HistoryType.INCREASE.type -> {
                        debtAmountText.text = "+${item.amount}"
                    }
                    HistoryType.DECREASE.type -> {
                        debtAmountText.text = "-${item.amount}"
                    }
                    else -> throw IllegalStateException("Illegal state of HistoryAdapter")
                }
                createdDateText.text =
                    Instant.ofEpochMilli(item.createdTime!!).atZone(ZoneId.systemDefault())
                        .toLocalDate().format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    )
            }
        }
    }
}