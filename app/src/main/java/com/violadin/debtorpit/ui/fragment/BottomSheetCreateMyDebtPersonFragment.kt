package com.violadin.debtorpit.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.MyDebtPerson
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_sheet_create_debtor_fragment.*
import kotlinx.android.synthetic.main.bottom_sheet_header_add_person.*

class BottomSheetCreateMyDebtPersonFragment(
    val viewModel: PersonViewModel
): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_create_debtor_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_phone.visibility = View.GONE

        create_debtor.setOnClickListener {
            insertPersonMyDebt(
                MyDebtPerson(
                    firstName = edit_first_name.text.toString(),
                    lastName = edit_last_name.text.toString(),
                    debt = if (edit_debt.text.isEmpty()) 0.0 else edit_debt.text.toString().toDouble()
                )
            )
        }

        tv_add.setOnClickListener {
            insertPersonMyDebt(
                MyDebtPerson(
                    firstName = edit_first_name.text.toString(),
                    lastName = edit_last_name.text.toString(),
                    debt = if (edit_debt.text.isEmpty()) 0.0 else edit_debt.text.toString().toDouble()
                )
            )
        }

        button_cancel.setOnClickListener {
            dismiss()
        }

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        edit_first_name.postDelayed({
            edit_first_name.requestFocus()
            imm!!.showSoftInput(edit_first_name, 0)
        }, 100)
    }

    @SuppressLint("CheckResult")
    private fun insertPersonMyDebt(person: MyDebtPerson) {
        Flowable.fromCallable {
            if (!person.firstName.isNullOrEmpty())
                viewModel.insertPersonMyDebt(person)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (!person.firstName.isNullOrEmpty()) {
                Toast.makeText(context, R.string.person_added, Toast.LENGTH_SHORT).show()
                dismiss()
            } else
                Toast.makeText(context, R.string.empty_field_first_name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getTheme(): Int =
        R.style.AppBottomSheetDialogTheme

}