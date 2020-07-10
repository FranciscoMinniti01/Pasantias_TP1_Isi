package com.example.smart_things

import android.app.AlertDialog
import android.content.Context

class ShowAlert {

    fun Showalert (context : Context,title :String,message :String, buttom1 :String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttom1,null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}