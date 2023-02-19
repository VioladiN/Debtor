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
import com.violadin.debtorpit.databinding.CreateDebtFragmentBinding
import com.violadin.debtorpit.enums.PersonType
import com.violadin.debtorpit.navigation.NavigationManager
import com.violadin.debtorpit.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
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
    private lateinit var binding: CreateDebtFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateDebtFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).changeHeader(R.string.create_debt_label)

        with(binding) {
            textPeekDate.text = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            initRadioButtons()

            addDebtor.setOnClickListener {
                if (checkEditTextFields()) {
                    viewModel.createPerson(
                        Person(
                            fio = fioEd.text.toString(),
                            debt = if (debtEd.text.isEmpty()) 0.0 else debtEd.text.toString()
                                .toDouble(),
                            phone = phoneEd.text.toString(),
                            createdTime = currentDate.toInstant().toEpochMilli(),
                            type = if (radioDebtForMe.isChecked) {
                                PersonType.DEBT_FOR_ME_PERSON.type
                            } else {
                                PersonType.MY_DEBT_PERSON.type
                            }
                        ),
                        descriptionEd.text.toString()
                    )
                    navigationManager.bottomBarController?.navigateUp()
                }
            }
        }
    }

    private fun checkEditTextFields(): Boolean {
        with(binding) {
            if (!radioDebtForMe.isChecked && !radioMyDebt.isChecked) {
                Toast.makeText(requireContext(), R.string.missing_person_type,Toast.LENGTH_SHORT).show()
                return false
            }
            if (fioEd.text.isEmpty()) {
                Toast.makeText(requireContext(), R.string.empty_field_fio, Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }
    }

    private fun initRadioButtons() {
        with(binding) {
            requireArguments().getString("type")?.let { type ->
                if (type == PersonType.DEBT_FOR_ME_PERSON.type) {
                    radioDebtForMe.isChecked = true
                } else if (type == PersonType.MY_DEBT_PERSON.type) {
                    radioMyDebt.isChecked = true
                }
            }

            radioDebtForMe.setOnClickListener {
                radioMyDebt.isChecked = false
            }

            radioMyDebt.setOnClickListener {
                radioDebtForMe.isChecked = false
            }
        }
    }
}