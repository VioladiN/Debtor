package com.violadin.debtorpit.ui.infoaboutdebt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.violadin.debtorpit.R
import com.violadin.debtorpit.database.tables.Person
import com.violadin.debtorpit.databinding.InfoAboutDebtFragmentBinding
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class InfoAboutDebtFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private lateinit var binding: InfoAboutDebtFragmentBinding
    private val viewModel: InfoAboutDebtFragmentVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoAboutDebtFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().getInt("id").let { id ->
            viewModel.getPersonById(id)
        }

        with(binding) {
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.person.collect { person ->
                    if (person.created_time.isNullOrEmpty()) {
                        createdDate.visibility = View.GONE
                        dateTextview.visibility = View.GONE
                    } else {
                        dateTextview.visibility = View.VISIBLE
                        createdDate.visibility = View.VISIBLE
                        createdDate.text = person.created_time.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                    }

                    if (person.fio.isNullOrEmpty()) {
                        debtorNameText.visibility = View.GONE
                    } else {
                        debtorNameText.visibility = View.VISIBLE
                        debtorNameText.text = person.fio
                    }

                    if (person.debt == null) {
                        debtCount.visibility = View.GONE
                        debtTextview.visibility = View.GONE
                    } else {
                        debtTextview.visibility = View.VISIBLE
                        debtCount.visibility = View.VISIBLE
                        debtCount.text = person.debt.toString()
                    }
                }
            }
        }
    }
}