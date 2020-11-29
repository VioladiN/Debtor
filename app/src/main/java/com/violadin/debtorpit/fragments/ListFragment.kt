package com.violadin.debtorpit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.adapter.ListOfPersonsAdapter
import com.violadin.debtorpit.viewmodel.PersonViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListFragment: Fragment() {

    private lateinit var model: PersonViewModel

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        val view = inflater.inflate(R.layout.list_activity, container, false)
        model = ViewModelProvider(this).get(PersonViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list_item)
        val linearLayoutManager = LinearLayoutManager(view.context,
                RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        model.allPersons.observe(viewLifecycleOwner, Observer {
            persons -> recyclerView.adapter = ListOfPersonsAdapter(persons)
        })

        val itemTouchHelper1 =
                object :
                        ItemTouchHelper.SimpleCallback(0,
                                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                GlobalScope.launch {
                    model.allPersons.value?.
                    get(viewHolder.adapterPosition)?.let { model.delete(it) }
                }
                Toast.makeText(
                        view.context,
                        recyclerView.adapter?.itemCount.toString(),
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelper1)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        return view
    }
}