package com.violadin.debtorpit.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.recyclerview_row_debt_for_me.view.*

class MultiDebtAdapter(
    private val list: List<Person>,
    private val context: Context
) : RecyclerView.Adapter<MultiDebtAdapter.ViewHolder>() {

    private val checkedPersons = BooleanArray(list.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row_debt_for_me, parent, false)
        return ViewHolder(v, context)
    }

    inner class ViewHolder(
        view: View,
        val context: Context
    ) : RecyclerView.ViewHolder(view) {
        var firstName: TextView? = null
        var lastName: TextView? = null
        var debt: TextView? = null

        init {
            firstName = view.first_name_text
            lastName = view.last_name_text
            debt = view.debt_count
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val person = list[position]
        viewHolder.firstName?.text = person.firstName
        viewHolder.lastName?.text = person.lastName
        viewHolder.debt?.text = person.debt.toString()
        viewHolder.itemView.setOnClickListener {
            if (checkedPersons[position]) {
                viewHolder.itemView.setBackgroundColor(context.getColor(R.color.white))
                checkedPersons[position] = false
            } else {
                viewHolder.itemView.setBackgroundColor(context.getColor(R.color.base_UI_mute))
                checkedPersons[position] = true
            }
        }
    }

    override fun getItemCount(): Int =
        list.size

    fun getCheckedPersons(): BooleanArray = checkedPersons

}