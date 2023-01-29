package com.violadin.debtorpit.ui.mydebts

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
import com.violadin.debtorpit.databinding.MyDebtFragmentBinding
import com.violadin.debtorpit.enums.PersonType
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyDebtFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private var recyclerAdapter: MyDebtAdapter? = null
    private val viewModel: MyDebtFragmentVM by viewModels()
    private lateinit var binding: MyDebtFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyDebtFragmentBinding.inflate(inflater, container, false)
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
                    putString("type", PersonType.MY_DEBT_PERSON.type)
                }
                it.findNavController().navigate(R.id.my_debt_fragment_to_create_debt_fragment, bundle)
            }

            recyclerMyDebtPersons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        binding.recyclerMyDebtPersons.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = MyDebtAdapter {
            val bundle = Bundle().apply {
                putInt("id", it.id!!)
            }
            findNavController().navigate(R.id.my_debt_fragment_to_info_about_debtor_fragment, bundle)
        }
        binding.recyclerMyDebtPersons.adapter = recyclerAdapter
    }
}