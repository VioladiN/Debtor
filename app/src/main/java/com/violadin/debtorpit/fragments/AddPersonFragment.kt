package com.violadin.debtorpit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.AppDataBase
import com.violadin.debtorpit.model.Person
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddPersonFragment: Fragment() {

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

        firstName = view.findViewById(R.id.et_firstname)
        lastName = view.findViewById(R.id.et_lastname)
        debt = view.findViewById(R.id.debt_count)
        addPersonButton = view.findViewById(R.id.btn_add_person)
        val db = AppDataBase.getInstance(view.context).personDao()

        addPersonButton.setOnClickListener {
           GlobalScope.launch {
               db.insertPerson(Person(firstName = "321", lastName = "123", debt = 100.0))
           }
        }

        return view
    }

}