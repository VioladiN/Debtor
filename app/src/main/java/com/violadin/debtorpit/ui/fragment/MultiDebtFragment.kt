package com.violadin.debtorpit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.violadin.debtorpit.R
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import com.violadin.debtorpit.ui.adapter.DebtForMeAdapter
import com.violadin.debtorpit.ui.adapter.MultiDebtAdapter
import kotlinx.android.synthetic.main.debt_for_me_fragment.list_item
import kotlinx.android.synthetic.main.fragment_header.view.*
import kotlinx.android.synthetic.main.multi_debt_fragment.*

class MultiDebtFragment : Fragment() {

    private lateinit var mViewModel: PersonViewModel
    private lateinit var personAdapter: MultiDebtAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        val view = inflater.inflate(R.layout.multi_debt_fragment, container, false)
        view.header_text.text = getString(R.string.second_page)

        mViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        return view
    }

    override fun onResume() {
        super.onResume()

        mViewModel.data.observe(this, { persons ->
            if (persons.isEmpty())
                list_empty_iv.visibility = View.VISIBLE
            else {
                list_empty_iv.visibility = View.INVISIBLE
                personAdapter = MultiDebtAdapter(persons, requireContext())
                list_item.layoutManager = LinearLayoutManager(requireContext())
                list_item.adapter = personAdapter
            }
        })

        add_depts.setOnClickListener {
            if (debt_ed.text.isNullOrEmpty()) {
                Toast.makeText(context, requireContext().getString(R.string.empty_field), Toast.LENGTH_SHORT).show()
            } else {
                val checkedPersons = personAdapter.getCheckedPersons()
                var countOfSelecting = 0
                var personsIds = arrayListOf<Int>()
                if (cb_me_too.isChecked)
                    ++countOfSelecting
                for (i in checkedPersons.indices)
                    if (checkedPersons[i]) {
                        personsIds.add(i)
                        ++countOfSelecting
                    }
                val newDebt = String.format("%.2f", debt_ed.text.toString().toDouble() / countOfSelecting).toDouble()
                for (id in personsIds) {
                    val person = mViewModel.data.value?.get(id)!!
                    mViewModel.update(person, (person.debt!! + newDebt))
                }
            }
        }
    }
}