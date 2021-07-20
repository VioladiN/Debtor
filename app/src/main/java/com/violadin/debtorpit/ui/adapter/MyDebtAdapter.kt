package com.violadin.debtorpit.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.violadin.debtorpit.R

class MyDebtAdapter : RecyclerView.Adapter<MyDebtAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_debt_fragment, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}