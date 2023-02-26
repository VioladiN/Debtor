package com.violadin.debtorpit.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.tables.History
import com.violadin.debtorpit.databinding.RecyclerRowHistoryBinding
import com.violadin.debtorpit.enums.HistoryType
import com.violadin.debtorpit.utils.DAY_MONTH_YEAR_PATTERN
import com.violadin.debtorpit.utils.longTimeToString

class HistoryAdapter(
    private val context: Context
) :
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
                        debtAmountText.text =
                            StringBuilder().append(context.getString(R.string.plus))
                                .append(item.amount)
                    }
                    HistoryType.DECREASE.type -> {
                        debtAmountText.text =
                            StringBuilder().append(context.getString(R.string.minus))
                                .append(item.amount)
                    }
                    else -> throw IllegalStateException("Illegal state of HistoryAdapter")
                }
                item.createdTime?.let {
                    createdDateText.text =
                        longTimeToString(item.createdTime, DAY_MONTH_YEAR_PATTERN)
                }
            }
        }
    }
}