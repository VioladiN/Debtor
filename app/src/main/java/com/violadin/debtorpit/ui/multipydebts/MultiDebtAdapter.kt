package com.violadin.debtorpit.ui.multipydebts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import kotlinx.android.synthetic.main.recyclerview_row_choose_person.view.*

class MultiDebtAdapter(
    private val personsIdsHashSet: HashSet<Int>
) : ListAdapter<ChoosePersonModel, MultiDebtAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<ChoosePersonModel>() {
    override fun areItemsTheSame(oldItem: ChoosePersonModel, newItem: ChoosePersonModel): Boolean {
        return oldItem.person.id == newItem.person.id
    }

    override fun areContentsTheSame(
        oldItem: ChoosePersonModel,
        newItem: ChoosePersonModel
    ): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row_choose_person, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(person: ChoosePersonModel) {
            view.radio_selected.isChecked = person.isChecked
            view.debtor_name_text.text = person.person.fio
            view.debtor_date_text.text = person.person.created_time
            view.debt_count.text = person.person.debt.toString()
            view.setOnClickListener {
                if (personsIdsHashSet.contains(person.person.id)) {
                    view.radio_selected.isChecked = false
                    personsIdsHashSet.remove(person.person.id!!)
                } else {
                    view.radio_selected.isChecked = true
                    personsIdsHashSet.add(person.person.id!!)
                }
            }
        }
    }
}