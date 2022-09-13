package com.violadin.debtorpit.ui.mydebts

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
import com.violadin.debtorpit.enums.PersonType
import com.violadin.debtorpit.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.my_debt_fragment.*
import kotlinx.android.synthetic.main.my_debt_fragment.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MyDebtFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager

    private var recyclerAdapter: MyDebtAdapter? = null
    private val viewModel: MyDebtFragmentVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.my_debt_fragment, container, false)
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
            val bundle = Bundle().apply {
                putString("type", PersonType.MY_DEBT_PERSON.type)
            }
            it.findNavController().navigate(R.id.my_debt_fragment_to_create_debt_fragment, bundle)
        }

        recycler_my_debt_persons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        parentView.recycler_my_debt_persons.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = MyDebtAdapter()
        parentView.recycler_my_debt_persons.adapter = recyclerAdapter
    }
}