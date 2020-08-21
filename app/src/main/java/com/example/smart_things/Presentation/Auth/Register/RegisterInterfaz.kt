package com.example.smart_things.Presentation.Auth.Register

interface RegisterInterfaz {

    interface RegisterView {
        fun Register()
        fun NavigateToLogIn()
        fun Buttons()
        fun saveAccount()
        fun showProgress()
        fun hideProgress()
        fun changeButton(verifi :Boolean)
        fun ShowMessage( title :String, message :String, buttom1 :String)
        fun toast(message: String)
    }

    interface RegisterPresenter{
        fun ButtonAction()
        fun attachView(view: RegisterView)
        fun dettachView()
        fun isViewAttached():Boolean
        fun CreateUserWithEmailAndPassword(email:String,password:String)
        fun SentvalidedEmail()
        //fun isValidedEmail()
        fun checkEmptyName(name:String?):Boolean
        fun checkEmptyEmail(email:String?):Boolean
        fun checkEmptyPassword (password:String?,password1: String?):Boolean
        fun checkEmails(email:String?):Boolean
        fun checkequalPassword(password1: String?,password: String):Boolean
    }

}