package com.example.smart_things.Presentation.Devise

import android.content.Context
import com.example.smart_things.Funtions.CloudFirebase.CloudFirebaseInterface
import com.example.smart_things.Presentation.Main.MainInterface
import com.example.smart_things.R

class ViewDevisePresente(cloudFirebaseInterface: CloudFirebaseInterface,context: Context):ViewDeviseInterface.DevisePresente{

    var view: ViewDeviseInterface.DeviseView? = null
    var cloudFirebaseInterface: CloudFirebaseInterface? = null
    val context:Context

    init {
        this.context = context
        this.cloudFirebaseInterface = cloudFirebaseInterface
    }

    override fun delet_devise(Email:String,deviceName:String) {
        cloudFirebaseInterface?.BorrarDevice(Email,deviceName,object:CloudFirebaseInterface.CloudCallback{
            override fun AutheonSuccessful() {
                view?.hideProgres()
                view?.toast(context.getString(R.string.Saccessful))
                view?.back()
            }
            override fun AutheonFailure(message: String?) {
                view?.hideProgres()
                view?.toast(context.getString(R.string.fallo))
            }
        })
    }

    override fun AttachView(view: ViewDeviseInterface.DeviseView) {
        this.view = view
    }

    override fun DettachView() {
        this.view = null
    }

    override fun isviewAttached(): Boolean {
        return view != null
    }

}