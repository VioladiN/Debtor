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
    private val list: List<Person>,
    private val context: Context,
    private val viewModel: PersonViewModel
) : RecyclerView.Adapter<DebtForMeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row_debt_for_me, parent, false)
        return ViewHolder(v, context, viewModel)
    }

    class ViewHolder(
        view: View,
        val context: Context,
        val viewModel: PersonViewModel
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
            showDialogPerson(
                viewHolder.context,
                viewHolder.firstName?.text.toString(),
                viewHolder.debt?.text.toString(),
                viewHolder.viewModel,
                person
            )
        }
    }

    override fun getItemCount(): Int =
        list.size

    private fun showDialogPerson(
        context: Context,
        firstName: String,
        debt: String,
        viewModel: PersonViewModel,
        person: Person
    ) {
        val alertDialog = MaterialAlertDialogBuilder(context)
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        alertDialog.setTitle("$firstName. Долг: $debt")
        val debtEditText = EditText(context)
        debtEditText.hint = context.getString(R.string.debt_hint)
        debtEditText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
        lp.marginStart = 100
        lp.marginEnd = 100
        debtEditText.layoutParams = lp
        layout.addView(debtEditText)
        alertDialog.setPositiveButton("Добавить") { dialog, which ->
            try {
                if (debtEditText.text.isNullOrEmpty()) {
                    Toast.makeText(context, context.getString(R.string.empty_field), Toast.LENGTH_SHORT).show()
                } else {
                    var newDebt = debt.toDouble() + debtEditText.text.toString().toDouble()
                    viewModel.update(person, newDebt)
                }
            } catch (e: Exception) {

            }
            dialog.cancel()
        }
        alertDialog.setNeutralButton("Списать") { dialog, which ->
            try {
                if (debtEditText.text.isNullOrEmpty()) {
                    Toast.makeText(context, context.getString(R.string.empty_field), Toast.LENGTH_SHORT).show()
                } else {
                    var newDebt = debt.toDouble() - debtEditText.text.toString().toDouble()
                    if (newDebt < 0)
                        newDebt = 0.0
                    viewModel.update(person, newDebt)
                }
            } catch (e: Exception) {

            }
            dialog.cancel()
        }
        alertDialog.setNegativeButton("Обнулить долг") { dialog, which ->
            try {
                viewModel.update(person, 0.0)
            } catch (e: Exception) {

            }
            dialog.cancel()
        }
        alertDialog.setView(layout)
        alertDialog.show()
    }
}