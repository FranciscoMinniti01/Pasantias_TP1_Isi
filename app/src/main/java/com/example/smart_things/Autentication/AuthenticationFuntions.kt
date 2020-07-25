package com.example.smart_things.Autentication

import android.content.ContentValues.TAG
import android.util.Log
import com.example.smart_things.Funtions.Constantes
import com.example.smart_things.Funtions.Constantes.RE_EMAIL_EXITS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import javax.xml.transform.Source


class AuthenticationFuntions {

    private var authevalided : AutheValided? = null
    fun AutheSetInterfaz(interfaz : AutheValided){
        this.authevalided = interfaz
    }

    fun Register(Email : String , Password : String, Acction : Int){
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(Email, Password)
            .addOnCompleteListener {resultado ->
                if (resultado.isSuccessful) {
                    authevalided?.StatusAuthentication(true, Acction)
                }else{
                    if (resultado.exception.toString().contains("The email address is already in use by another account", ignoreCase = true)){
                        authevalided?.StatusAuthentication(false, RE_EMAIL_EXITS)
                    }else{
                        authevalided?.StatusAuthentication(false, Acction)
                    }
                }
            }
    }

    fun sendVerificationEmail(Acction: Int){
        val auth = FirebaseAuth.getInstance().currentUser
        if(auth != null){
            auth
                .sendEmailVerification()
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        authevalided?.StatusAuthentication(true, Acction)
                    }else{
                        authevalided?.StatusAuthentication(false, Acction)
                    }
                }
        }
    }

    fun isVerifyEmail(Acction: Int){
        //val mAuthListener = FirebaseAuth.AuthStateListener {
        val auth = FirebaseAuth.getInstance().currentUser
        if (auth != null) {
            if (auth.isEmailVerified){
                authevalided?.StatusAuthentication(true, Acction)
            }else{
                authevalided?.StatusAuthentication(false, Acction)
            }
        }else{
            authevalided?.StatusAuthentication(false, Acction)
        }
        //}
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

    fun logOut(){
        FirebaseAuth.getInstance().signOut()
    }

}