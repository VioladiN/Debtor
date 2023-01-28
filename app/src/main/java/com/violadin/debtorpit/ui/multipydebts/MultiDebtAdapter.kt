package com.violadin.debtorpit.ui.multipydebts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.databinding.RecyclerviewRowChoosePersonBinding

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
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerviewRowChoosePersonBinding = RecyclerviewRowChoosePersonBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerviewRowChoosePersonBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecyclerviewRowChoosePersonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(person: ChoosePersonModel) {
            with(binding) {
                radioSelected.isChecked = person.isChecked
                debtorNameText.text = person.person.fio
                debtorDateText.text = person.person.created_time
                debtCount.text = person.person.debt.toString()
                root.setOnClickListener {
                    if (personsIdsHashSet.contains(person.person.id)) {
                        radioSelected.isChecked = false
                        personsIdsHashSet.remove(person.person.id!!)
                    } else {
                        radioSelected.isChecked = true
                        personsIdsHashSet.add(person.person.id!!)
                    }
                }
            }
        }
    }
}