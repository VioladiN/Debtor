package com.violadin.debtorpit.ui.history

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.HistoryFragmentBinding
import com.violadin.debtorpit.navigation.NavigationManager
import com.violadin.debtorpit.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalField
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private lateinit var binding: HistoryFragmentBinding
    private var allHistory = false
    private val currentDate = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId())
    private val viewModel: HistoryFragmentVM by viewModels()
    private var recyclerAdapter: HistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        initRecyclerList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).changeHeader(R.string.history)


        with(binding) {
            edPeekDateFrom.setText(
                currentDate.minusMonths(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            )
            edPeekDateTo.setText(currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))

            viewModel.getHistoriesByDates(
                edPeekDateFrom.text.toString(),
                edPeekDateTo.text.toString(),
                radioMyDebt.isChecked
            )

            edPeekDateFrom.setOnClickListener {
                showDatePickerDialog(edPeekDateFrom)
            }
            edPeekDateTo.setOnClickListener {
                showDatePickerDialog(edPeekDateTo)
            }

            radioShowHistoryAllTime.setOnClickListener {
                if (allHistory) {
                    viewModel.getHistoriesByDates(
                        edPeekDateFrom.text.toString(),
                        edPeekDateTo.text.toString(),
                        radioMyDebt.isChecked
                    )
                    radioShowHistoryAllTime.isChecked = false
                    allHistory = false
                } else {
                    viewModel.getAllHistoryFilteredByPersonType(radioMyDebt.isChecked)
                    radioShowHistoryAllTime.isChecked = true
                    allHistory = true
                }
            }

            radioDebtForMe.setOnClickListener {
                radioMyDebt.isChecked = false
                viewModel.getHistoriesByDates(
                    edPeekDateFrom.text.toString(),
                    edPeekDateTo.text.toString(),
                    radioMyDebt.isChecked
                )
            }

            radioMyDebt.setOnClickListener {
                radioDebtForMe.isChecked = false
                viewModel.getHistoriesByDates(
                    edPeekDateFrom.text.toString(),
                    edPeekDateTo.text.toString(),
                    radioMyDebt.isChecked
                )
            }

            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.histories.collect { histories ->
                    if (histories.isEmpty()) {
                        listIsEmptyTv.visibility = View.VISIBLE
                    } else {
                        listIsEmptyTv.visibility = View.INVISIBLE
                    }
                    recyclerAdapter?.submitList(histories)
                }
            }
        }
    }

    private fun initRecyclerList() {
        with(binding) {
            recyclerHistory.layoutManager = LinearLayoutManager(requireContext())
            recyclerAdapter = HistoryAdapter()
            recyclerHistory.adapter = recyclerAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePickerDialog(startView: EditText) {
        val date = LocalDate.parse(startView.text, DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                val string = StringBuilder()

                if (dayOfMonth < 10) {
                    string.append("0$dayOfMonth.")
                } else {
                    string.append("$dayOfMonth.")
                }
                if (monthOfYear + 1 < 10) {
                    string.append("0${monthOfYear + 1}.")
                } else {
                    string.append("${monthOfYear + 1}.")
                }
                string.append("$year")
                startView.setText(string)
                if (!binding.radioShowHistoryAllTime.isChecked) {
                    viewModel.getHistoriesByDates(
                        binding.edPeekDateFrom.text.toString(),
                        binding.edPeekDateTo.text.toString(),
                        binding.radioMyDebt.isChecked
                    )
                }
            },
            date.year,
            date.monthValue - 1,
            date.dayOfMonth
        ).show()
    }
}