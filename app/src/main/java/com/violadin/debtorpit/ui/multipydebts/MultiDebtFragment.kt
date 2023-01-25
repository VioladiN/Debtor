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
import com.violadin.debtorpit.navigation.NavigationManager
import com.violadin.debtorpit.utils.pluralTextFrom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.multi_debt_fragment.*
import kotlinx.android.synthetic.main.multi_debt_fragment.debt_ed
import kotlinx.android.synthetic.main.multi_debt_fragment.text_peek_date
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.multi_debt_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_peek_date.text = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        handleSelectedPersons()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.persons.collect { persons ->
                radio_on_me_too.setOnClickListener {
                    if (meToo) {
                        radio_on_me_too.isChecked = false
                        meToo = false
                    } else {
                        radio_on_me_too.isChecked = true
                        meToo = true
                    }
                }

                add_debts.setOnClickListener {
                    if (debt_ed.text.isEmpty()) {
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
                                persons.filter { it.isChecked }, meToo, debt_ed.text.toString().toInt()
                            )
                            navigationManager.bottomBarController?.popBackStack(R.id.multi_debt_fragment, true)
                            navigationManager.bottomBarController?.navigate(R.id.debt_for_me_nav_graph)
                        }
                    }
                }

                text_add_debtors.setOnClickListener {
                    val bundle = Bundle().apply {
                        putParcelableArrayList("persons", persons as ArrayList<ChoosePersonModel>)
                    }
                    findNavController().navigate(R.id.multi_debt_to_choose_persons_dialog, bundle)
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
                    text_added_persons.visibility = View.GONE
                } else {
                    text_added_persons.visibility = View.VISIBLE
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
                    text_added_persons.text = text
                }
            }
    }
}