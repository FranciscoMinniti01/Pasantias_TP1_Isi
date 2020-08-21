package com.example.smart_things.Presentation.Devise

import android.content.DialogInterface
import com.example.smart_things.Presentation.Main.MainInterface

interface ViewDeviseInterface {

    interface DeviseView{
        fun getInstance()
        fun viewData()
        fun Buttoms()
        fun delet()
        fun back()

        fun showProgres()
        fun hideProgres()

        fun toast(messge:String)
        fun showMessage(title: String, message: String,
                        buttom1: String, listener1: DialogInterface.OnClickListener,
                        buttom2: String? = null, listener2: DialogInterface.OnClickListener?= null)
    }

    interface DevisePresente{
        fun delet_devise(Email:String,deviceName:String)
        fun AttachView(view: ViewDeviseInterface.DeviseView)
        fun DettachView()
        fun isviewAttached():Boolean
    }

}