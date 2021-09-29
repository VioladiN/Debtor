package com.violadin.debtorpit.ui.adapter

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.recyclerview_row_debt_for_me.view.*
import java.lang.Exception

class DebtForMeAdapter(
    val persons: List<Person>,
    val context: Context
) : RecyclerView.Adapter<DebtForMeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row_debt_for_me, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val firstName = view.first_name_text
        val lastName = view.last_name_text
        val debt = view.debt_count
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.firstName.text = persons[position].firstName
        viewHolder.lastName.text = persons[position].lastName
        viewHolder.debt.text = persons[position].debt.toString()
    }

    override fun getItemCount(): Int =
        persons.size
}