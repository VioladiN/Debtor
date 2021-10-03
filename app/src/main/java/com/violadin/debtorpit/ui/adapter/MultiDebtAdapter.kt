package com.violadin.debtorpit.ui.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.recyclerview_row_debt_for_me.view.*

class MultiDebtAdapter(
    val persons: List<Person>,
    val context: Context
) : RecyclerView.Adapter<MultiDebtAdapter.ViewHolder>() {

    val selectedPersons = arrayListOf<Int>()
    private val clickSubject = PublishSubject.create<List<Int>>()
    val clickEvent: Observable<List<Int>> = clickSubject

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

        init {
            view.setOnClickListener {
                if (selectedPersons.contains(persons[absoluteAdapterPosition].id!!)) {
                    selectedPersons.remove(persons[absoluteAdapterPosition].id!!)
                    val typedValue = TypedValue()
                    context.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
                    view.setBackgroundColor(typedValue.data)
                } else {
                    selectedPersons.add(persons[absoluteAdapterPosition].id!!)
                    view.setBackgroundColor(context.getColor(R.color.selected_base))
                }
                clickSubject.onNext(selectedPersons)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.firstName.text = persons[position].firstName
        viewHolder.lastName.text = persons[position].lastName
        viewHolder.debt.text = persons[position].debt.toString()
        if (selectedPersons.contains(persons[position].id)) {
            viewHolder.itemView.setBackgroundColor(context.getColor(R.color.selected_base))
        } else {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
            viewHolder.itemView.setBackgroundColor(typedValue.data)
        }
    }

    override fun getItemCount(): Int =
        persons.size
}