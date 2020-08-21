package com.example.smart_things.Funtions.Autentication

interface AutheInteractor {

    interface Callback{
        fun AutheonSuccessful()
        fun AutheonFailure(message: String?)
    }

    suspend fun signInWithemailandpassword (email:String,password:String/*corrutina,listener:Callback*/)
    fun CreateUserWithemailandpassword(email: String,password: String,listener:Callback)
    //fun isValidedEmail():Boolean
    fun sentValideEmail(listener: Callback)
    fun LogOut()
    suspend fun sendPasswordResetEmail(email: String)

}