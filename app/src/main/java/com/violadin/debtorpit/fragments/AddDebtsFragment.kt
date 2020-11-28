package com.violadin.debtorpit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.adapter.AddDebtsPersonsAdapter
import com.violadin.debtorpit.adapter.ListOfPersonsAdapter
import com.violadin.debtorpit.viewmodel.PersonViewModel

class AddDebtsFragment: Fragment() {

    private lateinit var model: PersonViewModel

    companion object {
        fun newInstance(): AddDebtsFragment {
            return AddDebtsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.adddebts_activity, container, false)
        model = ViewModelProvider(this).get(PersonViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list_add_debt)
        val linearLayoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        model.allPersons.observe(viewLifecycleOwner, Observer {
            persons -> recyclerView.adapter = AddDebtsPersonsAdapter(persons)
        })
        return view
    }
}