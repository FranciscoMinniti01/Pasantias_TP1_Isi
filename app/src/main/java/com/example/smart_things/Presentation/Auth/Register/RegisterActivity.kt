package com.example.smart_things.Presentation.Auth.Register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smart_things.Funtions.Autentication.AutheFuntionImpl
import com.example.smart_things.Class.ModelUser
import com.example.smart_things.Presentation.Auth.LogIn.LogInActivity
import com.example.smart_things.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: AppCompatActivity(), RegisterInterfaz.RegisterView {

    lateinit var registerPresenter: RegisterPresenter

    var modelUser: ModelUser = ModelUser("","","",false,"")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerPresenter = RegisterPresenter(AutheFuntionImpl(),this)
        registerPresenter.attachView(this)

        Buttons()
    }

    override fun Buttons() {
        Re_register_buttom.setOnClickListener {
            registerPresenter.ButtonAction()
        }
    }

    override fun Register() {
        modelUser.Name = Re_edit_name.text.toString().trim()
        modelUser.Email = Re_edit_gmail.text.toString().trim()
        modelUser.Password = Re_edit_password.text.toString().trim()

        if (registerPresenter.checkEmptyEmail(modelUser.Email)){
            Re_edit_gmail.error = getString(R.string.empty_edit_email)
        }else if (registerPresenter.checkEmptyName(modelUser.Name)){
            Re_edit_name.error = getString(R.string.empty_edit_username)
        }else if (registerPresenter.checkEmptyPassword(modelUser.Password,Re_edit_password1.text.toString())){
            Re_edit_password.error = getString(R.string.empty_edit_password)
            Re_edit_password1.error = getString(R.string.empty_edit_password)
        }else if (!registerPresenter.checkEmails(modelUser.Email)){
            Re_edit_gmail.error = getString(R.string.invalid_email)
        }else if (!registerPresenter.checkequalPassword(modelUser.Password,Re_edit_password1.text.toString())){
            Re_edit_password.error = getString(R.string.passwords_dont_mach)
            Re_edit_password1.error = getString(R.string.passwords_dont_mach)
        }else{
            registerPresenter.CreateUserWithEmailAndPassword(modelUser.Email!!,modelUser.Password!!)
        }
    }

    override fun NavigateToLogIn() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }

    override fun saveAccount() {
        NavigateToLogIn()
    }

    override fun showProgress() {
        Re_register_buttom.visibility = View.INVISIBLE
        Re_progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        Re_register_buttom.visibility = View.VISIBLE
        Re_progressBar.visibility = View.GONE
    }

    override fun changeButton(verifi :Boolean) {
        if (verifi) Re_register_buttom.text = getText(R.string.buttom_verify_email)
        else Re_register_buttom.text = getText(R.string.buttom_register)
    }

    override fun ShowMessage(title: String, message: String, buttom1: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttom1, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        registerPresenter.dettachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        registerPresenter.dettachView()
    }

}