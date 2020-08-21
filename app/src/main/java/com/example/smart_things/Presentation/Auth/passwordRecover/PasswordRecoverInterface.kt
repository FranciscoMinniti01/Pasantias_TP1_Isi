package com.example.smart_things.Presentation.Auth.passwordRecover

interface PasswordRecoverInterface {

    interface PasswordRecoverView{
        fun PasswordRecover()
        fun Buttons()
        fun showProgress()
        fun hideProgress()
        fun ShowMessage( title :String, message :String, buttom1 :String)
        fun toast(message: String)
        fun navigateToLogIn()
    }

    interface PasswordRecoverPresenter{
        fun attachView(view: PasswordRecoverView)
        fun dettachView()
        fun isViewAttached():Boolean
        fun sentPasswordRecover(email:String)
    }

}