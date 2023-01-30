package com.violadin.debtorpit.ui.multipydebts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.MultiDebtFragmentBinding
import com.violadin.debtorpit.navigation.NavigationManager
import com.violadin.debtorpit.utils.pluralTextFrom
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone
import javax.inject.Inject

@AndroidEntryPoint
class MultiDebtFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private val viewModel: MultiDebtFragmentVM by viewModels()
    private var meToo: Boolean = false
    private val currentDate = LocalDateTime.now().atZone(TimeZone.getTimeZone("Moscow").toZoneId())
    private lateinit var binding: MultiDebtFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = MultiDebtFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            textPeekDate.text = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            handleSelectedPersons()

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.persons.collect { persons ->
                    radioOnMeToo.setOnClickListener {
                        if (meToo) {
                            radioOnMeToo.isChecked = false
                            meToo = false
                        } else {
                            radioOnMeToo.isChecked = true
                            meToo = true
                        }
                    }

                    addDebts.setOnClickListener {
                        if (debtEd.text.isEmpty()) {
                            Toast.makeText(
                                requireContext(), R.string.wrong_filed_debt, Toast.LENGTH_LONG
                            ).show()
                        } else {
                            if (persons.none { it.isChecked }) {
                                Toast.makeText(
                                    requireContext(), R.string.empty_count_of_debtors, Toast.LENGTH_LONG
                                ).show()
                            } else {
                                viewModel.updatePersons(
                                    persons.filter { it.isChecked }, meToo, debtEd.text.toString().toInt()
                                )
                                navigationManager.bottomBarController?.popBackStack(R.id.multi_debt_fragment, true)
                                navigationManager.bottomBarController?.navigate(R.id.debt_for_me_nav_graph)
                            }
                        }
                    }

                    textAddDebtors.setOnClickListener {
                        val bundle = Bundle().apply {
                            putParcelableArrayList("persons", persons as ArrayList<ChoosePersonModel>)
                        }
                        findNavController().navigate(R.id.multi_debt_to_choose_persons_dialog, bundle)
                    }
                }
            }
        }
    }

    private fun handleSelectedPersons() {
        findNavController()
            .currentBackStackEntry?.savedStateHandle?.getLiveData<ArrayList<ChoosePersonModel>>(
                "selected_persons"
            )?.observe(viewLifecycleOwner) { persons ->
                if (persons.isEmpty()) {
                    binding.textAddedPersons.visibility = View.GONE
                } else {
                    binding.textAddedPersons.visibility = View.VISIBLE
                    val countPersons = persons.size
                    val text = StringBuilder()
                    text.append(
                        "${requireContext().getText(R.string.text_u_are_added)} $countPersons ${
                            requireContext().getString(
                                pluralTextFrom(
                                    countPersons, R.string.form2_persons, R.string.form5_persons
                                )
                            )
                        }: "
                    )
                    persons.forEachIndexed { id, person ->
                        if (id == countPersons - 1) {
                            text.append("${person.person.fio}")
                        } else {
                            text.append("${person.person.fio}, ")
                        }
                    }
                    binding.textAddedPersons.text = text
                }
            }
    }
}
