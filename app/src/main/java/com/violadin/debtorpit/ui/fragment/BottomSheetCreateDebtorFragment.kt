package com.violadin.debtorpit.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.violadin.debtorpit.R
import com.violadin.debtorpit.domain.model.Person
import com.violadin.debtorpit.presentation.viewmodel.PersonViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_sheet_create_debtor_fragment.*
import kotlinx.android.synthetic.main.bottom_sheet_header_add_person.*

class BottomSheetCreateDebtorFragment(
    val viewModel: PersonViewModel
): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_create_debtor_fragment, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog?.setOnShowListener {
            Handler().postDelayed({
                val d = dialog as BottomSheetDialog
                val bottomSheet = d.findViewById<FrameLayout>(R.id.design_bottom_sheet)
                val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }, 0)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        create_debtor.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
            addPerson(
                Person(
                    firstName = edit_first_name.text.toString(),
                    lastName = edit_last_name.text.toString(),
                    debt = if (edit_debt.text.isEmpty()) 0.0 else edit_debt.text.toString().toDouble(),
                    phone = edit_phone.text.toString()
                )
            )
        }

        button_cancel.setOnClickListener {
            it.apply { isEnabled = false; postDelayed({isEnabled = true}, 1000) }
            dismiss()
        }

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        edit_first_name.postDelayed({
            edit_first_name.requestFocus()
            imm!!.showSoftInput(edit_first_name, 0)
        }, 100)
    }

    @SuppressLint("CheckResult")
    private fun addPerson(person: Person) {
        if (person.firstName.isNullOrEmpty()) {
            Toast.makeText(context, R.string.empty_field_first_name, Toast.LENGTH_SHORT).show()
            return
        }
        Flowable.fromCallable {
            viewModel.addPerson(person)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            dismiss()
        }
    }

    override fun getTheme(): Int =
        R.style.AppBottomSheetDialogTheme

}