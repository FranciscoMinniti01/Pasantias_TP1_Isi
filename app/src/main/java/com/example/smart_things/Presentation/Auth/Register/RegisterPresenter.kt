package com.example.smart_things.Presentation.Auth.Register

import android.content.Context
import androidx.core.util.PatternsCompat
import com.example.smart_things.Funtions.Autentication.AutheInteractor
import com.example.smart_things.R

class RegisterPresenter (autheinteractor: AutheInteractor,context: Context): RegisterInterfaz.RegisterPresenter{

    var view:RegisterInterfaz.RegisterView? = null
    var autheInteractor: AutheInteractor? = null

    val context:Context

    var buttonAction :Boolean = false

    init {
        this.autheInteractor = autheinteractor
        this.context = context
    }

    override fun attachView(view: RegisterInterfaz.RegisterView) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun ButtonAction(){
        if (!buttonAction){
            view?.Register()
        }else{
            view?.NavigateToLogIn()
        }
    }

    override fun CreateUserWithEmailAndPassword(email: String, password: String) {
        view?.showProgress()
        autheInteractor?.CreateUserWithemailandpassword(email,password,object:AutheInteractor.Callback{
            override fun AutheonSuccessful() {
                if (isViewAttached()){
                    view?.hideProgress()
                    SentvalidedEmail()
                }
            }
            override fun AutheonFailure(message: String?) {
                if (isViewAttached()){
                    view?.hideProgress()
                    view?.ShowMessage(context.getString(R.string.Error),message!!,context.getString(R.string.accept))
                }
            }
        })
    }

    override fun SentvalidedEmail() {
        view?.showProgress()
        autheInteractor?.sentValideEmail(object:AutheInteractor.Callback{
            override fun AutheonSuccessful() {
                view?.hideProgress()
                buttonAction = true
                view?.ShowMessage(context.getString(R.string.verify),context.getString(R.string.verifyEmail),context.getString(R.string.accept))
                view?.changeButton(true)
            }
            override fun AutheonFailure(message: String?) {
                view?.hideProgress()
                view?.ShowMessage(context.getString(R.string.Error),context.getString(R.string.error_tosent_verifi),context.getString(R.string.accept))
                SentvalidedEmail()
            }
        })
    }

    override fun checkEmptyName(name: String?): Boolean {
        return name.isNullOrEmpty()
    }

    override fun checkEmptyEmail(email: String?): Boolean {
        return email.isNullOrEmpty()
    }

    override fun checkEmptyPassword(password: String?, password1: String?): Boolean {
        return (password.isNullOrEmpty() || password1.isNullOrEmpty())
    }

    override fun checkEmails(email: String?): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun checkequalPassword(password1: String?, password: String): Boolean {
        return password == password1
    }

}