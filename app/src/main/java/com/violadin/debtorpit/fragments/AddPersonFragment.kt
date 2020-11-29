package com.violadin.debtorpit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.AppDataBase
import com.violadin.debtorpit.model.Person
import com.violadin.debtorpit.viewmodel.PersonViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddPersonFragment: Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.addperson_activity, container, false)

        model = ViewModelProvider(this).get(PersonViewModel::class.java)

        firstName = view.findViewById(R.id.et_firstname)
        lastName = view.findViewById(R.id.et_lastname)
        debt = view.findViewById(R.id.debt_count)
        addPersonButton = view.findViewById(R.id.btn_add_person)

        addPersonButton.setOnClickListener {
           GlobalScope.launch {
               model.insert(Person(firstName = firstName.text.toString(),
                       lastName = lastName.text.toString(),
                       debt = debt.text.toString().toDouble()))
           }
            Toast.makeText(view.context,
                    "Person added successfully",
                    Toast.LENGTH_LONG).show()
        }

        return view
    }

}