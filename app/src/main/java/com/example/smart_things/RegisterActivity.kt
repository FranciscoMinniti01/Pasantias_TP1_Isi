package com.example.smart_things

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.smart_things.Constantes.FIRST_REGISTER
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), AutheValided{

    val showalert = ShowAlert()
    val authenticationfuntions = AuthenticationFuntions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        authenticationfuntions.AutheSetInterfaz(this)

        Buttons()

    }

    fun Buttons(){
        Re_register_buttom.setOnClickListener{
            if(Re_edit_gmail.text.isNotEmpty() && Re_edit_name.text.isNotEmpty() && Re_edit_password.text.isNotEmpty()){
                authenticationfuntions.Register(Re_edit_gmail.text.toString(),
                                                Re_edit_password.text.toString(),
                                                FIRST_REGISTER)
            }
        }
    }

    fun LogIn(){
        val logInActivity = Intent(this,MainActivity::class.java).apply {
            putExtra("email",Re_edit_gmail.text.toString())
            putExtra("password",Re_edit_password.text.toString())
            putExtra("user",Re_edit_name.text.toString())
            if(Re_seve_pass.isChecked){
                putExtra("seve_user",true)
            }else{
                putExtra("seve_user",false)
            }
        }
        startActivity(logInActivity)
    }

    override fun StatusAuthentication(valided: Boolean, Action : Int) {
        if (Action == FIRST_REGISTER){
            if(valided){
                Toast.makeText(this,"Se Registro",Toast.LENGTH_LONG).show()
                LogIn()
            }else{
                showalert.Showalert(this,"Error","se a producido un error verifique los campos","Aceptar")
            }
        }
    }
}