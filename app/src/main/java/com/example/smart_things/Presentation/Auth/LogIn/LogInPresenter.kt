package com.example.smart_things.Presentation.Auth.LogIn

import android.content.Context
import com.example.smart_things.Funtions.Autentication.AutheInteractor
import com.example.smart_things.Funtions.Autentication.AuthenticationException
import com.example.smart_things.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LogInPresenter(autheinteractor:AutheInteractor,context: Context): LogInInterfaz.Presenter, CoroutineScope {

    var view:LogInInterfaz.View? = null
    var autheInteractor: AutheInteractor? = null

    var context:Context

    //corrutina
    private val  job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        this.autheInteractor = autheinteractor
        this.context = context
    }

    override fun attachView(view: LogInInterfaz.View) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    //corrutina
    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun signInUserWithEmailAndPassword(email: String, password: String) {
        launch {
            view?.showProgress()
            try {
                autheInteractor?.signInWithemailandpassword(email,password)
                if (isViewAttached()){
                    view?.hideProgress()
                    view?.saveAccount()
                }
            }catch (e: AuthenticationException){
                if (isViewAttached()){
                    view?.hideProgress()
                    view?.ShowMessage(context.getString(R.string.Error),e.message!!,context.getString(R.string.accept))
                }
            }
        }
    }

    override fun checkFields(email: String?, password: String?): Boolean {
        return !(email.isNullOrEmpty() || password.isNullOrEmpty())
    }

}