package com.example.smart_things.Presentation.Auth.LogIn

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smart_things.*
import com.example.smart_things.Funtions.Autentication.AutheFuntionImpl
import com.example.smart_things.Presentation.Auth.Register.RegisterActivity
import com.example.smart_things.Class.ModelUser
import com.example.smart_things.Presentation.Main.MainActivity
import com.example.smart_things.Presentation.Auth.passwordRecover.PasswordRecoverActivity
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity : BaceActivity(), LogInInterfaz.View {

    lateinit var logInPresenter:LogInInterfaz.Presenter

    var modelUser = ModelUser("","","",false,"")

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        logInPresenter = LogInPresenter(AutheFuntionImpl(),this)
        logInPresenter.attachView(this)

        IsAlredyInit()
        Buttons()
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun IsAlredyInit() {
        val prefs = getSharedPreferences(getString(R.string.prefsFile),Context.MODE_PRIVATE)
        modelUser.Email = prefs.getString("email",null)
        modelUser.Password = prefs.getString("password",null)
        modelUser.Room = prefs.getString("room",null)
        modelUser.Name = prefs.getString("name",null)
        if (logInPresenter.checkFields(modelUser?.Email,modelUser?.Password)){
            Lo_layout.visibility = View.INVISIBLE
            NavigateToMain()
        }
    }

    override fun Buttons() {
        Lo_bottom_login.setOnClickListener{
            modelUser.Email = Lo_edit_gmail.text.toString().trim()
            modelUser.Password = Lo_edit_password.text.toString().trim()
            SingIn()
        }
        Lo_bottom_register.setOnClickListener{
            NavigateToRegister()
        }
        button_recover_password.setOnClickListener{
            val intent = Intent(this, PasswordRecoverActivity::class.java)
            startActivity(intent)
        }
    }

    override fun SingIn() {
        if (logInPresenter.checkFields(modelUser.Email, modelUser.Password)){
            logInPresenter.signInUserWithEmailAndPassword(modelUser.Email!!, modelUser.Password!!)
        }else{
            Lo_edit_gmail.error = getString(R.string.empty_edit_email)
            Lo_edit_password.error = getString(R.string.empty_edit_password)
        }
    }



    override fun saveAccount() {
        if (Lo_seve_pass.isChecked){
            val prefs = getSharedPreferences(getString(R.string.prefsFile),Context.MODE_PRIVATE).edit()
            prefs.putString("email",modelUser.Email)
            prefs.putString("password",modelUser.Password)
            prefs.putString("room",modelUser.Room)
            prefs.putString("name",modelUser.Name)
            prefs.apply()
        }
        NavigateToMain()
    }

    override fun NavigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply{
            putExtra("come_from", "LogIn")
            val bundle: Bundle = Bundle()
            bundle.putSerializable("data", modelUser)
            putExtras(bundle)
        }
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun NavigateToRegister() {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
    }

    override fun showProgress(){
        Lo_bottom_login.visibility = View.INVISIBLE
        Lo_bottom_register.visibility = View.INVISIBLE
        progressBarLogIN.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        Lo_bottom_login.visibility = View.VISIBLE
        Lo_bottom_register.visibility = View.VISIBLE
        progressBarLogIN.visibility = View.GONE
    }

    override fun ShowMessage( title: String, message: String, buttom1: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttom1,null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun toast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    override fun getText(value: String):String{
        return getString(R.string.accept)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        logInPresenter.dettachView()
        //corrutina
        logInPresenter.dettachJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        logInPresenter.dettachView()
        //corrutina
        logInPresenter.dettachJob()
    }

}