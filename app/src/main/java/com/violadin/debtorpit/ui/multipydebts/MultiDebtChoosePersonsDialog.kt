package com.violadin.debtorpit.ui.multipydebts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.ChoosePersonsDialogFragmentBinding

class MultiDebtChoosePersonsDialog : BottomSheetDialogFragment() {

    private var recyclerAdapter: MultiDebtAdapter? = null
    private val personsIdsHashSet = HashSet<Int>()
    private var persons: ArrayList<ChoosePersonModel>? = null
    private lateinit var binding: ChoosePersonsDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChoosePersonsDialogFragmentBinding.inflate(inflater, container, false)
        initRecyclerList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            persons = bundle.getParcelableArrayList("persons")!!
            recyclerAdapter?.submitList(persons)
            persons!!.filter { it.isChecked }.forEach { personsIdsHashSet.add(it.person.id!!) }
        }

        with(binding) {
            persons?.let { persons ->
                buttonCloseDialog.setOnClickListener {
                    dismiss()
                }

                imageViewAccept.setOnClickListener {
                    persons.forEach { person ->
                        person.isChecked = personsIdsHashSet.contains(person.person.id)
                    }
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        "selected_persons",
                        persons.filter { it.isChecked })
                    dismiss()
                }

                buttonAccept.setOnClickListener {
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
    }

    private fun initRecyclerList() {
        binding.recyclerChoosePersons.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = MultiDebtAdapter(personsIdsHashSet)
        binding.recyclerChoosePersons.adapter = recyclerAdapter
    }

    override fun getTheme(): Int =
        R.style.AppBottomSheetDialogTheme
}