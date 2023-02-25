package com.violadin.debtorpit.ui.infoaboutdebt

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.DialogChangeDebtBinding
import com.violadin.debtorpit.enums.DebtType
import com.violadin.debtorpit.utils.DAY_MONTH_YEAR_PATTERN
import com.violadin.debtorpit.utils.stringCurrentTime

class ChangeDebtDialog(
    context: Context,
    val buttonListener: (Int, String) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogChangeDebtBinding
    private var dialogType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogChangeDebtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDialog(dialogType)
        window?.decorView?.setBackgroundResource(android.R.color.transparent)
    }

    private fun initDialog(type: String?) {
        with(binding) {
            textPeekDate.text = stringCurrentTime(DAY_MONTH_YEAR_PATTERN)

            when (type) {
                DebtType.INCREASE.type -> {
                    titleText.text = context.getText(R.string.increase_debt_title)
                    textDateOfDebt.text = context.getText(R.string.date_adding_debt)
                    addDebts.text = context.getText(R.string.debt_add)
                }
                DebtType.DECREASE.type -> {
                    titleText.text = context.getText(R.string.decrease_debt_title)
                    textDateOfDebt.text = context.getText(R.string.date_decreasing_debt)
                    addDebts.text = context.getText(R.string.debt_remove)
                }
                else -> throw IllegalStateException("Illegal state of ChangeDebtDialog")
            }

            dialogClose.setOnClickListener {
                dismiss()
            }

            addDebts.setOnClickListener {
                if (debtEd.text.isNullOrEmpty()) {
                    Toast.makeText(context, context.getText(R.string.wrong_filed_debt), Toast.LENGTH_SHORT).show()
                } else {
                    buttonListener(debtEd.text.toString().toInt(), descriptionEd.text.toString())
                    dismiss()
                }
            }
        }
    }

    fun show(
        type: String
    ) {
        dialogType = type
        super.show()
    }
}