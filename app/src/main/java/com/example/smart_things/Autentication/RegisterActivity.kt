package com.example.smart_things.Autentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smart_things.Funtions.BaseValided
import com.example.smart_things.Funtions.CloudFirestoreFuntions
import com.example.smart_things.Funtions.Constantes
import com.example.smart_things.Funtions.Constantes.*
import com.example.smart_things.MainActivity
import com.example.smart_things.R
import com.example.smart_things.Funtions.ShowAlert
import com.example.smart_things.MainListView.ModelDevices
import com.example.smart_things.MainListView.ModelListFloors
import com.example.smart_things.MainListView.ModelListRooms
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), AutheValided {

    /*------------IMPORTACIONES------------*/
    val showalert = ShowAlert()
    val authenticationfuntions = AuthenticationFuntions()
    /*-------------------------------------*/

    /*--------------VARIABLES--------------*/
    var RE_email: String = ""
    var RE_password: String = ""
    var create_user: Boolean = false
    /*-------------------------------------*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        /*------------IMPORTACIONES------------*/
        authenticationfuntions.AutheSetInterfaz(this)
        /*-------------------------------------*/

        Buttons()

    }

    /*-----------------------------------------------EVENTO DE LOS BOTONES-------------------------------------------------------------------*/
    fun Buttons(){
        Re_register_buttom.setOnClickListener{
            if (!create_user){
               if(Re_edit_gmail.text.isNotEmpty() && Re_edit_password.text.isNotEmpty()){
                    RE_email = Re_edit_gmail.text.toString()
                    RE_password = Re_edit_password.text.toString()
                    authenticationfuntions.Register(RE_email,RE_password, REGISTER)
               }
            }else{
                authenticationfuntions.isVerifyEmail(VERIFI_EMAIL)
            }
        }
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/

    /*---------------------------------------------LOG IN PARA IR A MAIN ACTIVITY------------------------------------------------------------*/
    fun LogIn(){
        val logInActivity = Intent(this, ConfigRegisterActivity::class.java).apply {
            putExtra("come_from", REGISTER)
            putExtra("email", RE_email)
            putExtra("password", RE_password)
            if (Re_seve_pass.isChecked) {
                putExtra("seve_user", true)
            } else {
                putExtra("seve_user", false)
            }
        }
        startActivity(logInActivity)
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------*/


    override fun StatusAuthentication(valided: Boolean, Action : Int) {
        if (Action == REGISTER){
            if(valided){
                authenticationfuntions.sendVerificationEmail(SEND_VERIFI_EMAIL)
            }else{
                showalert.Showalert(this,"Error","Error","Aceptar")
            }
        }
        if (Action == SEND_VERIFI_EMAIL){
            if(valided){
                create_user = true
                showalert.Showalert(this,"VERIFY","verifi your email","Aceptar")
                Re_register_buttom.text = getText(R.string.buttom_verify_email)
            }else{
                create_user = false
                showalert.Showalert(this,"Error","Error","Aceptar")
            }
        }
        if (Action == VERIFI_EMAIL){
            if(valided){
                create_user = false
                Re_register_buttom.text = getString(R.string.buttom_register)
                authenticationfuntions.LogIn(RE_email,RE_password,LOGIN_IN_REGISTER)
            }else{
                create_user = true
                showalert.Showalert(this,"Error","Error verify email","Aceptar")
            }
        }
        if (Action == RE_EMAIL_EXITS){
            if(!valided){
                showalert.Showalert(this,"Error","Error","Aceptar")
            }
        }
        if (Action == LOGIN_IN_REGISTER){
            if (valided){
                LogIn()
            }else{
                showalert.Showalert(this,"Error","Error","Aceptar")
            }
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
}