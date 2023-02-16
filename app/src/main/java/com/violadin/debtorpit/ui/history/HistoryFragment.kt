package com.violadin.debtorpit.ui.history

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.violadin.debtorpit.databinding.HistoryFragmentBinding
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private lateinit var binding: HistoryFragmentBinding
    private var allHistory = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            edPeekDateFrom.setOnClickListener {
                showDatePickerDialog(edPeekDateFrom)
            }
            edPeekDateTo.setOnClickListener {
                showDatePickerDialog(edPeekDateTo)
            }

            radioShowHistoryAllTime.setOnClickListener {
                if (allHistory) {
                    radioShowHistoryAllTime.isChecked = false
                    allHistory = false
                } else {
                    radioShowHistoryAllTime.isChecked = true
                    allHistory = true
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

    @SuppressLint("SetTextI18n")
    private fun showDatePickerDialog(startView: EditText) {
        DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                startView.setText("$dayOfMonth.$monthOfYear.$year")
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}