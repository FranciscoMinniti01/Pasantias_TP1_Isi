package com.example.smart_things.Presentation.Devise

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.smart_things.Class.ModelDevices
import com.example.smart_things.Class.ModelUser
import com.example.smart_things.Funtions.CloudFirebase.CloudFirebaseImpl
import com.example.smart_things.Presentation.Main.MainPresenter
import com.example.smart_things.R
import kotlinx.android.synthetic.main.activity_view_devise.*

class ViewDeviseActivity : AppCompatActivity(),ViewDeviseInterface.DeviseView {

    var Devise:ModelDevices = ModelDevices(null,null,null,null)
    var modelUser:ModelUser = ModelUser(null,null,null, false,null)

    lateinit var ViewPresenter:ViewDevisePresente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_devise)

        ViewPresenter = ViewDevisePresente(CloudFirebaseImpl(),this)
        ViewPresenter.AttachView(this)

        Buttoms()
        getInstance()
        viewData()

    }

    override fun getInstance(){
        val bundle = intent.extras
        if (bundle != null){
            Devise = bundle.getSerializable("data") as ModelDevices
        }
        val prefs = getSharedPreferences(getString(R.string.prefsFile), Context.MODE_PRIVATE)
        modelUser.Email = prefs.getString("email",null)
    }



    override fun viewData() {
        DeviseName.setText(Devise.Name.toString())
        DeviseRoom.setText(Devise.Room.toString())
        DeviseFlor.setText(Devise.Floor.toString())
        DeviseType.setText(Devise.Type.toString())
    }

    override fun Buttoms() {
        Devise_back_buttom.setOnClickListener{
            finish()
        }
        buttom_delet_devise.setOnClickListener{
            showMessage(getString(R.string.Alert),getString(R.string.Sure),getString(R.string.yes),object:DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    showProgres()
                    delet()
                }
            },getString(R.string.no),null)
        }
    }

    override fun delet() {
        ViewPresenter.delet_devise(modelUser.Email!!,Devise.Name!!)
    }

    override fun back() {
        finish()
    }

    override fun showProgres() {
        progres_space.visibility = View.GONE
        progressBarviewdevise.visibility = View.VISIBLE
    }

    override fun hideProgres() {
        progres_space.visibility = View.VISIBLE
        progressBarviewdevise.visibility = View.GONE
    }


    override fun toast(messge: String) {
        android.widget.Toast.makeText(this,messge, android.widget.Toast.LENGTH_LONG).show()
    }

    override fun showMessage(title: String, message: String,
                             buttom1: String, listener1:DialogInterface.OnClickListener,
                             buttom2: String?, listener2:DialogInterface.OnClickListener?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttom1,listener1)
        if (buttom2 != null ) {
            builder.setNegativeButton(buttom2, listener2)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}