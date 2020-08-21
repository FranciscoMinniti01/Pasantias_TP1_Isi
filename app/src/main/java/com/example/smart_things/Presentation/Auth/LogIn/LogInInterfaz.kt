package com.example.smart_things.Presentation.Auth.LogIn

interface LogInInterfaz {

    interface View {
        fun SingIn()
        fun NavigateToMain()
        fun NavigateToRegister()
        fun IsAlredyInit()
        fun Buttons()
        fun saveAccount()
        fun showProgress()
        fun hideProgress()
        fun ShowMessage( title :String, message :String, buttom1 :String)
        fun toast(message: String)
        fun getText(value:String):String
    }

    interface Presenter {
        fun attachView(view:View)
        fun dettachView()
        //corrutina
        fun dettachJob()
        fun isViewAttached():Boolean
        fun signInUserWithEmailAndPassword(email:String,password:String)
        fun checkFields(email:String?,password:String?):Boolean
    }

}