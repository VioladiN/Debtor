package com.violadin.debtorpit.ui.debtors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.DebtForMeFragmentBinding
import com.violadin.debtorpit.enums.PersonType
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DebtForMeFragment: Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private var recyclerAdapter: DebtForMeAdapter? = null
    private val viewModel: DebtForMeFragmentVM by viewModels()
    private lateinit var binding: DebtForMeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DebtForMeFragmentBinding.inflate(inflater, container, false)
        initRecyclerList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.persons.collect { persons ->
                    recyclerAdapter?.let { adapter ->
                        if (persons.isEmpty()) {
                            listIsEmptyTv.visibility = View.VISIBLE
                            adapter.submitList(persons)
                        } else {
                            listIsEmptyTv.visibility = View.GONE
                            adapter.submitList(persons)
                        }
                    }
                }
            }

            addPerson.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("type", PersonType.DEBT_FOR_ME_PERSON.type)
                }
                it.findNavController().navigate(R.id.debt_for_me_fragment_to_create_debt_fragment, bundle)
            }

            recyclerViewPersons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 || dy < 0 && addPerson.isShown)
                        addPerson.hide()
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                        addPerson.show()
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    private fun initRecyclerList() {
        with(binding) {
            recyclerViewPersons.layoutManager = LinearLayoutManager(requireContext())
            recyclerAdapter = DebtForMeAdapter {
                val bundle = Bundle().apply {
                    putInt("id", it.id!!)
                }
                findNavController().navigate(R.id.debt_for_me_fragment_to_info_about_debtor_fragment, bundle)
            }
            recyclerViewPersons.adapter = recyclerAdapter
        }
    }
}