package com.example.smart_things.Autentication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smart_things.*
import com.example.smart_things.Funtions.BaseValided
import com.example.smart_things.Funtions.CloudFirestoreFuntions
import com.example.smart_things.Funtions.Constantes.*
import com.example.smart_things.Funtions.ShowAlert
import com.example.smart_things.MainListView.ModelDevices
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity : AppCompatActivity(), AutheValided {

    /*------------IMPORTACIONES------------*/
    val showalert = ShowAlert()
    val authenticationfuntions = AuthenticationFuntions()
    /*-------------------------------------*/

    /*--------------VARIABLES--------------*/
    var Lo_email: String = ""
    var Lo_password: String = ""
    /*-------------------------------------*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*------------IMPORTACIONES------------*/
        authenticationfuntions.AutheSetInterfaz(this)
        /*-------------------------------------*/

        IsAlredyInit()

        Buttons()

    }

    /*-----------------------------------------------CUENTA GUARDADA-------------------------------------------------------------------*/
    override fun onStart() {
        super.onStart()
        Lo_layout.visibility = View.VISIBLE
    }
    fun IsAlredyInit(){
        val prefs = getSharedPreferences(getString(R.string.prefsFile), Context.MODE_PRIVATE)
        Lo_email = prefs.getString("email",null).toString()
        Lo_password = prefs.getString("password",null).toString()
        if(Lo_email.isNotEmpty() && Lo_password.isNotEmpty()){
            Lo_layout.visibility = View.INVISIBLE
            LogIn(true)
        }
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/


    /*-----------------------------------------------EVENTO DE LOS BOTONES-------------------------------------------------------------------*/
    fun Buttons(){
        Lo_bottom_login.setOnClickListener{
            if(Lo_edit_gmail.text.isNotEmpty() && Lo_edit_password.text.isNotEmpty()){
                Lo_email = Lo_edit_gmail.text.toString()
                Lo_password = Lo_edit_password.text.toString()
                authenticationfuntions.LogIn(Lo_email,Lo_password, LOG_IN)
            }
        }
        Lo_bottom_register.setOnClickListener{
            val registerIntent = Intent(this,
                RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/


    /*---------------------------------------------LOG IN PARA IR A MAIN ACTIVITY------------------------------------------------------------*/
    fun LogIn(NormalLogIN : Boolean){
        val mainactivity = Intent(this, MainActivity::class.java).apply{
            if(!NormalLogIN){
                if (Lo_seve_pass.isChecked) {
                    putExtra("seve_user", true)
                } else {
                    putExtra("seve_user", false)
                }
            }else{
                putExtra("seve_user", false)
            }
            putExtra("email", Lo_email)
            putExtra("password", Lo_password)
            putExtra("come_from", LOG_IN)
        }
        startActivity(mainactivity)
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/


    /*----------------------------CALBAKS DE LAS FUNCIONES DE AUTENTICATION Y DE LA BASE DE DATOS -------------------------------------------*/
    override fun StatusAuthentication(valided: Boolean, Action: Int) {
        if (Action == LOG_IN){
            if(valided){
                LogIn(false)
            }else{
                showalert.Showalert(this,"Error",getString(R.string.error_of_user_data),"Aceptar")
            }
        }
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/
}