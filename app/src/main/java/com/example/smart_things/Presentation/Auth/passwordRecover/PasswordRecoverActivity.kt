package com.example.smart_things.Presentation.Auth.passwordRecover

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smart_things.Funtions.Autentication.AutheFuntionImpl
import com.example.smart_things.Presentation.Auth.LogIn.LogInActivity
import com.example.smart_things.R
import com.example.smart_things.Presentation.Auth.LogIn.BaceActivity
import kotlinx.android.synthetic.main.activity_password_recover_main.*

class PasswordRecoverActivity :
    BaceActivity(),PasswordRecoverInterface.PasswordRecoverView {

    lateinit var passwordrecoverPresenter : PasswordRecoverPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        passwordrecoverPresenter = PasswordRecoverPresenter(AutheFuntionImpl(),this)
        passwordrecoverPresenter.attachView(this)
        Buttons()
    }

    override fun getLayout(): Int {
        return R.layout.activity_password_recover_main
    }

    override fun Buttons() {
        Recover_bottom.setOnClickListener{
            PasswordRecover()
        }
    }

    override fun PasswordRecover() {
        if (!Recover_email.text.toString().isNullOrEmpty()){
            passwordrecoverPresenter.sentPasswordRecover(Recover_email.text.toString().trim())
        }else{
            Recover_email.error = getString(R.string.empty_edit_email)
        }
    }

    override fun navigateToLogIn() {
        val intent = Intent(this, LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun showProgress() {
        Recover_bottom.visibility = View.GONE
        progressBarRecover.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        Recover_bottom.visibility = View.VISIBLE
        progressBarRecover.visibility = View.GONE
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
        passwordrecoverPresenter.dettachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        passwordrecoverPresenter.dettachView()
    }

}