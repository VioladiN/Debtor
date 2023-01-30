package com.violadin.debtorpit.ui.infoaboutdebt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.violadin.debtorpit.databinding.InfoAboutDebtFragmentBinding
import com.violadin.debtorpit.enums.DebtType
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
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
        viewModel.getPersonById(requireArguments().getInt("id"))

        with(binding) {
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.person.collect { person ->
                    person?.let {
                        if (person.created_time.isNullOrEmpty()) {
                            createdDate.visibility = View.GONE
                            dateTextview.visibility = View.GONE
                        } else {
                            dateTextview.visibility = View.VISIBLE
                            createdDate.visibility = View.VISIBLE
                            createdDate.text =
                                person.created_time.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
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
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.updateDebtResult.collect {
                    if (it.isNotEmpty())
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.deletePersonResult.collect {
                    if (it) {
                        navigationManager.bottomBarController?.navigateUp()
                    }
                }
            }

            decreaseDebtButton.setOnClickListener {
                ChangeDebtDialog(requireContext()) { debt, desctription ->
                    var finalDebt = viewModel.person.value!!.debt!! - debt
                    if (finalDebt <= 0) {
                        finalDebt = 0.0
                    }
                    viewModel.changeDebtOfPerson(viewModel.person.value!!.id!!, finalDebt)
                }.show(DebtType.DECREASE.type)
            }

            increaseDebtButton.setOnClickListener {
                ChangeDebtDialog(requireContext()) { debt, desctription ->
                    val finalDebt = viewModel.person.value!!.debt!! + debt
                    viewModel.changeDebtOfPerson(viewModel.person.value!!.id!!, finalDebt)
                }.show(DebtType.INCREASE.type)
            }

            deleteDebtorButton.setOnClickListener {
                DeletePersonDialog(requireContext()) {
                    viewModel.deletePerson(viewModel.person.value!!)
                }.show()
            }
        }
    }
}