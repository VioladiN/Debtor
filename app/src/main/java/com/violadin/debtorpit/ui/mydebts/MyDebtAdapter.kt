package com.violadin.debtorpit.ui.mydebts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.databinding.RecyclerviewRowDebtForMeBinding

class MyDebtAdapter(
    private val clickListener: (person: Person) -> Unit
) : ListAdapter<Person, MyDebtAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerviewRowDebtForMeBinding = RecyclerviewRowDebtForMeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerviewRowDebtForMeBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecyclerviewRowDebtForMeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Person) {
            with(binding) {
                debtorNameText.text = item.fio
                debtCount.text = item.debt.toString()
                debtorDateText.text = item.created_time

                root.setOnClickListener {
                    clickListener(item)
                }
            }
        }
    }
}