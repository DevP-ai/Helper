package com.example.bottomnavigation.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.bottomnavigation.R

import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Config {
    private var dialog : AlertDialog? = null
    private var alertDialog : AlertDialog? = null
    fun showDialog(context: Context){
        dialog   = AlertDialog.Builder(context).setView(R.layout.progress_dialog).setCancelable(false).create()
        dialog!!.show()
    }

    fun hideDialog(){
        dialog?.dismiss()
    }
    const val POST_STATUS_AVAILABLE="AVAILABLE"
    const val POST_STATUS_SOLD="SOLD"

    val categories = arrayOf(
        "Bricks",
        "Steels Bars",
        "Cement",
        "Electrician",
        "Furniture",
        "Mechanic"
    )
}