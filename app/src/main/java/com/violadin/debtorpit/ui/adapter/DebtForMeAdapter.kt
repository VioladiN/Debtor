package com.violadin.debtorpit.ui.adapter

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import kotlinx.android.synthetic.main.recyclerview_row_debt_for_me.view.*
import java.lang.Exception

class DebtForMeAdapter : RecyclerView.Adapter<DebtForMeAdapter.ViewHolder>() {

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