package com.violadin.debtorpit.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.violadin.debtorpit.R
import com.violadin.debtorpit.model.Person
import com.violadin.debtorpit.viewmodel.PersonViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddPersonFragment : Fragment() {

    private lateinit var model: PersonViewModel

    companion object {
        fun newInstance(): AddPersonFragment {
            return AddPersonFragment()
        }
    }

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var debt: EditText
    private lateinit var addPersonButton: Button

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        val view = inflater.inflate(R.layout.addperson_activity, container, false)

        model = ViewModelProvider(this).get(PersonViewModel::class.java)

        firstName = view.findViewById(R.id.et_firstname)
        lastName = view.findViewById(R.id.et_lastname)
        debt = view.findViewById(R.id.debt_count)
        addPersonButton = view.findViewById(R.id.btn_add_person)

        addPersonButton.setOnClickListener {

            if (firstName.text.toString() != "") {
                val debtField: String = if (debt.text.toString() == "")
                    "0.0"
                else
                    debt.text.toString()

                GlobalScope.launch {
                    model.insert(Person(firstName = firstName.text.toString(),
                            lastName = lastName.text.toString(),
                            debt = debtField.toDouble()))
                }
                Toast.makeText(view.context,
                        "Person added successfully",
                        Toast.LENGTH_LONG).show()

                val inputMethodMAnager: InputMethodManager =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                                as InputMethodManager
                inputMethodMAnager.hideSoftInputFromWindow(
                        activity?.currentFocus?.windowToken, 0)
                firstName.text.clear()
                lastName.text.clear()
                debt.text.clear()
            } else {
                Toast.makeText(context,
                        "Enter at least first name",
                        Toast.LENGTH_LONG).show()
                firstName.requestFocus()
            }
        }

        return view
    }

}