package com.violadin.debtorpit.ui.debtors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.databinding.RecyclerviewRowPersonBinding
import com.violadin.debtorpit.utils.longTimeToString

class DebtForMeAdapter(
    private val clickListener: (person: Person) -> Unit
) : ListAdapter<Person, DebtForMeAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerviewRowDebtForMeBinding = RecyclerviewRowPersonBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerviewRowDebtForMeBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecyclerviewRowPersonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Person) {
            with(binding) {
                debtorNameText.text = item.fio
                debtCount.text = item.debt.toString()
                if (item.createdTime != null) {
                    debtorDateText.text = longTimeToString(item.createdTime)
                }
                root.setOnClickListener {
                    clickListener(item)
                }
            }
        }
    }
}