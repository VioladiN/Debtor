package com.violadin.debtorpit.ui.debtors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.debt_for_me_fragment.*
import kotlinx.android.synthetic.main.debt_for_me_fragment.view.*
import javax.inject.Inject

@AndroidEntryPoint
class DebtForMeFragment: Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private var recyclerAdapter: DebtForMeAdapter? = null
    private val viewModel: DebtForMeFragmentVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.debt_for_me_fragment, container, false)
        initRecyclerList(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.persons.collect { persons ->
                recyclerAdapter?.let { adapter ->
                    if (persons.isEmpty()) {
                        list_is_empty_tv.visibility = View.VISIBLE
                        adapter.submitList(persons)
                    } else {
                        list_is_empty_tv.visibility = View.GONE
                        adapter.submitList(persons)
                    }
                }
            }
        }

        add_person.setOnClickListener {
            it.findNavController().navigate(R.id.debt_for_me_fragment_to_create_debt_fragment)
        }

        recycler_view_persons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && add_person.isShown)
                    add_person.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    add_person.show()
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun initRecyclerList(parentView: View) {
        parentView.recycler_view_persons.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = DebtForMeAdapter()
        parentView.recycler_view_persons.adapter = recyclerAdapter
    }
}