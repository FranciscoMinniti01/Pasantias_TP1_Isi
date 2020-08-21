package com.example.smart_things.Presentation.Configurations.UserConfig

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smart_things.Class.ModelUser
import com.example.smart_things.Funtions.Autentication.AutheFuntionImpl
import com.example.smart_things.R
import kotlinx.android.synthetic.main.activity_user_config.*

class UserConfigActivity : AppCompatActivity()/*,UserConfigInterface.UserConfigView*/ {

    /*var modelUser = ModelUser("","","",false,"")
    lateinit var userConfigPresenter:UserConfigInterface.UserConfigPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_config)

        userConfigPresenter = UserConfigPresenter(AutheFuntionImpl())
        userConfigPresenter.attachView(this)

        Buttons()

    }

    override fun SaveAcoount() {
        val prefs = getSharedPreferences(getString(R.string.prefsFile),Context.MODE_PRIVATE).edit()
        prefs.putString("email",modelUser.Email)
        prefs.putString("password",modelUser.Password)
        prefs.putString("room",modelUser.Room)
        prefs.putString("name",modelUser.Name)
        prefs.apply()
        toast("Accion Exitosa")
        finish()
    }

    override fun Buttons() {
        /*buttom_restar_email.setOnClickListener{
            lineChangeEmail.visibility = View.VISIBLE
            lineChangePassword.visibility = View.GONE
            lineChangeUserName.visibility = View.GONE
        }*/
        buttom_restar_password.setOnClickListener{
            //lineChangeEmail.visibility = View.GONE
            lineChangePassword.visibility = View.VISIBLE
            lineChangeUserName.visibility = View.GONE
        }
        buttom_restar_nameuser.setOnClickListener{
            //lineChangeEmail.visibility = View.GONE
            lineChangePassword.visibility = View.GONE
            lineChangeUserName.visibility = View.VISIBLE
        }
        /*buttom_change_Email.setOnClickListener{
            userConfigPresenter.changeEmail(channge_edid_gmail.text.toString().trim())
        }*/
        buttom_change_password.setOnClickListener{
            userConfigPresenter.changePassword(channge_edid_password.text.toString().trim())
        }
        buttom_change_UserName.setOnClickListener{
            userConfigPresenter.changeUserName(channge_edid_UserName.text.toString().trim())
        }
    }

    override fun GetUser() {
        val prefs = getSharedPreferences(getString(R.string.prefsFile), Context.MODE_PRIVATE)
        modelUser.Email = prefs.getString("email","null")
        modelUser.Password = prefs.getString("password",null)
        modelUser.Room = prefs.getString("room",null)
        modelUser.Name = prefs.getString("name",null)
    }

    override fun showProgress() {
        /*progres_change_email.visibility = View.VISIBLE
        buttom_change_Email.visibility = View.GONE*/

        progres_change_password.visibility = View.VISIBLE
        buttom_change_password.visibility = View.GONE

        progres_change_name.visibility = View.VISIBLE
        buttom_change_UserName.visibility = View.GONE
    }

    override fun hideProgress() {
        /*progres_change_email.visibility = View.GONE
        buttom_change_Email.visibility = View.VISIBLE*/

        progres_change_password.visibility = View.GONE
        buttom_change_password.visibility = View.VISIBLE

        progres_change_name.visibility = View.GONE
        buttom_change_UserName.visibility = View.VISIBLE
    }

    override fun ShowMessage(title: String, message: String, buttom1: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttom1,null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun toast(message: String) {
        android.widget.Toast.makeText(this,message, android.widget.Toast.LENGTH_LONG).show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        userConfigPresenter.dettachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        userConfigPresenter.dettachView()
    }*/

}