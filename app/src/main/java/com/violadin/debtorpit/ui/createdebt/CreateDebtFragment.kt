package com.violadin.debtorpit.ui.createdebt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.enums.PersonType
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_debt_fragment.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CreateDebtFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager
    private val viewModel: CreateDebtFragmentVM by viewModels()
    private val currentDate = LocalDateTime.now().atZone(TimeZone.getTimeZone("Moscow").toZoneId())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_debt_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_peek_date.text = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        initRadioButtons()

        add_debtor.setOnClickListener {
            if (checkEditTextFields()) {
                viewModel.createPerson(
                    Person(
                        fio = fio_ed.text.toString(),
                        debt = if (debt_ed.text.isEmpty()) 0.0 else debt_ed.text.toString()
                            .toDouble(),
                        phone = phone_ed.text.toString(),
                        created_time = currentDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                        type = if (radio_debt_for_me.isChecked) {
                            PersonType.DEBT_FOR_ME_PERSON.type
                        } else {
                            PersonType.MY_DEBT_PERSON.type
                        }
                    )
                )
                navigationManager.bottomBarController?.popBackStack()
            }
        }
    }

    private fun checkEditTextFields(): Boolean {
        if (!radio_debt_for_me.isChecked && !radio_my_debt.isChecked) {
            Toast.makeText(requireContext(), R.string.missing_person_type,Toast.LENGTH_SHORT).show()
            return false
        }
        if (fio_ed.text.isEmpty()) {
            Toast.makeText(requireContext(), R.string.empty_field_fio, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun initRadioButtons() {
        requireArguments().getString("type")?.let { type ->
            if (type == PersonType.DEBT_FOR_ME_PERSON.type) {
                radio_debt_for_me.isChecked = true
            } else if (type == PersonType.MY_DEBT_PERSON.type) {
                radio_my_debt.isChecked = true
            }
        }

        radio_debt_for_me.setOnClickListener {
            radio_my_debt.isChecked = false
        }

        radio_my_debt.setOnClickListener {
            radio_debt_for_me.isChecked = false
        }
    }
}