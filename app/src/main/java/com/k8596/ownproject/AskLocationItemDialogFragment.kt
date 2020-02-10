package com.k8596.ownproject

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_ask_new_destination.*
import kotlinx.android.synthetic.main.dialog_ask_new_destination.view.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import java.lang.Exception

class locationListItemDialogFragment : DialogFragment() {

    private lateinit var mListener: AddDialogListener

    interface AddDialogListener {
        fun onDialogPositiveClick(item: LocationListItem)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as AddDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement AddDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Custom layout for dialog
            val customView = LayoutInflater.from(context).inflate(R.layout.dialog_ask_new_destination, null)

            // Build dialog
            val builder = AlertDialog.Builder(it)
            builder.setView(customView)
            builder.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok) { dialog, id ->

                    var name = customView.nameEditText.text.toString()
                    if (name.isEmpty()) {name = "default"}
                    try{
                        val lat = customView.latEditText.text.toString().toDouble()
                        val lng = customView.lngEditText.text.toString().toDouble()
                        val item = LocationListItem(0, name, lat, lng)
                        mListener.onDialogPositiveClick(item)
                        dialog.dismiss()
                        //Start maps with marker on press
                        val data = lat.toString()+ "," + lng.toString() + "," + name
                        val intent = Intent(context, MapsActivity::class.java)
                        intent.putExtra("latlng", data)
                        startActivity(intent)}
                    catch(e: Exception){dialog.dismiss()}
                }
                .setNegativeButton(R.string.dialog_cancel) { dialog, id ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }
}