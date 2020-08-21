package com.example.smart_things.Presentation.Auth.passwordRecover

import android.content.Context
import com.example.smart_things.Funtions.Autentication.AutheInteractor
import com.example.smart_things.Funtions.Autentication.AuthenticationException
import com.example.smart_things.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PasswordRecoverPresenter(autheInteractor: AutheInteractor,context: Context):PasswordRecoverInterface.PasswordRecoverPresenter,CoroutineScope {

    var autheInteractor: AutheInteractor? = null
    var view:PasswordRecoverInterface.PasswordRecoverView? = null

    val context:Context

    var  job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        this.autheInteractor = autheInteractor
        this.context = context
    }

    override fun attachView(view: PasswordRecoverInterface.PasswordRecoverView) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun sentPasswordRecover(email: String) {
        launch {
            try {
                view?.showProgress()
                autheInteractor?.sendPasswordResetEmail(email)
                view?.hideProgress()
                view?.navigateToLogIn()
                view?.ShowMessage(context.getString(R.string.verify),context.getString(R.string.email_chang_password),context.getString(R.string.accept))
            }catch (e:AuthenticationException){
                view?.hideProgress()
                view?.ShowMessage(context.getString(R.string.Error),e.message!!,context.getString(R.string.accept))
            }
        }
    }

}