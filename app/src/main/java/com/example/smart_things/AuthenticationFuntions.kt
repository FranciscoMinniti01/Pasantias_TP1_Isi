package com.example.smart_things

import com.google.firebase.auth.FirebaseAuth

class AuthenticationFuntions {

    private var authevalided : AutheValided? = null
    fun AutheSetInterfaz(interfaz : AutheValided ){
        this.authevalided = interfaz
    }

    fun Register(Email : String , Password : String, Acction : Int){
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(Email,Password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    authevalided?.StatusAuthentication(true, Acction)
                }else{
                    authevalided?.StatusAuthentication(false, Acction)
                }
            }
    }

    fun LogIn(Email : String , Password : String, Acction : Int){
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(Email,Password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    authevalided?.StatusAuthentication(true, Acction)
                }else{
                    authevalided?.StatusAuthentication(false, Acction)
                }
            }
    }

}