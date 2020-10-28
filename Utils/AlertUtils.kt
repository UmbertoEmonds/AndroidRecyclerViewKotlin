package com.umbertoemonds.myapplication.Utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.umbertoemonds.myapplication.R

class AlertUtils {

    companion object {

        fun showErrorMessage(context:Context, message:String){
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.error_title)
            builder.setMessage(message)
            builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
            builder.show()
        }
        fun showMessage(context:Context, message:String){
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.alert_title)
            builder.setMessage(message)
            builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
            builder.show()
        }

    }

}