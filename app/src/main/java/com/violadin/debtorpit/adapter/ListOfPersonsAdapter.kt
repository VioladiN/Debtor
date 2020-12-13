package com.violadin.debtorpit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.AppDataBase
import com.violadin.debtorpit.model.Person
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListOfPersonsAdapter(private var persons: List<Person>, private var context: Context):
        RecyclerView.Adapter<ListOfPersonsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.recyclerview_row_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = persons[position].firstName.toString() +
                " " + persons[position].lastName.toString()
        holder.debt.text = persons[position].debt.toString()

        holder.buttonAddDebt.setOnClickListener {
            showDialog(persons[position], "add")
        }
        holder.buttonRemoveDebt.setOnClickListener {
            showDialog(persons[position], "remove")
        }
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    private fun updatePerson(person: Person, debt: Double, event: String) {
        val local = AppDataBase.getInstance(context).personDao()
        var newDebt = 0.0
        if (event.equals("add")) {
            newDebt = person.debt!! + debt
            newDebt = String.format("%.2f", newDebt).toDouble()
        } else if (event.equals("remove")) {
            newDebt = person.debt!! - debt
            newDebt = String.format("%.2f", newDebt).toDouble()
            if (newDebt < 0) newDebt = 0.0
        }
        GlobalScope.launch {
            person.lastName?.let { person.firstName?.let { it1 ->
                local.updatePerson(it1, it, newDebt)
            } }
        }
    }

    private fun showDialog(person:Person, event: String) {
        val alertDialog = MaterialAlertDialogBuilder(context)
        alertDialog.setTitle(person.firstName + " " + person.debt)
        alertDialog.setMessage("$event debt: ")
        val editText = EditText(context)
        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        editText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
        editText.layoutParams = lp
        alertDialog.setView(editText)

        if (event == "remove") {
            alertDialog.setNeutralButton("Zero debt") { dialog, which ->
                updatePerson(person, 0.0, "zero")
            }
        }

        alertDialog.setPositiveButton("Confirm") {dialog, which ->
            if (editText.text.toString() != "")
                updatePerson(person, editText.text.toString().toDouble(), event)
            else
                dialog.cancel()
        }
        alertDialog.setNegativeButton("Cancel") {dialog, which ->
            dialog.cancel()
        }

        alertDialog.show()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val debt = itemView.findViewById<TextView>(R.id.tv_dept)
        val buttonAddDebt = itemView.findViewById<FloatingActionButton>(R.id.fab_add)
        val buttonRemoveDebt = itemView.findViewById<FloatingActionButton>(R.id.fab_remove)
    }
}