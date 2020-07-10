package com.example.smart_things

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smart_things.Constantes.ACCEDER
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LogInActivity : AppCompatActivity(), AutheValided {

    private val showalert = ShowAlert()
    private val authenticationfuntions = AuthenticationFuntions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authenticationfuntions.AutheSetInterfaz(this)

        Buttons()

    }

    fun Buttons(){
        Lo_bottom_login.setOnClickListener{
            if(Lo_edit_gmail.text.isNotEmpty() && Lo_edit_name.text.isNotEmpty() && Lo_edit_password.text.isNotEmpty()){
                authenticationfuntions.LogIn(Lo_edit_gmail.text.toString(),
                                             Lo_edit_password.text.toString(),
                                             ACCEDER)
            }
        }
        Lo_bottom_register.setOnClickListener{
            val registerIntent = Intent(this,RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }

    fun LogIn(){
        val mainactivity = Intent(this,MainActivity::class.java).apply{
            putExtra("email",Lo_edit_gmail.text.toString())
            putExtra("password",Lo_edit_password.text.toString())
            putExtra("user",Lo_edit_name.text.toString())
            if(Lo_seve_pass.isChecked){
                putExtra("seve_user",true)
            }else{
                putExtra("seve_user",false)
            }
        }
        startActivity(mainactivity)
    }

    override fun StatusAuthentication(valided: Boolean, Action: Int) {
        if (Action == ACCEDER){
            if(valided){
                Toast.makeText(this,"Autenticacion exitosa", Toast.LENGTH_LONG).show()
                LogIn()
            }else{
                showalert.Showalert(this,"Error","se a producido un error verifique los campos","Aceptar")
            }
        }
    }

}