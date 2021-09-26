package com.violadin.debtorpit.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.recyclerview_row_debt_for_me.view.*

class MultiDebtAdapter : RecyclerView.Adapter<MultiDebtAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row_debt_for_me, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}