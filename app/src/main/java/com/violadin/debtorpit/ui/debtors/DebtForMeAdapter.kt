package com.violadin.debtorpit.ui.debtors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.tables.Person
import kotlinx.android.synthetic.main.recyclerview_row_debt_for_me.view.*

class DebtForMeAdapter(

) : ListAdapter<Person, DebtForMeAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Person>() {
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

    @SuppressLint("SetTextI18n")
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
//                it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
//                val createDebtorFragment = BottomSheetInfoPersonFragment()
//                val bundle = Bundle()
//                bundle.putSerializable("person", persons[bindingAdapterPosition])
//                createDebtorFragment.arguments = bundle
//                view.findFragment<DebtForMeFragment>().activity?.let { activity ->
//                    createDebtorFragment.show(activity.supportFragmentManager, null)
//                }
            }
        }
    }
}