package com.violadin.debtorpit.ui.infoaboutdebt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.InfoAboutDebtFragmentBinding
import com.violadin.debtorpit.enums.DebtType
import com.violadin.debtorpit.enums.PersonType
import com.violadin.debtorpit.navigation.NavigationManager
import com.violadin.debtorpit.ui.MainActivity
import com.violadin.debtorpit.utils.longCurrentTime
import com.violadin.debtorpit.utils.longTimeToString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import javax.inject.Inject

@AndroidEntryPoint
class InfoAboutDebtFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private lateinit var binding: InfoAboutDebtFragmentBinding
    private var recyclerAdapter: HistoryAdapter? = null
    private val viewModel: InfoAboutDebtFragmentVM by viewModels()
    private var personJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoAboutDebtFragmentBinding.inflate(inflater, container, false)
        initHistoryList()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPersonById(requireArguments().getInt("id"))
        viewModel.getHistory(requireArguments().getInt("id"))

        with(binding) {
            personJob = viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.person.collect { person ->
                    person.id?.let {
                        if (person.createdTime == null) {
                            createdDate.visibility = View.GONE
                            dateTextview.visibility = View.GONE
                        } else {
                            dateTextview.visibility = View.VISIBLE
                            createdDate.visibility = View.VISIBLE
                            createdDate.text = longTimeToString(person.createdTime)
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
                viewModel.deletePersonResult.collect {
                    if (it) {
                        navigationManager.bottomBarController?.navigateUp()
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.history.collect {
                    recyclerAdapter?.submitList(it.reversed())
                }
            }

            decreaseDebtButton.setOnClickListener {
                ChangeDebtDialog(requireContext()) { debt, desctription ->
                    var finalDebt = viewModel.person.value.debt!! - debt
                    if (finalDebt <= 0) {
                        finalDebt = 0.0
                    }
                    viewModel.changeDebtOfPerson(
                        viewModel.person.value.id!!,
                        finalDebt,
                        DebtType.DECREASE.type,
                        debt.toDouble(),
                        desctription,
                        longCurrentTime(),
                        viewModel.person.value.type!!,
                        viewModel.person.value.fio!!
                    )
                }.show(DebtType.DECREASE.type)
            }

            increaseDebtButton.setOnClickListener {
                ChangeDebtDialog(requireContext()) { debt, desctription ->
                    val finalDebt = viewModel.person.value.debt!! + debt
                    viewModel.changeDebtOfPerson(
                        viewModel.person.value.id!!,
                        finalDebt,
                        DebtType.INCREASE.type,
                        debt.toDouble(),
                        desctription,
                        longCurrentTime(),
                        viewModel.person.value.type!!,
                        viewModel.person.value.fio!!
                    )
                }.show(DebtType.INCREASE.type)
            }

            deleteDebtorButton.setOnClickListener {
                DeletePersonDialog(requireContext()) {
                    viewModel.deletePerson(viewModel.person.value, personJob)
                }.show()
            }

            imageviewEdit.setOnClickListener {
                UpdatePersonInfoDialog(requireContext()) { fio, phone ->
                    viewModel.updatePersonInfo(
                        viewModel.person.value.id!!,
                        fio,
                        phone
                    )
                }.show(viewModel.person.value.fio!!, viewModel.person.value.phone!!)
            }

            imageviewCall.setOnClickListener {
                viewModel.person.value.phone?.let { phone ->
                    val uri = "tel:" + phone.trim()
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse(uri)
                    startActivity(intent)
                }
            }
        }
    }

    private fun initHistoryList() {
        binding.profileHistory.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = HistoryAdapter()
        binding.profileHistory.adapter = recyclerAdapter
    }

    private fun setTitle() {
        when (requireArguments().getString("type")) {
            PersonType.MY_DEBT_PERSON.type -> {
                (activity as MainActivity).changeHeader(R.string.info_about_my_debt_label)
            }
            PersonType.DEBT_FOR_ME_PERSON.type -> {
                (activity as MainActivity).changeHeader(R.string.info_about_debt_for_me_label)
            }
            else -> throw IllegalStateException("Illegal state of InfoAboutDebtFragment")
        }
    }
}