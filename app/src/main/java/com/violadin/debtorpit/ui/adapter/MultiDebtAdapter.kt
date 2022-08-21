package com.violadin.debtorpit.ui.adapter

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
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
        val name = view.debtor_name_text
        val debt = view.debt_count

        init {
            view.setOnClickListener {
                if (selectedPersons.contains(persons[absoluteAdapterPosition].id!!)) {
                    selectedPersons.remove(persons[absoluteAdapterPosition].id!!)
                    val typedValue = TypedValue()
                    context.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
                    changeColor(view, context.getColor(R.color.selected_base), typedValue.data)
                } else {
                    selectedPersons.add(persons[absoluteAdapterPosition].id!!)
                    val typedValue = TypedValue()
                    context.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
                    changeColor(view, typedValue.data, context.getColor(R.color.selected_base))
                }
                clickSubject.onNext(selectedPersons)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = "${persons[position].lastName} ${persons[position].firstName}"
        viewHolder.debt.text = persons[position].debt.toString()
        if (selectedPersons.contains(persons[position].id)) {
            viewHolder.itemView.setBackgroundColor(context.getColor(R.color.selected_base))
        } else {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true)
            viewHolder.itemView.setBackgroundColor(typedValue.data)
        }
    }

    @SuppressLint("Recycle")
    private fun changeColor(view: View, colorFrom: Int, colorTo: Int) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 400
        colorAnimation.addUpdateListener {
            view.setBackgroundColor(it.animatedValue as Int)
        }
        colorAnimation.start()
    }

    override fun getItemCount(): Int =
        persons.size
}