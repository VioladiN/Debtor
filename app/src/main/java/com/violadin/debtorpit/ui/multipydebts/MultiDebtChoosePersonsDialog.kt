package com.violadin.debtorpit.ui.multipydebts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.violadin.debtorpit.R
import kotlinx.android.synthetic.main.choose_persons_dialog_fragment.*
import kotlinx.android.synthetic.main.choose_persons_dialog_fragment.view.*

class MultiDebtChoosePersonsDialog : BottomSheetDialogFragment() {

    private var recyclerAdapter: MultiDebtAdapter? = null
    private val personsIdsHashSet = HashSet<Int>()
    private var persons: ArrayList<ChoosePersonModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.choose_persons_dialog_fragment, container, false)
        initRecyclerList(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            persons = bundle.getParcelableArrayList("persons")!!
            recyclerAdapter?.submitList(persons)
            persons!!.filter { it.isChecked }.forEach { personsIdsHashSet.add(it.person.id!!) }
        }

        persons?.let { persons ->
            button_close_dialog.setOnClickListener {
                dismiss()
            }

            image_view_accept.setOnClickListener {
                persons.forEach { person ->
                    person.isChecked = personsIdsHashSet.contains(person.person.id)
                }
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "selected_persons",
                    persons.filter { it.isChecked })
                dismiss()
            }

            button_accept.setOnClickListener {
                persons.forEach { person ->
                    person.isChecked = personsIdsHashSet.contains(person.person.id)
                }
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "selected_persons",
                    persons.filter { it.isChecked })
                dismiss()
            }
        }
    }

    private fun initRecyclerList(parentView: View) {
        parentView.recycler_choose_persons.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = MultiDebtAdapter(personsIdsHashSet)
        parentView.recycler_choose_persons.adapter = recyclerAdapter
    }

    override fun getTheme(): Int =
        R.style.AppBottomSheetDialogTheme
}