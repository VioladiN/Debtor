package com.violadin.debtorpit.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.model.Person

class AddDebtsPersonsAdapter(private var persons: List<Person>):
        RecyclerView.Adapter<AddDebtsPersonsAdapter.ViewHolder>() {

    val checked = BooleanArray(persons.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.recyclerview_row_addorremovedebt, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = persons[position].firstName.toString() +
                " " + persons[position].lastName.toString()
        holder.debt.text = persons[position].debt.toString()

        holder.checkBox.setOnClickListener {
            checked[position] = !checked[position]
        }
    }

    fun getCheckedPersons(): BooleanArray = checked

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val debt = itemView.findViewById<TextView>(R.id.tv_dept)
        val checkBox = itemView.findViewById<CheckBox>(R.id.cb_selecting)
    }
}