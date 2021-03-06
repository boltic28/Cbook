package com.boltic28.recyclertask.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.boltic28.recyclertask.R
import com.boltic28.recyclertask.classes.BaseInstance
import com.boltic28.recyclertask.service.ItemService

class DeleteAlertDialog(private val item: BaseInstance, private val flagUndo: Boolean) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity).apply {
            setMessage(resources.getString(R.string.dialog_alert))
            setPositiveButton(
                R.string.dialog_alert_delete
            ) { _, _ ->
                if (flagUndo) {
                    (activity as? ItemService)?.removeItemUndo(item)
                }else{
                    (activity as? ItemService)?.removeItem(item)
                }
            }
            setNegativeButton(
                R.string.dialog_alert_cancel
            ) { _, _ ->
                (activity as? ItemService)?.cancel()
                dismiss()
            }
        }
        builder.setMessage(R.string.dialog_alert)
        return builder.create()
    }
}