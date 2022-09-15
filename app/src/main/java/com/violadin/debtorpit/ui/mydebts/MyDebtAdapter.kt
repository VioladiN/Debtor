package com.violadin.debtorpit.ui.mydebts

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import com.violadin.debtorpit.ui.debtors.DebtForMeAdapter
import com.violadin.debtorpit.ui.fragment.BottomSheetInfoPersonMyDebtFragment
import kotlinx.android.synthetic.main.recyclerview_row_debt_for_me.view.*

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
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row_debt_for_me, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    inner class ViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Person) {

            view.debtor_name_text.text = item.fio
            view.debt_count.text = item.debt.toString()

            view.setOnClickListener {
                clickListener(item)
            }
        }
    }
}