package com.violadin.debtorpit.ui.infoaboutdebt

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.violadin.debtorpit.databinding.DialogDeletePersonBinding

class DeletePersonDialog(
    context: Context,
    val buttonListener: () -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogDeletePersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeletePersonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDialog()
        window?.decorView?.setBackgroundResource(android.R.color.transparent)
    }

    private fun initDialog() {
        with(binding) {

            cancelText.setOnClickListener {
                dismiss()
            }

            deleteText.setOnClickListener {
                buttonListener()
                dismiss()
            }
        }
    }
}