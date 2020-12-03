package com.violadin.debtorpit.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.adapter.AddDebtsPersonsAdapter
import com.violadin.debtorpit.viewmodel.PersonViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddDebtsFragment : Fragment() {

    private lateinit var model: PersonViewModel

    companion object {
        fun newInstance(): AddDebtsFragment {
            return AddDebtsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        val view = inflater.inflate(R.layout.adddebts_activity, container, false)
        model = ViewModelProvider(this).get(PersonViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list_add_debt)
        val linearLayoutManager = LinearLayoutManager(view.context,
                RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        var checked: BooleanArray? = null
        model.allPersons.observe(viewLifecycleOwner, { persons ->
            run {
                val adapter = AddDebtsPersonsAdapter(persons)
                checked = adapter.getCheckedPersons()
                recyclerView.adapter = adapter
            }
        })


        val editText = view.findViewById<EditText>(R.id.debts_et)
        val meTooCheckBox = view.findViewById<CheckBox>(R.id.cb_me_too)
        val addDebtButton = view.findViewById<Button>(R.id.add_depts)
        addDebtButton.setOnClickListener {
            if (editText.text.toString() != "") {
                var countOfCheckedPersons = 0
                for (item in checked!!) {
                    if (item)
                        ++countOfCheckedPersons
                }
                if (meTooCheckBox.isChecked) ++countOfCheckedPersons
                val debt = editText.text.toString().toDouble() / countOfCheckedPersons


                for (i in checked!!.indices) {
                    if (checked?.get(i) == true) {
                        val newDebt = debt + model.allPersons.value!![i].debt!!
                        GlobalScope.launch {
                            model.allPersons.value?.let { it1 -> model.update(it1[i], newDebt) }
                        }
                    }
                }

                Toast.makeText(view.context,
                        "Debts added successfully", Toast.LENGTH_LONG).show()

                val inputMethodMAnager: InputMethodManager =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                                as InputMethodManager
                inputMethodMAnager.hideSoftInputFromWindow(
                        activity?.currentFocus?.windowToken, 0)
                editText.text.clear()
            } else {
                Toast.makeText(context, "Enter a valid check", Toast.LENGTH_LONG).show()
                editText.requestFocus()
            }
        }
        return view
    }
}