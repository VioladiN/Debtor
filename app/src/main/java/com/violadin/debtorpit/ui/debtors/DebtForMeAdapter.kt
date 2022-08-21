package com.violadin.debtorpit.ui.debtors

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import com.violadin.debtorpit.ui.fragment.BottomSheetInfoPersonFragment
import kotlinx.android.synthetic.main.recyclerview_row_debt_for_me.view.*

class DebtForMeAdapter(
    val persons: List<Person>,
    val context: Context,
    val viewModel: PersonViewModel
) : RecyclerView.Adapter<DebtForMeAdapter.ViewHolder>() {

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
                it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
                val createDebtorFragment = BottomSheetInfoPersonFragment(viewModel)
                val bundle = Bundle()
                bundle.putSerializable("person", persons[bindingAdapterPosition])
                createDebtorFragment.arguments = bundle
                view.findFragment<DebtForMeFragment>().activity?.let { activity ->
                    createDebtorFragment.show(activity.supportFragmentManager, null)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = "${persons[position].lastName} ${persons[position].firstName}"
        viewHolder.debt.text = persons[position].debt.toString()
    }

    override fun getItemCount(): Int =
        persons.size
}