package com.example.smart_things.Presentation.Configurations.UserConfig

import android.accounts.AuthenticatorException
import android.view.View
import com.example.smart_things.Funtions.Autentication.AutheInteractor
import com.example.smart_things.Presentation.Auth.LogIn.LogInInterfaz
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UserConfigPresenter(/*autheinteractor: AutheInteractor*/)/*:UserConfigInterface.UserConfigPresenter*/ {

    /*var view:UserConfigInterface.UserConfigView? = null
    var autheInteractor: AutheInteractor? = null

    init {
        this.autheInteractor = autheinteractor
    }

    override fun attachView(view: UserConfigInterface.UserConfigView) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }


    override fun changePassword(newPassword: String) {
        view?.showProgress()
        autheInteractor?.changePassword(newPassword,object:AutheInteractor.Callback{
            override fun AutheonSuccessful() {
                view?.hideProgress()
                //reverifiuser()
                view?.ShowMessage("","se envio un mail de cambio de contraseña")
            }
            override fun AutheonFailure(message: String?) {
                view?.hideProgress()
            }
        })
    }

    override fun changeUserName(User: String) {

    }

    override fun changeEmail(newEmail: String) {
        view?.showProgress()
        autheInteractor?.changeEmail(newEmail,object:AutheInteractor.Callback{
            override fun AutheonSuccessful() {
                view?.hideProgress()
                reverifiuser()
            }
            override fun AutheonFailure(message: String?) {
                view?.hideProgress()
            }
        })
    }

    override fun reverifiuser(){
        autheInteractor?.reverificEmail()
    }*/

}