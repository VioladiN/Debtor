package com.violadin.debtorpit.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.Status
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import com.violadin.debtorpit.ui.adapter.DebtForMeAdapter
import kotlinx.android.synthetic.main.debt_for_me_fragment.*
import kotlinx.android.synthetic.main.fragment_header.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DebtForMeFragment: Fragment() {

    private lateinit var mViewModel: PersonViewModel
    private lateinit var personAdapter: DebtForMeAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        val view = inflater.inflate(R.layout.debt_for_me_fragment, container, false)
        view.header_text.text = getString(R.string.first_page)

        mViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        return view
    }

    override fun onResume() {
        super.onResume()

        mViewModel.data.observe(this, { persons ->
            if (persons.isEmpty())
                list_is_empty_tv.visibility = View.VISIBLE
            else {
                list_is_empty_tv.visibility = View.INVISIBLE
                personAdapter = DebtForMeAdapter(persons, requireContext(), mViewModel)
                list_item.layoutManager = LinearLayoutManager(requireContext())
                list_item.adapter = personAdapter
            }
        })

        add_person.setOnClickListener {
            showDialogAddPerson()
        }

        val itemTouchHelperRecyclerView =
            object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    p0: RecyclerView,
                    p1: RecyclerView.ViewHolder,
                    p2: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    try {
                        mViewModel.data.value?.get(viewHolder.absoluteAdapterPosition)?.let {
                            mViewModel.delete(
                                it
                            )
                        }
                    } catch (e: Exception) {

                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperRecyclerView)
        itemTouchHelper.attachToRecyclerView(list_item)
    }

    private fun showDialogAddPerson() {
        val alertDialog = MaterialAlertDialogBuilder(requireContext())
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        alertDialog.setTitle(getString(R.string.add_person))
        val firstNameEditText = EditText(requireContext())
        firstNameEditText.hint = getString(R.string.first_name_hint)
        val lastNameEditText = EditText(requireContext())
        lastNameEditText.hint = getString(R.string.last_name_hint)
        val debtEditText = EditText(requireContext())
        debtEditText.hint = getString(R.string.debt_hint)
        debtEditText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)
        lp.marginStart = 100
        lp.marginEnd = 100
        firstNameEditText.layoutParams = lp
        lastNameEditText.layoutParams = lp
        debtEditText.layoutParams = lp
        layout.addView(firstNameEditText)
        layout.addView(lastNameEditText)
        layout.addView(debtEditText)
        alertDialog.setPositiveButton("Добавить") { dialog, which ->
            try {
                mViewModel.insert(Person(
                    firstName = firstNameEditText.text.toString(),
                    lastName = lastNameEditText.text.toString(),
                    debt = if (debtEditText.text.isNullOrEmpty()) 0.0 else debtEditText.text.toString().toDouble()))
            } catch (e: Exception) {

            }
            dialog.cancel()
        }
        alertDialog.setNegativeButton("Закрыть") { dialog, which ->
            dialog.cancel()
        }
        alertDialog.setView(layout)
        alertDialog.show()
    }

}